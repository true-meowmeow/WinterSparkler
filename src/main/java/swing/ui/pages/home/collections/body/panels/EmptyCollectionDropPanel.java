package swing.ui.pages.home.collections.body.panels;

import swing.objects.dropper.DropPanel;
import swing.ui.pages.home.collections.body.droppers.DropTargetNewCollection;

public class EmptyCollectionDropPanel extends DropPanel {
    public static final String PROPERTY_NAME = "_EMPTY_COLLECTION_DROP_PANEL_";

    public EmptyCollectionDropPanel() {
        super(PROPERTY_NAME, new DropTargetNewCollection(true));
        setDimensions(ZERO, ZERO, MAX);
        //setHoverBorderEnabled(false);

        setOpaque(false);
    }
}