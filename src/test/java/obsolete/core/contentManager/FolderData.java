package obsolete.core.contentManager;

import java.nio.file.Path;
import java.util.Objects;


public class FolderData implements Comparable<FolderData> {

    private String fullPathString;

    private Path fullPath;
    private Path rootPath;
    private Path relativePath;
    private Path namePath;            //folder name

    private boolean active = false;        //Переключается на false при соответствии условии (Нет медиафайлов и одна папка)
    //todo Может boolean есть ли в нём active чтобы пустую папку выключенную просто так не показывать
    private Path linkParentPathFull;    //Текстовая ссылка на предыдущую папку, при условии деактивации она меняется на следующую папку
    private Path linkNextPathFull;      //Текстовая ссылка на следующую папку, при условии деактивации она меняется на следующую папку

    public FolderData(Path fullPath, Path rootPath, Path relativePath, Path namePath, Path linkParentPathFull) {
        this.fullPathString = fullPath.toString();

        this.fullPath = fullPath;
        this.rootPath = rootPath;
        this.relativePath = relativePath;
        this.namePath = namePath;

        this.linkParentPathFull = linkParentPathFull;
        this.linkNextPathFull = fullPath;

/*        System.out.println(pathFull);
        System.out.println(info);
        System.out.println();
        System.out.println();*/

        //System.out.println(pathFull + " | " + pathRoot + " | " + pathRelative + " | " + name);
    }

    public String getFullPathString() {
        return fullPathString;
    }

    public Path getFullPath() {
        return fullPath;
    }

    public Path getRootPath() {
        return rootPath;
    }

    public Path getRelativePath() {
        return relativePath;
    }

    public Path getNamePath() {
        return namePath;
    }

    public Path getLinkParentPathFull() {
        return linkParentPathFull;
    }

    public Path getLinkNextPathFull() {
        return linkNextPathFull;
    }

    public boolean isActive() {
        return active;
    }

    public void activate() {
        active = true;
    }

/*    public void deactivate(Path linkNextPathFull*//*, String linkParentPathFull*//*) {
        this.active = false;
        this.linkNextPathFull = linkNextPathFull;
        //this.linkParentPathFull = linkParentPathFull;
    }*/

    @Override
    public String toString() {
        return namePath.toString() + " [isActive : " + isActive() + "]";
    }

/*    @Override
    public String toString() {
        return "FolderData{" +
                "pathFull='" + pathFull + '\'' +
                ", pathRoot='" + pathRoot + '\'' +
                ", pathRelative='" + pathRelative + '\'' +
                ", name='" + name + '\'' +
                ", active=" + active +
                '}';
    }*/

    @Override
    public int compareTo(FolderData o) {
        return fullPath.compareTo(o.fullPath);
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FolderData)) return false;
        FolderData that = (FolderData) o;
        return Objects.equals(fullPath, that.fullPath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fullPath);
    }
}
