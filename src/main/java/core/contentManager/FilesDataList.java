package core.contentManager;

import java.beans.PropertyChangeSupport;

import java.util.ArrayList;
import java.util.List;


public class FilesDataList {

    private List<FilesData> filesDataListAll;
    private List<FilesData> filesDataListFiltered;

    public FilesDataList() {
        filesDataListAll = new ArrayList<>();
        filesDataListFiltered = new ArrayList<>();
    }

    public List<FilesData> getFilesDataListAll() {
        return filesDataListAll;
    }

    public List<FilesData> getFilesDataListFiltered() {
        return filesDataListFiltered;
    }
}

