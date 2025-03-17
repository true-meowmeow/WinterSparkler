package core.contentManager;

import java.nio.file.Path;
import java.util.*;


public class FilesDataMap {

    public FilesDataMap() {
        mediaFolderDataHashMap = new HashMap<>();
    }

    private HashMap<Path, FilesData> mediaFolderDataHashMap;     //rootPath, FilesData

    public void createMediaFolderDataValues(Path rootPath) {
        mediaFolderDataHashMap.putIfAbsent(rootPath, new FilesData(rootPath));
    }

    public void addMediaData(Path rootPath, MediaData mediaData) {
        mediaFolderDataHashMap.get(rootPath).getMediaDataSet().put(mediaData.getFullNamePath(), mediaData);
    }
    public void addFolderData(Path rootPath, FolderData folderData) {
        mediaFolderDataHashMap.get(rootPath).getFolderDataMap().put(folderData.getFullPath(), folderData);
    }

    public void terminate(String rootPath) {
        mediaFolderDataHashMap.remove(rootPath);
    }

    public HashMap<Path, FilesData> getMediaFolderDataHashMap() {
        return mediaFolderDataHashMap;
    }

    public static class FilesData {   //В каждом объекте есть как минимум одна корневая папка и файл, подходящий под критерии

        private Path pathRoot;

        public FilesData(Path pathRoot) {
            this.pathRoot = pathRoot;

            mediaDataSet = new HashMap<>();
            folderDataMap = new HashMap<>();
        }

        public Path getPathRoot() {
            return pathRoot;
        }

        //media map
        private HashMap<Path, MediaData> mediaDataSet;

        public HashMap<Path, MediaData> getMediaDataSet() {
            return mediaDataSet;
        }

        //folders map
        private HashMap<Path, FolderData> folderDataMap;

        public HashMap<Path, FolderData> getFolderDataMap() {
            return folderDataMap;
        }
    }

}


