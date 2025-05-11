package core.contentManager;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import static core.contentManager.FolderUtil.getExtension;
import static core.contentManager.FolderUtil.getName;

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
        //FilesDataMap.CatalogData.FilesData filesData = filesDataMap.getCatalogDataWithPath(rootPath).getFilesDataWithPath(currentPath);

        for (File file : files) {
            Path pathFile = Paths.get(file.getPath()).normalize();
            if (file.isDirectory()) {
                processDirectory(rootPath, file, filesDataMap);     // Рекурсивный вызов для обхода поддиректорий
                filesDataMap.getCatalogDataWithPath(rootPath).getFilesDataWithPath(currentPath).addSubFolder(pathFile, pathFile.getFileName());
            } else {
                Path fullNamePath = pathFile;
                String folderName = String.valueOf(pathFile.getParent().getFileName());
                String nameFull = fullNamePath.getFileName().toString();
                String name = getName(nameFull);
                String extension = getExtension(nameFull);

                if (AUDIO_EXTENSIONS.contains(extension)) {
                    //todo Нужно будет parent and next links ставить

                    filesDataMap.getCatalogDataWithPath(rootPath).getFilesDataWithPath(currentPath).addMediaData(new MediaData(fullNamePath, currentPath, rootPath, relativePath, nameFull, name, extension));
                }
            }
        }
    }

    public static String[] getFolderNames(Path fullNamePath) {
        int nameCount = fullNamePath.getNameCount();
        if (nameCount <= 1) {
            return new String[0];
        }
        String[] folders = new String[nameCount];
        for (int i = 0; i < nameCount; i++) {
            folders[i] = fullNamePath.getName(i).toString();
        }
        return folders;
    }
}