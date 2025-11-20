package obsolete.swing.elements.pages.home.repository.collections;

import obsolete.swing.core.basics.JPanelCustom;
import obsolete.swing.elements.pages.home.repository.collections.body.panels.CollectionsPanel;

import java.awt.*;

public class CollectionsFrame extends JPanelCustom {

    public CollectionsFrame() {
        super(true);
        add(new CollectionsPanel(), BorderLayout.CENTER);
    }
}

