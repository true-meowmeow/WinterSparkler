package core.contentManager;


import java.util.*;

public class FilesData {

    private String rootPath;     //Корневой путь
    private List<FileData> fileData;
    private TreeSet<String> relativePathsHashSet;   //Список всех папок с сортировкой по имени
    private TreeSet<String> fullPathsHashSet;   //Список всех папок с сортировкой по имени

    public FilesData(String rootPath) {
        this.rootPath = rootPath;
        this.fileData = new ArrayList<>();
        this.relativePathsHashSet = new TreeSet<>(String::compareToIgnoreCase);
        this.fullPathsHashSet = new TreeSet<>(String::compareToIgnoreCase);     //todo нужен ли компаратор?
    }

    public String getRootPath() {
        return rootPath;
    }

    public List<FileData> getFileData() {
        return fileData;
    }

    public void addFileData(FileData fd) {
        fileData.add(fd);
        addRelativePathHashSet(fd.pathRelative);
        addFullPathHashSet(rootPath + "\\" + fd.pathRelative);
    }

    private void addRelativePathHashSet(String path) {
        relativePathsHashSet.add(path);
    }

    public TreeSet<String> getRelativePathsHashSet() {
        return relativePathsHashSet;
    }

    private void addFullPathHashSet(String path) {
        fullPathsHashSet.add(path);
    }

    public TreeSet<String> getFullPathsHashSet() {
        return fullPathsHashSet;
    }



    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Root: ").append(rootPath).append("\n");
        for (FileData fd : fileData) {
            sb.append(fd).append("\n");
        }
        return sb.toString();
    }

    public static class FileData {
        private String pathRoot;            // Например, "C:\Users\meowmeow\Music\testing\core 1 — копия\"
        private String pathRelative;        // Относительный путь от корня до папки (например, "Album1\")
        private String pathFull;            // Полный путь до папки (например, "C:\Users\meowmeow\Music\testing\Album1\")
        private String pathFullName;        // Полный путь до папки (например, "C:\Users\meowmeow\Music\testing\Album1\WS.flac")

        private String nameFull;            // Например, "song.mp3"
        private String name;                // Например, "song"
        private String extension;           // Расширение файла без точки, например "mp3"

        public FileData(String pathRoot, String pathRelative, String nameFull) {
            this.nameFull = nameFull;
            this.name = extractFileName(nameFull);

            this.pathRoot = pathRoot;
            this.pathRelative = pathRelative;
            this.pathFull = pathRoot + pathRelative;
            this.pathFullName = pathRoot + pathRelative + nameFull;
            this.extension = extractExtension(nameFull);
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
                //System.out.println("нет_расширения у файла " + fileName);   //todo Обработать файлы без расширения методом проб разных для работы с файлом, хотя они тогда не попадут в список, это можно делать если принудительно совать папку в WS
                return "нет_расширения";
            }
        }

        public String getNameFull() {
            return nameFull;
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
        public String getExtension() {
            return extension;
        }
        public String getPathRoot() {
            return pathRoot;
        }
        public String getName() {
            return name;
        }

        @Override
        public String toString() {
            return "FileData{fileName='" + nameFull + "', fullNamePath='" + pathFullName + "', extension='" + extension + "'}";
        }
    }
}
