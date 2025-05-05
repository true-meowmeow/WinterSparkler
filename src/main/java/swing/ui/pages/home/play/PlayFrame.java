package swing.ui.pages.home.play;

import swing.objects.dropper.DropPanel;
import swing.objects.general.JPanelCustom;
import swing.ui.pages.home.collections.droppers.DropTargetNewCollection;

import javax.swing.*;
import java.awt.*;

public class PlayFrame extends JPanelCustom {

    public PlayFrame() {
        super(true);
        add(CombinedPanel.getInstance(), BorderLayout.CENTER);
        add(new PlayerPanel(), BorderLayout.SOUTH);
    }
}

class PlayerPanel extends DropPanel {
    public PlayerPanel() {
        super("_EMPTY_SLOT_", new DropTargetNewCollection());
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(Box.createVerticalStrut(300));
    }
}