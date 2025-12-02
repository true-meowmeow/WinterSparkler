package core.swing.panels;

import core.objects.GPanel;

import java.awt.*;

public class ExplorerPanel extends GPanel {

    public ExplorerPanel() {
    }

    public void init() {

        setBackground(Color.GREEN);
        add(new Label("explorer"));
        System.out.println("2");
    }
}