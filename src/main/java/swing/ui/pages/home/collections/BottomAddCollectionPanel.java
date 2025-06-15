package swing.ui.pages.home.collections;

import swing.objects.dropper.DropPanel;
import swing.ui.pages.home.SwingHomeVariables;
import swing.ui.pages.home.collections.droppers.DropTargetNewCollection;

import javax.swing.*;

public class BottomAddCollectionPanel extends DropPanel implements SwingHomeVariables {
    public static final String PROPERTY_NAME = "_BOTTOM_ADD_COLLECTION_PANEL_2_";

    public BottomAddCollectionPanel() {
        super(PROPERTY_NAME, new DropTargetNewCollection(true));

        int height = HEIGHT_ADD_COLLECTION_PANEL;
        setPreferredSize(MAX_INT, height);
        add(new JLabel(" + New Collection"));

        /* панель появляется только при перетаскивании */
        setVisible(false);
    }
}