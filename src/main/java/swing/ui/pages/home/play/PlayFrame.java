package swing.ui.pages.home.play;

import swing.objects.core.JPanelCustom;
import swing.ui.pages.home.play.view.CombinedPanel;
import swing.ui.pages.home.play.player.PlayerPanel;

import java.awt.*;

public class PlayFrame extends JPanelCustom {

    public PlayFrame() {
        super(true);
        add(CombinedPanel.getInstance(), BorderLayout.CENTER);
        add(new PlayerPanel(), BorderLayout.SOUTH);
    }
}

