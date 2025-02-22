package core.contentManager;

import java.util.TreeSet;

public class FoldersRootData implements Comparable<FolderData> {

    String rootPath;
    public TreeSet<FolderData> folderData;

    public FoldersRootData(String rootPath) {
        this.rootPath = rootPath;
        folderData = new TreeSet<>();
    }

    public String getRootPath() {
        return rootPath;
    }

    public TreeSet<FolderData> getFolderData() {
        return folderData;
    }

    @Override
    public int compareTo(FolderData o) {
        return 0;
    }
}