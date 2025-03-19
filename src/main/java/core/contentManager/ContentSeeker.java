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
        FileDataProcessor processor = new FileDataProcessor();                                  //todo Жиденький класс, избавиться!
        FilesDataMap filesDataMap = processor.processRootPaths(folderEntities.getAllPaths());
        return filesDataMap;
    }
}
