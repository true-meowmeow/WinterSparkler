package swing.ui.pages.home.collections;

import swing.objects.dropper.DropPanel;
import swing.ui.pages.home.collections.droppers.DropTargetNewCollection;

public class EmptyDropPanel extends DropPanel {
    public static final String PROPERTY_NAME = "_EMPTY_DROP_PANEL_";

    public EmptyDropPanel() {
        super(PROPERTY_NAME, new DropTargetNewCollection(true));
        setDimensions(ZERO, ZERO, MAX);
        //setHoverBorderEnabled(false);

        setOpaque(false);
    }
}