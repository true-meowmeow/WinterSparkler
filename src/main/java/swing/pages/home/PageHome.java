package swing.pages.home;

import core.contentManager.FolderEntities;
import swing.objects.JPanelCustom;
import swing.pages.home.collections.CollectionsFrame;
import swing.pages.home.play.PlayFrame;
import swing.pages.home.play.PlaylistPanel;
import swing.pages.home.queue.QueueFrame;
import swing.pages.home.series.SeriesFrame;



public class PageHome extends JPanelCustom {

    public PageHome(PlaylistPanel playlistPanel) {
        super(PanelType.GRID, true);
        add(new LeftSidePageHome(playlistPanel), menuGridBagConstraintsX(0, 37));
        add(new RightSidePageHome(playlistPanel), menuGridBagConstraintsX(1, 63));
    }
}

class LeftSidePageHome extends JPanelCustom {

    public LeftSidePageHome(PlaylistPanel playlistPanel) {
        super(PanelType.GRID);
        add(new CollectionsFrame(), menuGridBagConstraintsX(0, 50));
        add(new SeriesFrame(playlistPanel), menuGridBagConstraintsX(1, 50));

    }
}

class RightSidePageHome extends JPanelCustom {

    public RightSidePageHome(PlaylistPanel playlistPanel) {
        super(PanelType.GRID);
        add(new PlayFrame(playlistPanel), menuGridBagConstraintsX(0, 75));
        add(new QueueFrame(), menuGridBagConstraintsX(1, 25));
    }
}
