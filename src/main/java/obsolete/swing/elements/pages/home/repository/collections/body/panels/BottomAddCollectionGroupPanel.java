package obsolete.swing.elements.pages.home.repository.collections.body.panels;

import core.config.LayoutProperties;
import obsolete.swing.core.dropper.DropPanel;
import obsolete.swing.elements.pages.home.repository.collections.body.droppers.DropTargetNewCollection;

import javax.swing.*;

public class BottomAddCollectionGroupPanel extends DropPanel {
    public static final String PROPERTY_NAME = "_BOTTOM_ADD_COLLECTION_PANEL_GROUP_";

    public BottomAddCollectionGroupPanel() {
        super(PROPERTY_NAME, new DropTargetNewCollection(false));

        setPreferredSize(MAX_INT, LayoutProperties.get().getHeightAddCollectionPanel());
        add(new JLabel(" + New group of collections"));

        setVisible(false);
    }
}
