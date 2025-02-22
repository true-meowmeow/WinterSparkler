package core.contentManager;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

import static swing.pages.home.play.objects.FolderUtil.convertSlashes;

public class FileDataProcessor {
    private static final Set<String> AUDIO_EXTENSIONS = new HashSet<>(Arrays.asList("wav", "flac", "opus", "mp3"));

    /**
     * Обходит список корневых директорий, собирает информацию о файлах и формирует
     * два списка: всех найденных файлов и только аудиофайлов.
     *
     * @param rootPaths список абсолютных путей к корневым папкам
     * @return объект FilesDataList с двумя коллекциями файлов
     */
    public FilesDataList processRootPaths(List<String> rootPaths) {
        FilesDataList filesDataList = new FilesDataList();
        for (String rootPath : rootPaths) {
            File root = new File(rootPath);
            if (!root.exists() || !root.isDirectory()) {
                System.out.println("Путь не является допустимой директорией: " + rootPath);
                continue;
            }
            MediaData mediaDataAll = new MediaData(rootPath);
            MediaData mediaDataFiltered = new MediaData(rootPath);
            processDirectory(root, root, mediaDataAll, mediaDataFiltered, filesDataList);
            filesDataList.getMediaDataListAll().add(mediaDataAll);
            if (!mediaDataFiltered.getMediaData().isEmpty()) {
                filesDataList.getMediaDataListFiltered().add(mediaDataFiltered);
            }
        }

        for (FoldersRootData foldersRootData : filesDataList.getFoldersRootData()) {
            addMissingParentFolders(foldersRootData.getFolderData());
        }
        return filesDataList;
    }

    public void addMissingParentFolders(TreeSet<FolderRootsData> folderRootsDataList) {
        Set<String> existingFullPaths = folderRootsDataList.stream()
                .map(FolderRootsData::getFullPath)
                .collect(Collectors.toCollection(HashSet::new));

        Set<FolderRootsData> foldersToAdd = new HashSet<>();
        Set<String> newFullPaths = new HashSet<>();

        for (FolderRootsData folderRootsData : folderRootsDataList) {
            String rootPath = folderRootsData.getRootPath();
            String relativePath = folderRootsData.getRelativePath();

            // Нормализуем относительный путь, удаляя завершающие слеши
            String normalizedRelative = relativePath.replaceAll("\\\\+$", "");
            if (normalizedRelative.isEmpty()) {
                continue;
            }

            String[] relativeParts = normalizedRelative.split("\\\\");
            for (int i = 1; i < relativeParts.length; i++) {
                String[] currentParts = Arrays.copyOfRange(relativeParts, 0, i);
                String currentRelative = String.join("\\", currentParts) + "\\";
                String currentFullPath = rootPath + currentRelative;

                if (!existingFullPaths.contains(currentFullPath) && !newFullPaths.contains(currentFullPath)) {
                    FolderRootsData newFolder = new FolderRootsData(currentFullPath, rootPath, currentRelative);
                    foldersToAdd.add(newFolder);
                    newFullPaths.add(currentFullPath);
                }
            }
        }

        folderRootsDataList.addAll(foldersToAdd);
    }


    /**
     * Рекурсивно обходит директорию, добавляя информацию о файлах в общий список и,
     * если файл имеет аудио-расширение, в фильтрованный список.
     *
     * @param currentDir       текущая обрабатываемая директория
     * @param baseDir          корневая директория, относительно которой считается путь
     * @param mediaDataAll     объект для хранения информации обо всех файлах
     * @param mediaDataFiltered объект для хранения информации об аудиофайлах
     */
    private void processDirectory(File currentDir, File baseDir, MediaData mediaDataAll, MediaData mediaDataFiltered, FilesDataList filesDataList) {
        File[] files = currentDir.listFiles();
        if (files == null) return;
        for (File file : files) {
            if (file.isDirectory()) {
                processDirectory(file, baseDir, mediaDataAll, mediaDataFiltered, filesDataList);
            } else if (file.isFile()) {
                // Вычисляем относительный путь папки, в которой находится файл
                String folderRelative = "";
                File parent = file.getParentFile();
                if (parent != null) {
                    folderRelative = convertSlashes(baseDir.toURI().relativize(parent.toURI()).getPath());
                }
                MediaData.MediaFile mediaFile = new MediaData.MediaFile(
                        extractRootPath(file.getPath(), folderRelative, file.getName()),
                        folderRelative,
                        file.getName());
                mediaDataAll.addMediaData(mediaFile);
                if (AUDIO_EXTENSIONS.contains(mediaFile.getExtension())) {
                    mediaDataFiltered.addMediaData(mediaFile);  //Добавление медиа файла
                    filesDataList.addFolderData(new FolderRootsData(mediaFile.getPathFull(), mediaFile.getPathRoot(), mediaFile.getPathRelative()));    //Добавления папок

                }
            }
        }
    }

    /**
     * Извлекает корневой путь из полного абсолютного пути файла, удаляя из конца относительный путь и имя файла.
     *
     * @param absolutePath полный путь к файлу
     * @param relativePath относительный путь до файла (без имени файла)
     * @param fileName     имя файла
     * @return строка с корневым путем
     */
    private String extractRootPath(String absolutePath, String relativePath, String fileName) {
        String endPart = relativePath + fileName;
        if (absolutePath.endsWith(endPart)) {
            return absolutePath.substring(0, absolutePath.length() - endPart.length());
        }
        int index = absolutePath.indexOf(relativePath);
        if (index != -1) {
            return absolutePath.substring(0, index);
        }
        throw new IllegalArgumentException("Переданные данные не соответствуют ожиданиям");
    }

    /**
     * Собирает все аудиофайлы из списка FilesDataList.
     *
     * @param filesDataList объект с информацией о файлах
     * @return список аудиофайлов
     */
    public List<MediaData.MediaFile> filterAudioFiles(FilesDataList filesDataList) {
        return filesDataList.getMediaDataListFiltered().stream()
                .flatMap(fd -> fd.getMediaData().stream())
                .collect(Collectors.toList());
    }
}
