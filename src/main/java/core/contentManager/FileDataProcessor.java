package core.contentManager;

import java.io.File;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileDataProcessor {

    /**
     * Проходит по всем указанным корневым путям и собирает данные обо всех файлах.
     * Для каждого корневого пути вычисляется относительный путь для найденных файлов.
     *
     * @param rootPaths список корневых путей (например, "C:\\Music")
     * @return список объектов FileData со всей информацией обо всех найденных файлах
     */
    public List<FileData> processRootPaths(List<String> rootPaths) {
        List<FileData> allFiles = new ArrayList<>();
        for (String rootPath : rootPaths) {
            File root = new File(rootPath);
            if (root.exists() && root.isDirectory()) {
                allFiles.addAll(processDirectory(root, root));
            } else {
                System.out.println("Путь не является допустимой директорией: " + rootPath);
            }
        }
        return allFiles;
    }

    /**
     * Рекурсивно обходит директорию.
     *
     * @param currentDir текущая директория обхода
     * @param baseDir    корневая директория, относительно которой будет вычисляться относительный путь
     * @return список объектов FileData, найденных в данной директории и её подпапках
     */
    private List<FileData> processDirectory(File currentDir, File baseDir) {
        List<FileData> filesList = new ArrayList<>();
        File[] files = currentDir.listFiles();
        if (files == null) {
            return filesList;
        }
        for (File file : files) {
            if (file.isDirectory()) {
                // Рекурсивно обрабатываем подпапки
                filesList.addAll(processDirectory(file, baseDir));
            } else if (file.isFile()) {
                // Вычисляем полный относительный путь от baseDir до файла
                URI baseURI = baseDir.toURI();
                URI fileURI = file.toURI();
                String fullRelativePath = baseURI.relativize(fileURI).getPath();
                // Убираем имя файла из полного относительного пути, оставляя только путь к папке
                String folderRelative;
                if (fullRelativePath.endsWith(file.getName())) {
                    folderRelative = fullRelativePath.substring(0, fullRelativePath.length() - file.getName().length());
                } else {
                    folderRelative = fullRelativePath; // На всякий случай
                }
                // Вычисляем расширение файла (без точки)
                String extension;
                int dotIndex = file.getName().lastIndexOf('.');
                if (dotIndex != -1 && dotIndex < file.getName().length() - 1) {
                    extension = file.getName().substring(dotIndex + 1).toLowerCase();
                } else {
                    extension = "нет_расширения";
                }
                // Создаём объект FileData с корневым путем, именем файла, относительным путем (без имени) и расширением
                filesList.add(new FileData(baseDir.getAbsolutePath(), file.getName(), folderRelative, extension));
            }
        }
        return filesList;
    }

    /**
     * Фильтрует список файлов, оставляя только аудиофайлы с нужными расширениями.
     *
     * @param allFiles список всех найденных файлов
     * @return список аудиофайлов (wav, opus, flac, mp3)
     */


    public List<FileData> filterAudioFiles(List<FileData> allFiles) {
        List<FileData> audioFiles = new ArrayList<>();
        for (FileData fileData : allFiles) {

            switch (fileData.getExtension()) {
                case "wav" -> {
                    audioFiles.add(fileData);
                }
                case "flac" -> {
                    audioFiles.add(fileData);
                }
                default -> {
                    System.out.println("no support");
                }
            }
        }
        return audioFiles;
    }

    /**
     * Выводит статистику в консоль – количество файлов для каждого расширения.
     *
     * @param files список файлов для анализа
     * @param label метка, например "Неотсортированные" или "Отфильтрованные"
     */
    public void printFileStatistics(List<FileData> files, String label) {
        Map<String, Integer> extensionCount = new HashMap<>();
        for (FileData fileData : files) {
            String ext = fileData.getExtension();
            extensionCount.put(ext, extensionCount.getOrDefault(ext, 0) + 1);
        }
        System.out.println("Статистика для " + label + " файлов:");
        int total = 0;
        for (Map.Entry<String, Integer> entry : extensionCount.entrySet()) {
            System.out.println("Расширение '" + entry.getKey() + "': " + entry.getValue() + " шт.");
            total += entry.getValue();
        }
        System.out.println("Общее количество файлов: " + total);
    }
}
