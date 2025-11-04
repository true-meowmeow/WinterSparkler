package swing.elements.pages.home.playground.playlist;

import swing.core.basics.JPanelCustom;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class PlaylistField extends JPanelCustom {

    public PlaylistField() {
        super();
        setBackground(Color.CYAN);


        add(new MediaPanel());
        add(new MediaPanel());
    }

}
