package obsolete.swing.elements.pages.home.playground.playlist;

import obsolete.swing.core.basics.JPanelCustom;

import java.awt.*;

public class PlaylistPanel extends JPanelCustom {

    public PlaylistPanel() {

        setBackground(Color.GRAY);

        add(new PlaylistTopPanel(), BorderLayout.NORTH);
        add(new PlaylistField(), BorderLayout.CENTER);
    }
}
