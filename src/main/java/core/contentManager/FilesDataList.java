package core.contentManager;

import com.sun.jna.platform.unix.X11;

import java.util.*;


public class FilesDataList {


    private List<MediaData> mediaDataListAll;
    private List<MediaData> mediaDataListFiltered;

    public FilesDataList() {
        mediaDataListAll = new ArrayList<>();
        mediaDataListFiltered = new ArrayList<>();

        folderDataMap = new HashMap<>();
    }

    public List<MediaData> getMediaDataListAll() {
        return mediaDataListAll;
    }

    public List<MediaData> getMediaDataListFiltered() {
        return mediaDataListFiltered;
    }


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
}


