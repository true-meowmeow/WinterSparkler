package swing.ui.pages.home.collections;

import swing.objects.core.JPanelCustom;

import java.awt.*;

public class CollectionsFrame extends JPanelCustom {

    public CollectionsFrame() {
        super(true);
        add(new SearchPanel(), BorderLayout.NORTH);
        add(new CollectionsPanel(), BorderLayout.CENTER);
    }
}

