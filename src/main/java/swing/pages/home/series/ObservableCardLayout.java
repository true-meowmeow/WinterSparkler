package swing.pages.home.series;

import core.contentManager.ContentSeeker;
import core.contentManager.FilesDataMap;

import java.awt.*;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class ObservableCardLayout extends CardLayout {

    private String MANAGE_VIEW;
    private ContentSeeker contentSeeker;
    private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);

    public FilesDataMap filesDataMap = new FilesDataMap();

    public ObservableCardLayout(String MANAGE_VIEW, ContentSeeker contentSeeker) {
        this.MANAGE_VIEW = MANAGE_VIEW;
        this.contentSeeker = contentSeeker;
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

    private void fireBeforeSwitchEvent() {
        pcs.firePropertyChange("filesDataList", filesDataMap, contentSeeker.seek());
    }
}
