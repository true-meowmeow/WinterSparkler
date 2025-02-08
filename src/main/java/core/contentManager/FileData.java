package core.contentManager;

public class FileData {
    private String fileName;     // Имя файла, например "song.mp3"
    private String relativePath; // Относительный путь до папки, в которой лежит файл, например "Album1/"
    private String rootPath;     // Путь к корневой директории, откуда начался поиск (например, "C:\Music")
    private String extension;    // Расширение файла без точки, например "mp3"

    public FileData(String rootPath, String fileName, String relativePath, String extension) {
        this.rootPath = rootPath;
        this.fileName = fileName;
        this.relativePath = relativePath;
        this.extension = extension;
    }

    public String getFileName() {
        return fileName;
    }

    /**
     * Возвращает относительный путь до папки, в которой лежит файл (без имени файла).
     */
    public String getRelativePath() {
        return relativePath;
    }

    public String getRootPath() {
        return rootPath;
    }

    public String getExtension() {
        return extension;
    }

    @Override
    public String toString() {
        return "FileData{" +
                "rootPath='" + rootPath + '\'' +
                ", relativePath='" + relativePath + '\'' +
                ", fileName='" + fileName + '\'' +
                ", extension='" + extension + '\'' +
                '}';
    }
}
