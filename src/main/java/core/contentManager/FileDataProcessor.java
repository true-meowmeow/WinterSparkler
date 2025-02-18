package core.contentManager;

import java.io.File;
import java.net.URI;
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
            FilesData filesDataAll = new FilesData(rootPath);
            FilesData filesDataFiltered = new FilesData(rootPath);
            processDirectory(root, root, filesDataAll, filesDataFiltered);
            filesDataList.getFilesDataListAll().add(filesDataAll);
            if (!filesDataFiltered.getFileData().isEmpty()) {
                filesDataList.getFilesDataListFiltered().add(filesDataFiltered);
            }
        }
        return filesDataList;
    }

    /**
     * Рекурсивно обходит директорию, добавляя информацию о файлах в общий список и,
     * если файл имеет аудио-расширение, в фильтрованный список.
     *
     * @param currentDir       текущая обрабатываемая директория
     * @param baseDir          корневая директория, относительно которой считается путь
     * @param filesDataAll     объект для хранения информации обо всех файлах
     * @param filesDataFiltered объект для хранения информации об аудиофайлах
     */
    private void processDirectory(File currentDir, File baseDir, FilesData filesDataAll, FilesData filesDataFiltered) {
        File[] files = currentDir.listFiles();
        if (files == null) return;
        for (File file : files) {
            if (file.isDirectory()) {
                processDirectory(file, baseDir, filesDataAll, filesDataFiltered);
            } else if (file.isFile()) {
                // Вычисляем относительный путь папки, в которой находится файл
                String folderRelative = "";
                File parent = file.getParentFile();
                if (parent != null) {
                    folderRelative = convertSlashes(baseDir.toURI().relativize(parent.toURI()).getPath());
                }
                FilesData.FileData fileData = new FilesData.FileData(
                        extractRootPath(file.getPath(), folderRelative, file.getName()),
                        folderRelative,
                        file.getName());
                filesDataAll.addFileData(fileData);
                if (AUDIO_EXTENSIONS.contains(fileData.getExtension())) {
                    filesDataFiltered.addFileData(fileData);
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
    public List<FilesData.FileData> filterAudioFiles(FilesDataList filesDataList) {
        return filesDataList.getFilesDataListFiltered().stream()
                .flatMap(fd -> fd.getFileData().stream())
                .collect(Collectors.toList());
    }
}
