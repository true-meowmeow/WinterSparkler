package core.contentManager;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class ContentSeeker {

    private FolderEntities folderEntities;

    public ContentSeeker(FolderEntities folderEntities) {
        this.folderEntities = folderEntities;
    }

    /**
     * Ищет файлы по всем заданным корневым путям, выводит статистику и фильтрует аудиофайлы.
     *
     * @return объект FilesDataList, содержащий найденные файлы для каждого корневого пути
     */
    public FilesDataList seek() {
        FileDataProcessor processor = new FileDataProcessor();

        // Получаем список корневых путей из FolderEntities (предполагается, что возвращается List<String>)
        FilesDataList filesDataList = processor.processRootPaths(folderEntities.getAllPaths());

        // Для статистики «неотсортированных» собираем все MediaData из всех корневых директорий
        List<MediaData> allFiles = new ArrayList<>();
        for (HashSet<MediaData> mediaSet : filesDataList.getMediaDataMap().values()) {
            allFiles.addAll(mediaSet);
        }
        // processor.printFileStatistics(allFiles, "неотсортированных");
        // System.out.println();

        // Фильтруем аудиофайлы (например, с расширениями wav, flac, opus, mp3)
        List<MediaData> audioFiles = processor.filterAudioFiles(filesDataList);
        // processor.printFileStatistics(audioFiles, "отфильтрованных (аудио)");
        // System.out.println();

        // Демонстрация – вывод найденных аудиофайлов
        // System.out.println("Найденные аудиофайлы:");
        for (MediaData mediaData : audioFiles) {
            // System.out.println(mediaData);
        }

        return filesDataList;
    }
}
