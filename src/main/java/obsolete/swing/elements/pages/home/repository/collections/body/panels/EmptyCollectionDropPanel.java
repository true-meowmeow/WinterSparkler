package obsolete.swing.elements.pages.home.repository.collections.body.panels;

import obsolete.swing.core.dropper.DropPanel;
import obsolete.swing.elements.pages.home.repository.collections.body.droppers.DropTargetNewCollection;

public class EmptyCollectionDropPanel extends DropPanel {
    public static final String PROPERTY_NAME = "_EMPTY_COLLECTION_DROP_PANEL_";

    public EmptyCollectionDropPanel() {
        super(PROPERTY_NAME, new DropTargetNewCollection(true));
        setDimensions(ZERO, ZERO, MAX);
        //setHoverBorderEnabled(false);

        setOpaque(false);
    }
}