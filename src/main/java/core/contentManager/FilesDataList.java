package core.contentManager;

import com.sun.jna.platform.unix.X11;

import java.util.*;


public class FilesDataList {

    public FilesDataList() {
        mediaDataMap = new HashMap<>();
        folderDataMap = new HashMap<>();
    }


    //media
    private HashMap<String, HashSet<MediaData>> mediaDataMap;     //rootPath, mediaData


    public void createMediaDataValues(String rootPath) {
        mediaDataMap.putIfAbsent(rootPath, new HashSet<>());
    }

    public void addMediaData(String rootPath, MediaData mediaData) {
        mediaDataMap.get(rootPath).add(mediaData);
    }

    public HashMap<String, HashSet<MediaData>> getMediaDataMap() {
        return mediaDataMap;
    }


    //folders
    private HashMap<String, HashSet<FolderData>> folderDataMap;     //rootPath, folderData

    public void createFoldersDataValues(String rootPath) {
        folderDataMap.putIfAbsent(rootPath, new HashSet<>());
    }

    public void addFoldersData(String rootPath, FolderData folderData) {
        folderDataMap.get(rootPath).add(folderData);
    }

    public HashMap<String, HashSet<FolderData>> getFolderDataMap() {
        return folderDataMap;
    }

    private HashMap<String, MediaFolderData> mediaFolderDataHashMap;     //rootPath, mediaData

    public static class MediaFolderData {
    }
}


