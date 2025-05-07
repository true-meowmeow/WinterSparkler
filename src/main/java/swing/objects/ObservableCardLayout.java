package swing.objects;

import core.contentManager.FileDataProcessor;
import core.contentManager.FilesDataMap;
import core.contentManager.FolderEntities;
import swing.ui.pages.home.play.view.CombinedPanel;

import java.awt.*;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class ObservableCardLayout extends CardLayout {

    public FilesDataMap filesDataMap = new FilesDataMap();
    private final PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }

    @Override
    public void show(Container parent, String name) {
        if (name.equals(CombinedPanel.MANAGE_VIEW)) {
            propertyChangeSupport.firePropertyChange("filesDataList", filesDataMap, seek());
        }
        super.show(parent, name);
    }


    private FilesDataMap seek() {
        FileDataProcessor processor = new FileDataProcessor();
        FilesDataMap filesDataMap = processor.processRootPaths(FolderEntities.getInstance().getAllPaths());
        return filesDataMap;
    }
}
