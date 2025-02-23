package core.contentManager;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Objects;


public class FolderData implements Comparable<FolderData> {

    private String pathFull;
    private String pathRoot;
    private String pathRelative;
    private String name;            //folder name

    private boolean active = true; //Переключается на false при соответствии условии (Нет медиафайлов и одна папка)


    public FolderData(String pathFull, String pathRoot, String pathRelative, String name) {
        this.pathFull = pathFull;
        this.pathRoot = pathRoot;
        this.pathRelative = pathRelative;
        this.name = name;

        //System.out.println(pathFull + " | " + pathRoot + " | " + pathRelative + " | " + name);
    }

    public String getPathFull() {
        return pathFull;
    }

    public String getPathRoot() {
        return pathRoot;
    }

    public String getPathRelative() {
        return pathRelative;
    }

    public String getName() {
        return name;
    }

    public boolean isActive() {
        return active;
    }

    public void deactivate() {
        this.active = false;
    }

/*    @Override
    public String toString() {
        return "{path='" + pathFull + '\'' +
                '}';
    }*/

    @Override
    public String toString() {
        return "FolderData{" +
                "pathFull='" + pathFull + '\'' +
                ", pathRoot='" + pathRoot + '\'' +
                ", pathRelative='" + pathRelative + '\'' +
                ", name='" + name + '\'' +
                ", active=" + active +
                '}';
    }

    @Override
    public int compareTo(FolderData o) {
        return pathFull.compareTo(o.pathFull);
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FolderData)) return false;
        FolderData that = (FolderData) o;
        return Objects.equals(pathFull, that.pathFull);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pathFull);
    }
}
