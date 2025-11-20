package obsolete.swing.core.objects;

import obsolete.core.contentManager.FileDataProcessor;
import obsolete.core.contentManager.FilesDataMap;
import obsolete.core.contentManager.FolderEntities;

public class DataManager {
    private static final DataManager INSTANCE = new DataManager();
    public static DataManager getInstance() {
        return INSTANCE;
    }


    private final FileDataProcessor processor = new FileDataProcessor();


    private static FilesDataMap filesDataMap;

    public FilesDataMap getFilesDataMap() {
        return filesDataMap;
    }

    public void setFilesDataMap() {
        filesDataMap = processor.processRootPaths(FolderEntities.getInstance().getAllPaths());
    }
}
