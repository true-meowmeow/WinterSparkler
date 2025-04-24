package swing.ui.pages.home.collections;

import swing.objects.dropper.DropPanel;

public class EmptyDropPanel extends DropPanel {
    public EmptyDropPanel() {
        super("_EMPTY_SLOT_", new DropTargetNewCollection());
        setDimensions(ZERO, ZERO, MAX);
        setHoverBorderEnabled(false);

        setOpaque(false);
    }
}