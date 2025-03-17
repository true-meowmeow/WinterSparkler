package core.contentManager;

import java.util.HashSet;
import java.util.Objects;


public class FolderData implements Comparable<FolderData> {

    private String pathFull;
    private String pathRoot;
    private String pathRelative;
    private String name;            //folder name

    private boolean active = true;        //Переключается на false при соответствии условии (Нет медиафайлов и одна папка)        ///todo maybe delete; no idea
    private String linkParentPathFull;    //Текстовая ссылка на предыдущую папку, при условии деактивации она меняется на следующую папку
    private String linkNextPathFull;      //Текстовая ссылка на следующую папку, при условии деактивации она меняется на следующую папку

    private Info info = new Info();       //Содержит информацию о папках и медиа файлах внутри папки в виде их полных путей


    public FolderData(String pathFull, String pathRoot, String pathRelative, String name, String linkParentPathFull) {
        this.pathFull = pathFull;
        this.pathRoot = pathRoot;
        this.pathRelative = pathRelative;
        this.name = name;

        this.linkParentPathFull = linkParentPathFull;
        this.linkNextPathFull = pathFull;

/*        System.out.println(pathFull);
        System.out.println(info);
        System.out.println();
        System.out.println();*/

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

    public void deactivate(String linkNextPathFull/*, String linkParentPathFull*/) {
        this.active = false;
        this.linkNextPathFull = linkNextPathFull;
        //this.linkParentPathFull = linkParentPathFull;
    }

    public String getLinkNextPathFull() {
        return linkNextPathFull;
    }

    public String getLinkParentPathFull() {
        return linkParentPathFull;
    }

    public void setLinkParentPathFull(String linkParentPathFull) {
        this.linkParentPathFull = linkParentPathFull;
    }

    @Override
    public String toString() {
        return pathFull;
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
    class Info {
        private HashSet<String> folders = new HashSet<>();
        private HashSet<String> medias = new HashSet<>();

        public void addFolder(String path) {
            folders.add(path);
        }
        public void addMedia(String path) {
            medias.add(path);
        }

        public HashSet<String> getFolders() {
            return folders;
        }

        public HashSet<String> getMedias() {
            return medias;
        }

        @Override
        public String toString() {
            return "Info{" +
                    "folders=" + folders +
                    ", medias=" + medias +
                    '}';
        }
    }

    public Info getInfo() {
        return info;
    }
}
