package swing.ui.pages.home.play.player;

import swing.objects.dropper.DropPanel;
import swing.ui.pages.home.collections.droppers.DropTargetNewCollection;

import javax.swing.*;

public class PlayerPanel extends DropPanel {
    public PlayerPanel() {
        super("_EMPTY_SLOT_", new DropTargetNewCollection());
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(Box.createVerticalStrut(300));
    }
}