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

        addMissingParentFolders(filesDataList);

        return filesDataList;
    }

    private void addMissingParentFolders(FilesDataList filesDataList) {
        // Проходим по всем записям в HashMap: ключ – rootPath, значение – HashSet с FolderData
        for (Map.Entry<String, HashSet<FolderData>> entry : filesDataList.getFolderDataMap().entrySet()) {
            String rootPath = entry.getKey();
            HashSet<FolderData> folderSet = entry.getValue();
            // Собираем уже имеющиеся полные пути для быстрого поиска
            Set<String> existingFullPaths = folderSet.stream()
                    .map(FolderData::getPathFull)
                    .collect(Collectors.toSet());
            // Множество для уже добавленных новых путей, чтобы не создавать дубликаты
            Set<String> newFullPaths = new HashSet<>();

            // Создаем копию списка, чтобы не получить ConcurrentModificationException при добавлении новых элементов
            List<FolderData> folderList = new ArrayList<>(folderSet);

            for (FolderData folderData : folderList) {
                String relativePath = folderData.getPathRelative();
                // Удаляем завершающие слеши
                String normalizedRelative = relativePath.replaceAll("[/\\\\]+$", "");
                if (normalizedRelative.isEmpty()) {
                    continue;
                }
                // Разбиваем относительный путь по разделителям (учитывая и "/" и "\")
                String[] parts = normalizedRelative.split("[/\\\\]");
                // Если в относительном пути несколько уровней, проверяем наличие каждого промежуточного уровня
                // Например, для "Папка1\Папка2\Папка3" добавляем "Папка1\" и "Папка1\Папка2\"
                for (int i = 1; i < parts.length; i++) {
                    String parentRelative = String.join("\\", Arrays.copyOfRange(parts, 0, i)) + "\\";
                    String parentFullPath = rootPath + parentRelative;
                    if (!existingFullPaths.contains(parentFullPath) && !newFullPaths.contains(parentFullPath)) {
                        FolderData parentFolder = new FolderData(
                                parentFullPath,
                                rootPath,
                                parentRelative,
                                FileDataProcessor.extractFolderName(parentFullPath)
                        );

                        // Подсчёт количества подпапок для данного родительского пути и деактивация папки для пропуска
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
        // Нормализуем parentRelative, если требуется
        return folderSet.stream()
                .filter(fd -> {
                    String norm = fd.getPathRelative().replaceAll("[/\\\\]+$", "");
                    // Проверяем, что путь начинается с parentRelative и сам не равен ему
                    return norm.startsWith(parentRelative) && !norm.equals(parentRelative);
                })
                .count();
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

                String pathRoot = extractRootPath(file.getPath(), folderRelative, file.getName());
                String pathRelative = folderRelative;
                String pathFull = pathRoot + pathRelative;

                MediaData.MediaFile mediaFile = new MediaData.MediaFile(
                        pathFull,
                        pathRoot,
                        pathRelative,
                        file.getName());
                mediaDataAll.addMediaData(mediaFile);
                if (AUDIO_EXTENSIONS.contains(mediaFile.getExtension())) {
                    mediaDataFiltered.addMediaData(mediaFile);  //Добавление медиа файла

                    filesDataList.createFoldersDataValues(pathRoot);
                    filesDataList.addFoldersData(pathRoot, new FolderData(pathFull, pathRoot, pathRelative, extractFolderName(pathFull)));  //Добавление папки
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


    public static String extractFolderName(String fullPath) {
        if (fullPath == null || fullPath.isEmpty()) {
            return "";
        }
        // Удаляем завершающие разделители
        while (fullPath.endsWith("\\") || fullPath.endsWith("/")) {
            fullPath = fullPath.substring(0, fullPath.length() - 1);
        }
        // Находим последний индекс разделителя
        int lastSeparatorIndex = Math.max(fullPath.lastIndexOf("\\"), fullPath.lastIndexOf("/"));
        if (lastSeparatorIndex == -1) {
            // Если разделитель не найден, возвращаем всю строку
            return fullPath;
        }
        return fullPath.substring(lastSeparatorIndex + 1);
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
