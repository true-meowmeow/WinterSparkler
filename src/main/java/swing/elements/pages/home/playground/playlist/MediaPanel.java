package swing.elements.pages.home.playground.playlist;

import swing.core.basics.JPanelCustom;

import java.awt.*;

public class MediaPanel extends JPanelCustom {

    private static final int PANEL_HEIGHT = 80;

    public MediaPanel() {

        setBackground(Color.WHITE);
        createBorder();

        add(new Label("media"), BorderLayout.CENTER);

    }
}
