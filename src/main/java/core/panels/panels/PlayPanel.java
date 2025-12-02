package core.panels.panels;

import core.objects.GPanel;

import java.awt.*;

public class PlayPanel extends GPanel {

    public PlayPanel() {
        setBackground(Color.PINK);
        add(new Label("play Panel"));
    }
}