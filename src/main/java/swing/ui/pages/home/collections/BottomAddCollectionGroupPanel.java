package swing.ui.pages.home.collections;

import swing.objects.dropper.DropPanel;
import swing.ui.pages.home.SwingHomeVariables;
import swing.ui.pages.home.collections.droppers.DropTargetNewCollection;

import javax.swing.*;

public class BottomAddCollectionGroupPanel extends DropPanel implements SwingHomeVariables {
    public static final String PROPERTY_NAME = "_BOTTOM_ADD_COLLECTION_PANEL_";

    public BottomAddCollectionGroupPanel() {
        super(PROPERTY_NAME, new DropTargetNewCollection(false));

        int height = HEIGHT_ADD_COLLECTION_PANEL;
        setPreferredSize(MAX_INT, height);
        add(new JLabel(" + New group of collections"));

        /* панель появляется только при перетаскивании */
        setVisible(false);
    }
}
