package swing.elements.pages.home.repository.collections.body.panels;

import swing.core.dropper.DropPanel;
import swing.elements.pages.home.SwingHomeVariables;
import swing.elements.pages.home.repository.collections.body.droppers.DropTargetNewCollection;

import javax.swing.*;

public class BottomAddCollectionGroupPanel extends DropPanel implements SwingHomeVariables {
    public static final String PROPERTY_NAME = "_BOTTOM_ADD_COLLECTION_PANEL_GROUP_";

    public BottomAddCollectionGroupPanel() {
        super(PROPERTY_NAME, new DropTargetNewCollection(false));

        int height = HEIGHT_ADD_COLLECTION_PANEL;
        setPreferredSize(MAX_INT, height);
        add(new JLabel(" + New group of collections"));

        setVisible(false);
    }
}
