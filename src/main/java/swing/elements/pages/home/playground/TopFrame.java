package swing.elements.pages.home.playground;

import core.main.config.LayoutProperties;
import swing.core.basics.JPanelCustom;
import swing.core.basics.Pages;
import swing.core.basics.PanelType;
import swing.elements.pages.home.playground.player.PlayerPanel;
import swing.elements.pages.home.playground.playlist.PlaylistPanel;
import swing.elements.pages.home.playground.queue.QueuePanel;

import java.awt.*;

public class TopFrame extends Pages {

    public TopFrame() {
        super(PanelType.GRID);

        add(new PlaylistPanel(), menuGridBagConstraintsX(0, LayoutProperties.get().getWeightPlayFrame()));
        add(new QueuePanel(), menuGridBagConstraintsX(1, LayoutProperties.get().getWeightQueueFrame()));
    }
}
