package swing.objects;

import core.contentManager.FileDataProcessor;
import core.contentManager.FilesDataMap;
import core.contentManager.FolderEntities;
import swing.ui.pages.home.play.view.CombinedPanel;
import swing.ui.pages.home.play.view.ManagePanel;

import java.awt.*;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class ViewCardLayout extends CardLayout {

    private final FileDataProcessor processor = new FileDataProcessor();
    private final PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

    private static final String propertyName = "filesDataList";
    public ViewCardLayout() {
        propertyChangeSupport.addPropertyChangeListener(evt -> {
            if (propertyName.equals(evt.getPropertyName())) {
                ManagePanel.getInstance().updateManagingPanel(((FilesDataMap) evt.getNewValue()));
            }
        });
    }
    @Override
    public void show(Container parent, String name) {
        if (name.equals(CombinedPanel.MANAGE_VIEW)) {
            propertyChangeSupport.firePropertyChange(propertyName, new Object(), processor.processRootPaths(FolderEntities.getInstance().getAllPaths()));
        }
        super.show(parent, name);
    }
}