package swing.ui.pages.home.collections;

import swing.objects.dropper.DropPanel;
import swing.ui.pages.home.collections.droppers.DropTargetNewCollection;

public class EmptyDropPanel extends DropPanel {
    public EmptyDropPanel() {
        super("_EMPTY_DROP_PANEL_", new DropTargetNewCollection());
        setDimensions(ZERO, ZERO, MAX);
        setHoverBorderEnabled(false);

        setOpaque(false);
    }
}