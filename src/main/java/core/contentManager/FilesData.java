package core.contentManager;


import java.util.*;

public class FilesData {

    private String rootPath;     //Корневой путь
    private List<FileData> fileData;
    private TreeSet<String> pathsHashSet;   //Список всех папок с сортировкой по имени

    public FilesData(String rootPath) {
        this.rootPath = rootPath;
        this.fileData = new ArrayList<>();
        this.pathsHashSet = new TreeSet<>(String::compareToIgnoreCase);
    }

    public String getRootPath() {
        return rootPath;
    }

    public List<FileData> getFileData() {
        return fileData;
    }

    public void addFileData(FileData fd) {
        fileData.add(fd);
        addPathHashSet(fd.relativePath);
    }

    private void addPathHashSet(String path) {
        pathsHashSet.add(path);
    }

    public Set<String> getPathsHashSet() {
        return pathsHashSet;
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
        private String fileName;     // Например, "song.mp3"
        private String relativePath; // Относительный путь до папки (например, "Album1/")
        private String extension;    // Расширение файла без точки, например "mp3"

        public FileData(String fileName, String relativePath, String extension) {
            this.fileName = fileName;
            this.relativePath = relativePath;
            this.extension = extension;
        }

        public String getFileName() {
            return fileName;
        }

        public String getRelativePath() {
            return relativePath;
        }

        public String getExtension() {
            return extension;
        }

        @Override
        public String toString() {
            return "FileData{fileName='" + fileName + "', relativePath='" + relativePath + "', extension='" + extension + "'}";
        }
    }
}
