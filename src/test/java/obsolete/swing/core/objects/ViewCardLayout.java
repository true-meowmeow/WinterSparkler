package obsolete.swing.core.objects;

import obsolete.core.contentManager.FilesDataMap;
import obsolete.swing.elements.pages.home.playground.obsolete.play.view.CombinedPanel;
import obsolete.swing.elements.pages.home.playground.obsolete.play.view.ManagePanel;

import java.awt.*;
import java.beans.PropertyChangeSupport;

public class ViewCardLayout extends CardLayout {

    private final PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

    private static final String PROPERTY_NAME = "_FILE_DATA_LIST_";

    public ViewCardLayout() {
        propertyChangeSupport.addPropertyChangeListener(evt -> {
            DataManager.getInstance().setFilesDataMap();
            ManagePanel.getInstance().updateManagingPanel(((FilesDataMap) evt.getNewValue()));
        });
    }

    @Override
    public void show(Container parent, String name) {
        if (name.equals(CombinedPanel.MANAGE_VIEW)) {
            DataManager.getInstance().setFilesDataMap();
            propertyChangeSupport.firePropertyChange(PROPERTY_NAME, null, DataManager.getInstance().getFilesDataMap());
        }
        super.show(parent, name);
    }
}