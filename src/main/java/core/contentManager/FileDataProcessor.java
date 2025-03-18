package core.contentManager;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import static swing.pages.home.play.objects.FolderUtil.*;

public class FileDataProcessor {
    private static final Set<String> AUDIO_EXTENSIONS = new HashSet<>(Arrays.asList("wav", "flac", "opus", "mp3"));

    public FilesDataMap processRootPaths(List<Path> rootPathList) {
        FilesDataMap filesDataMap = new FilesDataMap();
        for (Path rootPath : rootPathList) {
            File root = rootPath.toFile();
            if (!root.exists() || !root.isDirectory()) {
                System.out.println("Путь не является допустимой директорией: " + rootPath);
                continue;
            }
            rootPath = rootPath.normalize();


            filesDataMap.createCatalogDataHashMap(rootPath);
            processDirectory(rootPath, root, filesDataMap);

/*            for (FolderData folderData : filesDataMap.getMediaFolderDataHashMap().get(rootPath).getFolderDataSet()) {
                folderData.setLinkParentPathFull(getClosestPath(filesDataMap.getMediaFolderDataHashMap().get(rootPath).getFolderDataSet(), folderData.getPathFull()));
            }

            if (filesDataMap.getMediaFolderDataHashMap().get(rootPath).getFolderDataSet().size() == 0) {
                filesDataMap.terminate(rootPath);
            }*/
        }
        //addMissingParentFolders(filesDataMap);
        return filesDataMap;
    }

