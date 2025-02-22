package core.contentManager;

import com.sun.jna.platform.unix.X11;

import java.util.*;


public class FilesDataList {


    private List<MediaData> mediaDataListAll;
    private List<MediaData> mediaDataListFiltered;

    public FilesDataList() {
        mediaDataListAll = new ArrayList<>();
        mediaDataListFiltered = new ArrayList<>();

        foldersRootData = new HashMap<>();
    }

    public List<MediaData> getMediaDataListAll() {
        return mediaDataListAll;
    }

    public List<MediaData> getMediaDataListFiltered() {
        return mediaDataListFiltered;
    }


    private HashMap<String, FolderRootsData> foldersRootData;
    public void addFoldersData(FolderRootsData folderRootsData, FolderRootsData.FolderData folderData) {
        foldersRootData.;
    }

    public void createFolderRootData(String rootPath) {
        foldersRootData.add(new FolderRootsData(rootPath));
    }

    public TreeSet<FolderRootsData> getFoldersRootData() {
        return foldersRootData;
    }
}


