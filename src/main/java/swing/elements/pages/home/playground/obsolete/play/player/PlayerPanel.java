package swing.elements.pages.home.playground.obsolete.play.player;

import swing.core.dropper.DropPanel;
import swing.elements.pages.home.repository.collections.body.droppers.DropTargetNewCollection;

import javax.swing.*;

public class PlayerPanel extends DropPanel {
    public static final String PROPERTY_NAME = "_PLAYER_PANEL_";
    public PlayerPanel() {
        super(PROPERTY_NAME, new DropTargetNewCollection(false));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(Box.createVerticalStrut(300));
    }
}