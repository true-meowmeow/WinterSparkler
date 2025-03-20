package swing.pages.home.series;

import core.contentManager.FileDataProcessor;
import core.contentManager.FilesDataMap;
import core.contentManager.FolderEntities;

import java.awt.*;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class ObservableCardLayout extends CardLayout {

    private String MANAGE_VIEW;
    private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);


    private FolderEntities folderEntities;
    public ObservableCardLayout(String MANAGE_VIEW, FolderEntities folderEntities) {
        this.MANAGE_VIEW = MANAGE_VIEW;
        this.folderEntities = folderEntities;
    }

    // Методы для добавления/удаления слушателей
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        pcs.removePropertyChangeListener(listener);
    }

    @Override
    public void show(Container parent, String name) {
        if (name.equals(MANAGE_VIEW)) {
            fireBeforeSwitchEvent();
        }
        super.show(parent, name);
    }

    public FilesDataMap filesDataMap = new FilesDataMap();
    private void fireBeforeSwitchEvent() {
        pcs.firePropertyChange("filesDataList", filesDataMap, seek());
    }

    @Deprecated
    private FilesDataMap seek() {
        FileDataProcessor processor = new FileDataProcessor();
        FilesDataMap filesDataMap = processor.processRootPaths(folderEntities.getAllPaths());
        return filesDataMap;
    }
}
