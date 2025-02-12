package core.contentManager;


import java.util.ArrayList;
import java.util.List;

public class FilesData {

    private String rootPath;     // Корневой путь, откуда начинается поиск
    private List<FileData> fileData;

    public FilesData(String rootPath) {
        this.rootPath = rootPath;
        this.fileData = new ArrayList<>();
    }

    public String getRootPath() {
        return rootPath;
    }

    public List<FileData> getFileData() {
        return fileData;
    }

    public void addFileData(FileData fd) {
        fileData.add(fd);
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
