package core.contentManager;


import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.List;

public class ContentSeeker {

    FolderEntities folderEntities;

    public ContentSeeker(FolderEntities folderEntities) {
        this.folderEntities = folderEntities;
    }

    public List<FileData> seek() {


        FileDataProcessor processor = new FileDataProcessor();

        // Получаем все файлы из указанных корневых директорий (рекурсивно)
        List<FileData> allFiles = processor.processRootPaths((folderEntities.getAllPaths()));

        // Выводим статистику по неотсортированным (полным) данным
        processor.printFileStatistics(allFiles, "неотсортированных");
        System.out.println();

        // Фильтруем только аудиофайлы (wav, opus, flac, mp3)
        List<FileData> audioFiles = processor.filterAudioFiles(allFiles);

        // Выводим статистику по аудиофайлам
        processor.printFileStatistics(audioFiles, "отфильтрованных (аудио)");
        System.out.println();

        // Для демонстрации можно распечатать все найденные аудиофайлы:
        System.out.println("Найденные аудиофайлы:");
        for (FileData fd : audioFiles) {
            System.out.println(fd);
        }
        System.out.println();
        System.out.println();
        return audioFiles;
    }
}
