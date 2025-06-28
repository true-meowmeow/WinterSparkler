package swing.ui.pages.home.collections;

import swing.objects.core.JPanelCustom;
import swing.ui.pages.home.collections.body.panels.CollectionsPanel;
import swing.ui.pages.home.collections.controls.ControlsPanel;

import java.awt.*;

public class CollectionsFrame extends JPanelCustom {

    public CollectionsFrame() {
        super(true);
        add(new ControlsPanel(), BorderLayout.NORTH);
        add(new CollectionsPanel(), BorderLayout.CENTER);
    }
}

