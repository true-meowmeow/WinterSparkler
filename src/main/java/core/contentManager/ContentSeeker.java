package core.contentManager;

import java.util.ArrayList;
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
    @Deprecated
    public FilesDataMap seek() {
        FileDataProcessor processor = new FileDataProcessor();

        // Получаем список корневых путей из FolderEntities (предполагается, что возвращается List<String>)
        FilesDataMap filesDataMap = processor.processRootPaths(folderEntities.getAllPaths());

/*        // Для статистики «неотсортированных» собираем все MediaData из всех корневых директорий
        List<MediaData> allFiles = new ArrayList<>();
        for (FilesDataMap.FilesData filesDataHashMap : filesDataMap.getMediaFolderDataHashMap().values()) { //fixme Какой в пизду статистики, иди к чёрту
            allFiles.addAll(filesDataHashMap.getMediaDataSet());
        }*/


        return filesDataMap;
    }
}
