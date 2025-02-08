package swing.pages.home.series;

import core.contentManager.ContentSeeker;
import core.contentManager.FolderEntities;

import java.awt.*;

public class ObservableCardLayout extends CardLayout {

    private String MANAGE_VIEW;
    private ContentSeeker contentSeeker;

    public ObservableCardLayout(String MANAGE_VIEW, ContentSeeker contentSeeker) {
        this.MANAGE_VIEW = MANAGE_VIEW;
        this.contentSeeker = contentSeeker;
    }

    @Override
    public void show(Container parent, String name) {
        if (name == MANAGE_VIEW) {
            fireBeforeSwitchEvent();
        }
        super.show(parent, name);
    }

    private void fireBeforeSwitchEvent() {
        //todo логика
        contentSeeker.seek();

    }
}