package obsolete.swing.elements.pages.home.playground.playlist;

import obsolete.swing.core.basics.JPanelCustom;

import java.awt.*;

public class MediaPanel extends JPanelCustom {


    private static final int height = 80;
    private static final int minWidth = 50;
    private static final int maxWidth = 200;

    private static final int minColumns = 1;
    private static final int maxColumns = 4;

    public MediaPanel() {

        setBackground(Color.WHITE);
        createBorder();

        add(new Label("media"), BorderLayout.CENTER);

    }
}
