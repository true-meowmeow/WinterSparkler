package core.contentManager;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

import static swing.pages.home.play.objects.FolderUtil.convertSlashes;

public class FileDataProcessor {
    private static final Set<String> AUDIO_EXTENSIONS = new HashSet<>(Arrays.asList("wav", "flac", "opus", "mp3"));

    /**
     * Обходит список корневых директорий, собирает информацию об аудиофайлах,
     * формируя структуру в FilesDataList.
     *
     * @param rootPaths список абсолютных путей к корневым папкам
     * @return объект FilesDataList с информацией об аудиофайлах и папках
     */
    public FilesDataList processRootPaths(List<String> rootPaths) {
        FilesDataList filesDataList = new FilesDataList();
        for (String rootPath : rootPaths) {
            File root = new File(rootPath);
            if (!root.exists() || !root.isDirectory()) {
                System.out.println("Путь не является допустимой директорией: " + rootPath);
                continue;
            }
            rootPath += "\\";
            // Создаем контейнер для аудиоданных по данному корневому пути
            filesDataList.createMediaFolderDataValues(rootPath);
            processDirectory(root, root, filesDataList);

            if (filesDataList.getMediaFolderDataHashMap().get(rootPath).getFolderDataSet().size() == 0) {
                filesDataList.terminate(rootPath);
            }
        }
        addMissingParentFolders(filesDataList);
        return filesDataList;
    }

    /**
     * Рекурсивно обходит директорию, добавляя информацию об аудиофайлах в FilesDataList.
     *
     * @param currentDir    текущая директория для обработки
     * @param baseDir       базовая директория для вычисления относительного пути
     * @param filesDataList объект для хранения аудиофайлов и данных о папках
     */
    private void processDirectory(File currentDir, File baseDir, FilesDataList filesDataList) {
        File[] files = currentDir.listFiles();
        if (files == null) return;
        for (File file : files) {
            if (file.isDirectory()) {
                processDirectory(file, baseDir, filesDataList);
            } else if (file.isFile()) {
                // Вычисляем относительный путь папки, где находится файл
                String folderRelative = "";
                File parent = file.getParentFile();
                if (parent != null) {
                    folderRelative = convertSlashes(baseDir.toURI().relativize(parent.toURI()).getPath());
                }
                String pathRoot = extractRootPath(file.getPath(), folderRelative, file.getName());
                String pathRelative = folderRelative;
                String pathFull = pathRoot + pathRelative;

                MediaData mediaData = new MediaData(pathFull, pathRoot, pathRelative, file.getName());
                // Обрабатываем только аудиофайлы
                if (AUDIO_EXTENSIONS.contains(mediaData.getExtension())) {
                    filesDataList.addMediaData(pathRoot, mediaData);
                    filesDataList.addFolderData(pathRoot, new FolderData(
                            pathFull,
                            pathRoot,
                            pathRelative,
                            extractFolderName(pathFull)
                    ));
                }
            }
        }
    }

    /**
     * Добавляет недостающие родительские папки в FilesDataList.
     *
     * @param filesDataList объект с данными о папках
     */
    private void addMissingParentFolders(FilesDataList filesDataList) {
        for (Map.Entry<String, FilesDataList.MediaFolderData> entry : filesDataList.getMediaFolderDataHashMap().entrySet()) {
            String rootPath = entry.getKey();
            HashSet<FolderData> folderSet = entry.getValue().getFolderDataSet();
            // Собираем уже имеющиеся полные пути для быстрого поиска
            Set<String> existingFullPaths = folderSet.stream()
                    .map(FolderData::getPathFull)
                    .collect(Collectors.toSet());
            // Множество для вновь добавленных путей, чтобы избежать дубликатов
            Set<String> newFullPaths = new HashSet<>();

            List<FolderData> folderList = new ArrayList<>(folderSet);
            for (FolderData folderData : folderList) {
                String relativePath = folderData.getPathRelative();
                String normalizedRelative = relativePath.replaceAll("[/\\\\]+$", "");
                if (normalizedRelative.isEmpty()) {
                    continue;
                }
                // Разбиваем относительный путь на части
                String[] parts = normalizedRelative.split("[/\\\\]");
                // Для многосоставных путей добавляем каждый промежуточный уровень
                for (int i = 1; i < parts.length; i++) {
                    String parentRelative = String.join("\\", Arrays.copyOfRange(parts, 0, i)) + "\\";
                    String parentFullPath = rootPath + parentRelative;
                    if (!existingFullPaths.contains(parentFullPath) && !newFullPaths.contains(parentFullPath)) {
                        FolderData parentFolder = new FolderData(
                                parentFullPath,
                                rootPath,
                                parentRelative,
                                extractFolderName(parentFullPath)
                        );
                        long childCount = countChildFolders(folderSet, parentRelative);
                        if (childCount == 1) {
                            parentFolder.deactivate();
                        }
                        folderSet.add(parentFolder);
                        newFullPaths.add(parentFullPath);
                    }
                }
            }
        }
    }


    private long countChildFolders(Set<FolderData> folderSet, String parentRelative) {
        return folderSet.stream()
                .filter(fd -> {
                    String norm = fd.getPathRelative().replaceAll("[/\\\\]+$", "");
                    return norm.startsWith(parentRelative) && !norm.equals(parentRelative);
                })
                .count();
    }

    /**
     * Извлекает корневой путь из полного пути к файлу, удаляя из конца относительный путь и имя файла.
     *
     * @param absolutePath полный путь к файлу
     * @param relativePath относительный путь до файла
     * @param fileName     имя файла
     * @return корневой путь
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
     * Извлекает имя папки из полного пути.
     *
     * @param fullPath полный путь к папке
     * @return имя папки
     */
    public static String extractFolderName(String fullPath) {
        if (fullPath == null || fullPath.isEmpty()) {
            return "";
        }
        while (fullPath.endsWith("\\") || fullPath.endsWith("/")) {
            fullPath = fullPath.substring(0, fullPath.length() - 1);
        }
        int lastSeparatorIndex = Math.max(fullPath.lastIndexOf("\\"), fullPath.lastIndexOf("/"));
        if (lastSeparatorIndex == -1) {
            return fullPath;
        }
        return fullPath.substring(lastSeparatorIndex + 1);
    }

    /**
     * Собирает все аудиофайлы из FilesDataList.
     *
     * @param filesDataList объект с информацией о медиа файлах
     * @return список аудиофайлов
     */
    public List<MediaData> filterAudioFiles(FilesDataList filesDataList) {
        return filesDataList.getMediaFolderDataHashMap().values().stream()
                .flatMap(mfd -> mfd.getMediaDataSet().stream())
                .collect(Collectors.toList());
    }

}