    private void processDirectory(Path rootPath, File currentFile, FilesDataMap filesDataMap) {
        Path currentPath = Paths.get(currentFile.getPath()).normalize();
        Path relativePath = rootPath.relativize(currentPath);
        Path namePath = currentPath.getFileName();

        filesDataMap.getCatalogDataWithPath(rootPath)
                .createFilesData(
                        new FolderData(currentPath, rootPath, relativePath, namePath, relativePath));

        File[] files = currentFile.listFiles();
        if (files == null) return;
        FilesDataMap.CatalogData.FilesData filesData = filesDataMap.getCatalogDataWithPath(rootPath).getFilesDataWithPath(currentPath);

        for (File file : files) {
            if (file.isDirectory()) {
                processDirectory(rootPath, file, filesDataMap);     // Рекурсивный вызов для обхода поддиректорий
                filesDataMap.getCatalogDataWithPath(rootPath).getFilesDataWithPath(currentPath).addSubFolder(currentPath, currentPath.getFileName());
            } else {
                Path fullNamePath = Paths.get(file.getPath()).normalize();
                String nameFull = fullNamePath.getFileName().toString();
                String name = getName(nameFull);
                String extension = getExtension(nameFull);


                if (AUDIO_EXTENSIONS.contains(extension)) {
                    //todo Нужно будет parrent and next links ставить

                    filesDataMap.getCatalogDataWithPath(rootPath).getFilesDataWithPath(currentPath).addMediaData(new MediaData(fullNamePath, currentPath, rootPath, relativePath, nameFull, name, extension));
                }
            }
        }
    }
}
/*    private String applyBackslash(File name) {
        return applyBackslash(name.toString());
    }

    private String applyBackslash(String name) {
        return name + "\\";
    }*/
    /*

    //Работает и ладно :/
    public static String getClosestPath(HashSet<FolderData> folderSet, String pathFull) {
        // Собираем все полные пути в множество для быстрого поиска
        Set<String> paths = folderSet.stream()
                .map(FolderData::getPathFull)
                .collect(Collectors.toSet());

        // Генерируем список всех родительских путей от ближайшего к корню
        List<String> allParents = generateAllParents(pathFull);

        for (String parent : allParents) {
            // Если ближайший родитель уже присутствует в сете, возвращаем его
            if (paths.contains(parent)) {
                return parent;
            } else {
                // Если его нет, проверяем, образует ли этот уровень развилку
                long count = paths.stream()
                        .filter(p -> p.startsWith(parent) && p.length() > parent.length())
                        .count();
                if (count >= 2) {
                    return parent;
                }
            }
        }

        // Если ничего не найдено – находим самый длинный префикс среди путей из сета
        return paths.stream()
                .filter(p -> pathFull.startsWith(p) && !p.equals(pathFull))
                .max(Comparator.comparingInt(String::length))
                .orElse("");
    }

    private static List<String> generateAllParents(String pathFull) {
        List<String> parents = new ArrayList<>();
        String normalizedPath = pathFull.endsWith("\\") ? pathFull : pathFull + "\\";
        String current = normalizedPath;

        while (true) {
            int lastSeparatorIndex = current.lastIndexOf("\\", current.length() - 2);
            if (lastSeparatorIndex == -1) {
                break;
            }
            String parent = current.substring(0, lastSeparatorIndex + 1);
            parents.add(parent);
            current = parent;

            // Если достигли корня, например, "C:\", выходим
            if (parent.length() == 3 && parent.charAt(1) == ':') {
                break;
            }
        }

        return parents;
    }


    private static boolean isBranching(HashSet<FolderData> folderSet, String candidateFullPath) {
        Set<String> immediateChildren = new HashSet<>();
        for (FolderData fd : folderSet) {
            String full = fd.getPathFull();
            if (full.startsWith(candidateFullPath) && !full.equals(candidateFullPath)) {
                // Получаем остаток после candidateFullPath
                String remainder = full.substring(candidateFullPath.length());
                // Убираем ведущие разделители (например, "\" или "/")
                remainder = remainder.replaceAll("^[\\\\/]+", "");
                if (!remainder.isEmpty()) {
                    // Извлекаем первую часть (имя непосредственного ребенка)
                    String[] tokens = remainder.split("[\\\\/]+");
                    if (tokens.length > 0) {
                        immediateChildren.add(tokens[0]);
                    }
                }
            }
        }
        return immediateChildren.size() > 1;
    }

    private String getParentFolder(String path) {
        if (path == null || path.isEmpty()) {
            return path;
        }

        // Убираем завершающий символ '\' или '/', если он присутствует
        String trimmedPath = path;
        if (trimmedPath.endsWith("\\") || trimmedPath.endsWith("/")) {
            trimmedPath = trimmedPath.substring(0, trimmedPath.length() - 1);
        }

        // Ищем индекс последнего разделителя '\'
        int lastSeparatorIndex = trimmedPath.lastIndexOf("\\");
        if (lastSeparatorIndex == -1) {
            // Если разделитель не найден, возвращаем пустую строку или можно обработать иначе
            return "";
        }

        // Возвращаем подстроку от начала до найденного разделителя включительно (для сохранения завершающего '\')
        return trimmedPath.substring(0, lastSeparatorIndex + 1);
    }
    private void addMissingParentFolders(FilesDataMap filesDataMap) {
        for (Map.Entry<String, FilesDataMap.FilesData> entry : filesDataMap.getMediaFolderDataHashMap().entrySet()) {
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
                                extractFolderName(parentFullPath),
                                getParentFolder(parentFullPath)
                        );

                        if (getChildFolderPaths(folderSet, parentFullPath).length == 1) {   //Если количество папок внутри равно одному, то задаём ссылку для пропуска этой папки и деактивируем её. В папке нет аудиофайлов так как нет её пути в объектах папок
                            parentFolder.deactivate(getChildFolderPaths(folderSet, parentFullPath)[0]);
                        }

                        folderSet.add(parentFolder);
                        newFullPaths.add(parentFullPath);

                    }
                }
            }
        }
    }


    private String[] getChildFolderPaths(Set<FolderData> folderSet, String parentFullPath) {
        return folderSet.stream()
                .map(FolderData::getPathFull) // Теперь используем полный путь
                .filter(path -> path.startsWith(parentFullPath) && !path.equals(parentFullPath)) // Сравниваем с полным путем
                .toArray(String[]::new); // Преобразуем в массив строк
    }

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
        while (fullPath.endsWith("\\") || fullPath.endsWith("/")) {
            fullPath = fullPath.substring(0, fullPath.length() - 1);
        }
        int lastSeparatorIndex = Math.max(fullPath.lastIndexOf("\\"), fullPath.lastIndexOf("/"));
        if (lastSeparatorIndex == -1) {
            return fullPath;
        }
        return fullPath.substring(lastSeparatorIndex + 1);
    }
*//*    public List<MediaData> filterAudioFiles(FilesDataList filesDataList) {
        return filesDataList.getMediaFolderDataHashMap().values().stream()
                .flatMap(mfd -> mfd.getMediaDataSet().stream())
                .collect(Collectors.toList());
    }

}*/
