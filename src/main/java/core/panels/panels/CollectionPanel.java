package core.panels.panels;

import core.objects.GPanel;

import java.awt.*;

public class CollectionPanel extends GPanel {

    public CollectionPanel() {

        setBackground(Color.RED);
        createLabel();
        add(new Label("collection"));
    }

    public void createLabel() {
        add(new Label("new text manage panel"));
    }
}
