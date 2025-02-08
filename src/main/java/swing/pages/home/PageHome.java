package swing.pages.home;

import core.contentManager.FolderEntities;
import swing.objects.JPanelCustom;
import swing.pages.home.collections.CollectionsFrame;
import swing.pages.home.play.PlayFrame;
import swing.pages.home.play.PlaylistPanel;
import swing.pages.home.queue.QueueFrame;
import swing.pages.home.series.SeriesFrame;

import static swing.objects.MethodsSwing.newGridBagConstraintsX;


public class PageHome extends JPanelCustom {

    public PageHome(PlaylistPanel playlistPanel) {
        super(PanelType.GRID, true);
        add(new LeftSidePageHome(playlistPanel), newGridBagConstraintsX(0, 37));
        add(new RightSidePageHome(playlistPanel), newGridBagConstraintsX(1, 63));
    }
}

class LeftSidePageHome extends JPanelCustom {

    public LeftSidePageHome(PlaylistPanel playlistPanel) {
        super(PanelType.GRID);
        add(new CollectionsFrame(), newGridBagConstraintsX(0, 50));
        add(new SeriesFrame(playlistPanel), newGridBagConstraintsX(1, 50));

    }
}

class RightSidePageHome extends JPanelCustom {

    public RightSidePageHome(PlaylistPanel playlistPanel) {
        super(PanelType.GRID);
        add(new PlayFrame(playlistPanel), newGridBagConstraintsX(0, 75));
        add(new QueueFrame(), newGridBagConstraintsX(1, 25));
    }
}
