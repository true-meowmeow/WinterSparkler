package obsolete.swing.elements.pages.home.playground.playlist;

import obsolete.swing.core.basics.JPanelCustom;

import java.awt.*;

public class PlaylistField extends JPanelCustom {

    public PlaylistField() {
        super();
        setBackground(Color.CYAN);


        add(new MediaPanel());
        add(new MediaPanel());
    }

}
