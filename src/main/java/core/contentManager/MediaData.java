package core.contentManager;


import java.nio.file.Path;
import java.util.*;

public class MediaData implements Comparable<MediaData> {    //Содержит информацию о медиа файлах

    private Path fullPath;            // C:\Users\meowmeow\Music\testing\core 1 — копия\dsadsadasd
    private Path rootPath;            // C:\Users\meowmeow\Music\testing\core 1 — копия
    private Path relativePath;        // dsadsadasd
    private Path fullNamePath;        // C:\Users\meowmeow\Music\testing\core 1 — копия\dsadsadasd\Winter Sparkler.flac

    private String nameFull;            // Winter Sparkler.flac
    private String name;                // Winter Sparkler
    private String extension;           // flac

    public MediaData(Path fullNamePath, Path fullPath, Path rootPath, Path relativePath, String nameFull, String name, String extension) {
        this.fullPath = fullPath;
        this.rootPath = rootPath;
        this.relativePath = relativePath;
        this.fullNamePath = fullNamePath;

        this.nameFull = nameFull;
        this.name = extractFileName(nameFull);
        this.extension = extractExtension(nameFull);

        //System.out.println(fullPath + " | " + rootPath + " | " + relativePath + " | " + nameFull + " | " + fullNamePath );
    }

    private String extractFileName(String fileName) {
        int dotIndex = fileName.lastIndexOf('.');

        if (dotIndex > 0) {
            // Если точка найдена и не является первым символом,
            // возвращаем часть строки до последней точки
            return fileName.substring(0, dotIndex);
        } else if (dotIndex == 0) {
            // Если точка является первым символом,
            // имя файла отсутствует (например, ".txt")
            return "";
        } else {
            // Если точка не найдена, расширения нет — возвращаем всю строку
            return fileName;
        }
    }

    private String extractExtension(String fileName) {
        // Определяем расширение файла (без точки)
        int dotIndex = fileName.lastIndexOf('.');
        if (dotIndex != -1 && dotIndex < fileName.length() - 1) {
            return fileName.substring(dotIndex + 1).toLowerCase();
        } else {
            //System.out.println("нет_расширения у файла " + fileName);
            // todo Обработать файлы без расширения методом проб разных для работы с файлом, хотя они тогда не попадут в список, это можно делать если принудительно совать папку в WS
            return "нет_расширения";
        }
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
