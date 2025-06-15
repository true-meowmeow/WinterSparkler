package core.contentManager;


import java.nio.file.Path;
import java.util.*;

//fixme Есть некоторая информация из закрытых источников, что такая реализация компаратора - плохая идея и требуется TreeSet для хранения элементов в отсортированном виде, но это не точно
public class MediaData implements Comparable<MediaData> {    //Содержит информацию о медиа файлах

    private Path fullNamePath;        // C:\Users\meowmeow\Music\testing\core 1 — копия\dsadsadasd\Winter Sparkler.flac
    private Path fullPath;            // C:\Users\meowmeow\Music\testing\core 1 — копия\dsadsadasd
    private Path rootPath;            // C:\Users\meowmeow\Music\testing\core 1 — копия
    private Path relativePath;        // dsadsadasd

    private String nameFull;            // Winter Sparkler.flac
    private String name;                // Winter Sparkler
    private String extension;           // flac

    public MediaData(Path fullNamePath, Path fullPath, Path rootPath, Path relativePath, String nameFull, String name, String extension) {
        this.fullNamePath = fullNamePath;
        this.fullPath = fullPath;
        this.rootPath = rootPath;
        this.relativePath = relativePath;

        this.nameFull = nameFull;
        this.name = name;
        this.extension = extension;

        //System.out.println(fullPath + " | " + rootPath + " | " + relativePath + " | " + nameFull + " | " + fullNamePath );
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

    public Path getFullNamePath() {
        return fullNamePath;
    }

    public String getNameFull() {
        return nameFull;
    }

    public String getName() {
        return name;
    }

    public String getExtension() {
        return extension;
    }

    @Override
    public String toString() {
        return "{" +
                "nameFull='" + nameFull + '\'' +
                '}';
    }

    @Override
    public int compareTo(MediaData o) {
        return fullNamePath.compareTo(o.fullNamePath);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MediaData)) return false;
        MediaData that = (MediaData) o;
        return Objects.equals(fullNamePath, that.fullNamePath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fullNamePath);
    }
}
