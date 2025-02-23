package core.contentManager;

import java.util.*;


public class FilesDataList {

    public FilesDataList() {
        mediaFolderDataHashMap = new HashMap<>();
    }

    private HashMap<String, MediaFolderData> mediaFolderDataHashMap;     //rootPath, mediaData

    public void createMediaFolderDataValues(String rootPath) {
        mediaFolderDataHashMap.putIfAbsent(rootPath, new MediaFolderData(rootPath));
    }

    public void addMediaData(String rootPath, MediaData mediaData) {
        mediaFolderDataHashMap.get(rootPath).getMediaDataSet().add(mediaData);
    }
    public void addFolderData(String rootPath, FolderData folderData) {
        mediaFolderDataHashMap.get(rootPath).getFolderDataSet().add(folderData);
    }

    public HashMap<String, MediaFolderData> getMediaFolderDataHashMap() {
        return mediaFolderDataHashMap;
    }

    public static class MediaFolderData {

        private String pathRoot;

        public MediaFolderData(String pathRoot) {
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


