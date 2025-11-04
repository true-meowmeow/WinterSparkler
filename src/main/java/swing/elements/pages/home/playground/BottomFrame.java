package swing.elements.pages.home.playground;

import core.main.config.LayoutProperties;
import swing.core.basics.Pages;
import swing.elements.pages.home.RightSideMode;
import swing.elements.pages.home.playground.cover.CoverPanel;
import swing.elements.pages.home.playground.player.PlayerPanel;
import swing.elements.pages.home.playground.queue.QueuePanel;

import static swing.elements.pages.home.RightSideMode.DEFAULT_TAB;

public class BottomFrame extends ModeCardPanel {

    public BottomFrame() {
        setPreferredSize(MAX_INT, LayoutProperties.get().getBottomFrameHeight());

        Pages home = createGrid(
                column(new PlayerPanel(), LayoutProperties.get().getWeightPlayFrame()),
                column(new CoverPanel(), LayoutProperties.get().getWeightQueueFrame())
        );
        Pages manage = createGrid(
                column(new PlayerPanel(), LayoutProperties.get().getWeightPlayFrame()),
                column(new QueuePanel(RightSideMode.MANAGE), LayoutProperties.get().getWeightQueueFrame())
        );

        register(RightSideMode.HOME, home);
        register(RightSideMode.MANAGE, manage);
        showMode(DEFAULT_TAB);
    }
}
