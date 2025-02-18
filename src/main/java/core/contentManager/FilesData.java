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
        private String pathRoot;     // Например, "song.mp3"
        private String pathRelative; // Относительный путь от корня до папки (например, "Album1/")
        private String name;     // Например, "song.mp3"

        private String pathFull; // Полный путь до папки (например, "C:/Users/meowmeow/Music/testing/Album1/")
        private String pathFullName; // Полный путь до папки (например, "C:/Users/meowmeow/Music/testing/Album1/WS.flac")
        private String extension;    // Расширение файла без точки, например "mp3"

        public FileData(String pathRoot, String name, String pathRelative) {
            this.pathRoot = pathRoot;
            this.pathRelative = pathRelative;
            this.name = name;

            this.pathFull = pathRoot + pathRelative;
            this.pathFullName = pathRoot + pathRelative + name;
            this.extension = extractExtension(name);
        }

        private String extractExtension(String fileName) {
            // Определяем расширение файла (без точки)
            int dotIndex = fileName.lastIndexOf('.');
            if (dotIndex != -1 && dotIndex < fileName.length() - 1) {
                return fileName.substring(dotIndex + 1).toLowerCase();
            } else {
                //System.out.println("нет_расширения у файла " + fileName);   //todo Обработать файлы без расширения методом проб разных для работы с файлом.
                return "нет_расширения";
            }
        }

        public String getName() {
            return name;
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

        @Override
        public String toString() {
            return "FileData{fileName='" + name + "', fullNamePath='" + pathFullName + "', extension='" + extension + "'}";
        }
    }
}
