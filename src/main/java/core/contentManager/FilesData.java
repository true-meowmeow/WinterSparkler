package core.contentManager;


import java.util.*;

public class FilesData {

    private String rootPath;     //Корневой путь
    private List<FileData> fileData;
    private TreeSet<String> relativePathsHashSet;   //Список всех папок
    private TreeSet<String> fullPathsHashSet;   //Список всех папок

    public FilesData(String rootPath) {
        this.rootPath = rootPath;
        this.fileData = new ArrayList<>();
        this.relativePathsHashSet = new TreeSet<>(/*String::compareToIgnoreCase*/);
        this.fullPathsHashSet = new TreeSet<>(/*String::compareToIgnoreCase*/);     //todo нужен ли компаратор?
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
        private String pathRoot;            // C:\Users\meowmeow\Music\testing\core 1 — копия\
        private String pathRelative;        // dsadsadasd\
        private String pathFull;            // C:\Users\meowmeow\Music\testing\core 1 — копия\dsadsadasd\
        private String pathFullName;        // C:\Users\meowmeow\Music\testing\core 1 — копия\dsadsadasd\Winter Sparkler.flac

        private String nameFull;            // Winter Sparkler.flac
        private String name;                // Winter Sparkler
        private String extension;           // flac

        public FileData(String pathRoot, String pathRelative, String nameFull) {
            this.pathRoot = pathRoot;
            this.pathRelative = pathRelative;
            this.pathFull = pathRoot + pathRelative;
            this.pathFullName = pathRoot + pathRelative + nameFull;

            this.nameFull = nameFull;
            this.name = extractFileName(nameFull);
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

/*        @Override
        public String toString() {
            return "pathRoot='" + pathRoot + '\'' +
                    ", pathRelative='" + pathRelative + '\'' +
                    ", pathFull='" + pathFull + '\'' +
                    ", pathFullName='" + pathFullName + '\'' +
                    ", nameFull='" + nameFull + '\'' +
                    ", name='" + name + '\'' +
                    ", extension='" + extension + '\'' +
                    '}';
        }*/

        @Override
        public String toString() {
            return "{root='" + pathRoot +
                    "' name='" + name + '\'' +
                    '}';
        }
    }
}
