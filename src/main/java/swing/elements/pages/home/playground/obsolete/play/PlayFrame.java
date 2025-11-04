package swing.elements.pages.home.playground.obsolete.play;

import swing.core.basics.JPanelCustom;
import swing.elements.pages.home.playground.obsolete.play.view.CombinedPanel;
import swing.elements.pages.home.playground.obsolete.play.player.PlayerPanel;

import java.awt.*;

public class PlayFrame extends JPanelCustom {

    public PlayFrame() {
        super(true);
        add(CombinedPanel.getInstance(), BorderLayout.CENTER);
        add(new PlayerPanel(), BorderLayout.SOUTH);
    }
}

