package swing.elements.pages.home.repository.collections;

import swing.core.basics.JPanelCustom;
import swing.elements.pages.home.repository.collections.body.panels.CollectionsPanel;

import java.awt.*;

public class CollectionsFrame extends JPanelCustom {

    public CollectionsFrame() {
        super(true);
        add(new CollectionsPanel(), BorderLayout.CENTER);
    }
}

