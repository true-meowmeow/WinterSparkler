package swing.elements.pages.home.repository.collections.body.panels;

import swing.core.dropper.DropPanel;
import swing.elements.pages.home.SwingHomeVariables;
import swing.elements.pages.home.repository.collections.body.droppers.DropTargetNewCollection;

import javax.swing.*;

public class BottomAddCollectionPanel extends DropPanel implements SwingHomeVariables {
    public static final String PROPERTY_NAME = "_BOTTOM_ADD_COLLECTION_PANEL_";

    public BottomAddCollectionPanel() {
        super(PROPERTY_NAME, new DropTargetNewCollection(true));

        int height = HEIGHT_ADD_COLLECTION_PANEL;
        setPreferredSize(MAX_INT, height);
        add(new JLabel(" + New Collection"));

        setVisible(false);
    }
}