package core.contentManager;

import com.sun.jna.platform.unix.X11;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;


public class FilesDataList {


    private List<MediaData> mediaDataListAll;
    private List<MediaData> mediaDataListFiltered;

    public FilesDataList() {
        mediaDataListAll = new ArrayList<>();
        mediaDataListFiltered = new ArrayList<>();

        foldersRootData = new TreeSet<>();
    }

    public List<MediaData> getMediaDataListAll() {
        return mediaDataListAll;
    }

    public List<MediaData> getMediaDataListFiltered() {
        return mediaDataListFiltered;
    }


    private TreeSet<FoldersRootData> foldersRootData;

    public void addFoldersRootData(FoldersRootData folderData) {
        foldersRootData.add(folderData);
    }

    public TreeSet<FoldersRootData> getFoldersRootData() {
        return foldersRootData;
    }
}


