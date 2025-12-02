package core.panels.panels;

import core.objects.GPanel;

import java.awt.*;

public class ExplorerPanel extends GPanel {

    public ExplorerPanel() {
        setBackground(Color.GREEN);
        add(new Label("explorer"));
    }
}