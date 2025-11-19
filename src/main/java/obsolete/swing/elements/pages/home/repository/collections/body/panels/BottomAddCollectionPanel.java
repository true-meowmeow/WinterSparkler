package obsolete.swing.elements.pages.home.repository.collections.body.panels;

import obsolete.core.main.config.LayoutProperties;
import obsolete.swing.core.dropper.DropPanel;
import obsolete.swing.elements.pages.home.repository.collections.body.droppers.DropTargetNewCollection;

import javax.swing.*;

public class BottomAddCollectionPanel extends DropPanel {
    public static final String PROPERTY_NAME = "_BOTTOM_ADD_COLLECTION_PANEL_";

    public BottomAddCollectionPanel() {
        super(PROPERTY_NAME, new DropTargetNewCollection(true));

        setPreferredSize(MAX_INT, LayoutProperties.get().getHeightAddCollectionPanel());
        add(new JLabel(" + New Collection"));

        setVisible(false);
    }
}
