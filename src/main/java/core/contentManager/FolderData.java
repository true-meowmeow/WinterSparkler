package core.contentManager;

import java.util.Comparator;

public class FolderData implements Comparable<FolderData>  {

    Comparator<FolderData> rootPathComparator = Comparator.comparing(FolderData::getRootPath);

    private String fullPath;
    private String rootPath;
    private String relativePath;

    private boolean active = true; //Переключается на false при соответствии условии (Нет медиафайлов и одна папка)

    private String name;            //folder name

    public FolderData(String fullPath, String rootPath, String relativePath) {
        this.fullPath = fullPath;
        this.rootPath = rootPath;
        this.relativePath = relativePath;

        System.out.println(fullPath);

        this.name = name;   //todo метод установки имени.
    }

    @Override
    public int compareTo(FolderData o) {
        return this.fullPath.compareTo(o.fullPath);
    }

    @Override
    public String toString() {
        return "FolderData{" +
                "fullPath='" + fullPath + '\'' +
                '}';
    }

    public String getFullPath() {
        return fullPath;
    }

    public String getRootPath() {
        return rootPath;
    }

    public String getRelativePath() {
        return relativePath;
    }

    public boolean isActive() {
        return active;
    }

    public void deactivate() {
        this.active = false;
    }
}
