package swing.ui.pages.home.play.player;

import swing.objects.dropper.DropPanel;
import swing.ui.pages.home.collections.droppers.DropTargetNewCollection;

import javax.swing.*;

public class PlayerPanel extends DropPanel {
    public static final String PROPERTY_NAME = "_PLAYER_PANEL_";
    public PlayerPanel() {
        super(PROPERTY_NAME, new DropTargetNewCollection(false));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(Box.createVerticalStrut(300));
    }
}