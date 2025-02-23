package core.contentManager;


import java.util.*;

public class MediaData implements Comparable<MediaData> {    //Содержит информацию о медиа файлах

    private String pathRoot;            // C:\Users\meowmeow\Music\testing\core 1 — копия\
    private String pathRelative;        // dsadsadasd\
    private String pathFull;            // C:\Users\meowmeow\Music\testing\core 1 — копия\dsadsadasd\
    private String pathFullName;        // C:\Users\meowmeow\Music\testing\core 1 — копия\dsadsadasd\Winter Sparkler.flac

    private String nameFull;            // Winter Sparkler.flac
    private String name;                // Winter Sparkler
    private String extension;           // flac

    public MediaData(String pathFull, String pathRoot, String pathRelative, String nameFull) {
        this.pathFull = pathFull;
        this.pathRoot = pathRoot;
        this.pathRelative = pathRelative;
        this.pathFullName = pathRoot + pathRelative + nameFull;

        this.nameFull = nameFull;
        this.name = extractFileName(nameFull);
        this.extension = extractExtension(nameFull);

        //System.out.println(pathFull + " | " + pathRoot + " | " + pathRelative + " | " + nameFull);
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

    public String getPathRoot() {
        return pathRoot;
    }

    public String getPathRelative() {
        return pathRelative;
    }

    public String getPathFull() {
        return pathFull;
    }

    public String getPathFullName() {
        return pathFullName;
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
    public int compareTo(MediaData o) {
        return pathFullName.compareTo(o.pathFullName);
    }
}
