package core.contentManager;

import java.util.*;


public class FilesDataMap {

    public FilesDataMap() {
        mediaFolderDataHashMap = new HashMap<>();
    }

    private HashMap<String, FilesData> mediaFolderDataHashMap;     //rootPath, mediaData

    public void createMediaFolderDataValues(String rootPath) {
        mediaFolderDataHashMap.putIfAbsent(rootPath, new FilesData(rootPath));
    }

    public void addMediaData(String rootPath, MediaData mediaData) {
        mediaFolderDataHashMap.get(rootPath).getMediaDataSet().add(mediaData);
    }
    public void addFolderData(String rootPath, FolderData folderData) {
        mediaFolderDataHashMap.get(rootPath).getFolderDataSet().add(folderData);
    }

    public void terminate(String rootPath) {
        mediaFolderDataHashMap.remove(rootPath);
    }

    public HashMap<String, FilesData> getMediaFolderDataHashMap() {
        return mediaFolderDataHashMap;
    }

    public static class FilesData {   //В каждом объекте есть как минимум одна корневая папка и файл, подходящий под критерии

        private String pathRoot;

        public FilesData(String pathRoot) {
            this.pathRoot = pathRoot;

            mediaDataSet = new HashSet<>();
            folderDataSet = new HashSet<>();
        }

        public String getPathRoot() {
            return pathRoot;
        }

        //media
        private HashSet<MediaData> mediaDataSet;

        public HashSet<MediaData> getMediaDataSet() {
            return mediaDataSet;
        }

        //folders
        private HashSet<FolderData> folderDataSet;

        public HashSet<FolderData> getFolderDataSet() {
            return folderDataSet;
        }
    }

}


