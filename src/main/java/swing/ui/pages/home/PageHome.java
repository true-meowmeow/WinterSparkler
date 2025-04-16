package swing.ui.pages.home;

import swing.objects.general.JPanelCustom;
import swing.ui.pages.home.collections.CollectionsFrame;
import swing.ui.pages.home.play.CombinedPanel;
import swing.ui.pages.home.play.PlayFrame;
import swing.ui.pages.home.queue.QueueFrame;
import swing.ui.pages.home.series.SeriesFrame;


public class PageHome extends JPanelCustom {

    public PageHome(CombinedPanel combinedPanel) {
        super(PanelType.GRID, true);
        add(new LeftSidePageHome(combinedPanel), menuGridBagConstraintsX(0, 37));
        add(new RightSidePageHome(combinedPanel), menuGridBagConstraintsX(1, 63));
    }
}

class LeftSidePageHome extends JPanelCustom {

    public LeftSidePageHome(CombinedPanel combinedPanel) {
        super(PanelType.GRID);
        add(new CollectionsFrame(), menuGridBagConstraintsX(0, 50));
        add(new SeriesFrame(combinedPanel), menuGridBagConstraintsX(1, 50));

    }
}

class RightSidePageHome extends JPanelCustom {

    public RightSidePageHome(CombinedPanel combinedPanel) {
        super(PanelType.GRID);
        add(new PlayFrame(combinedPanel), menuGridBagConstraintsX(0, 75));
        add(new QueueFrame(), menuGridBagConstraintsX(1, 25));
    }
}
