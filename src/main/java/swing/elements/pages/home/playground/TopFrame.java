package swing.elements.pages.home.playground;

import core.main.config.LayoutProperties;
import swing.core.basics.Pages;
import swing.elements.pages.home.RightSideMode;
import swing.elements.pages.home.playground.manage.ManagePanel;
import swing.elements.pages.home.playground.playlist.PlaylistPanel;
import swing.elements.pages.home.playground.queue.QueuePanel;

import static swing.elements.pages.home.RightSideMode.DEFAULT_TAB;

public class TopFrame extends ModeCardPanel {

    public TopFrame() {
        Pages home = createGrid(
                column(new PlaylistPanel(), LayoutProperties.get().getWeightPlayFrame()),
                column(new QueuePanel(RightSideMode.HOME), LayoutProperties.get().getWeightQueueFrame())
        );
        Pages manage = createGrid(
                column(new ManagePanel(), 1)
        );

        register(RightSideMode.HOME, home);
        register(RightSideMode.MANAGE, manage);
        showMode(DEFAULT_TAB);
    }
}
