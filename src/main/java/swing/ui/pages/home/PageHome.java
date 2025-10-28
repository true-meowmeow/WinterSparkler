package swing.ui.pages.home;

import core.config.LayoutSettings;
import swing.objects.core.Pages;
import swing.objects.core.PanelType;
import swing.ui.pages.home.collections.CollectionsFrame;
import swing.ui.pages.home.play.PlayFrame;
import swing.ui.pages.home.queue.QueueFrame;
import swing.ui.pages.home.series.SeriesFrame;


public class PageHome extends Pages {

    public PageHome() {
        super(PanelType.GRID, true);
        add(new LeftSidePageHome(), menuGridBagConstraintsX(0, LayoutSettings.get().getWeightLeftSidePageHome()));
        add(new RightSidePageHome(), menuGridBagConstraintsX(1, LayoutSettings.get().getWeightRightSidePageHome()));
    }
}

class LeftSidePageHome extends Pages {

    public LeftSidePageHome() {
        super(PanelType.GRID);
        add(new CollectionsFrame(), menuGridBagConstraintsX(0, LayoutSettings.get().getWeightCollectionsFrame()));
        add(new SeriesFrame(), menuGridBagConstraintsX(1, LayoutSettings.get().getWeightSeriesFrame()));

    }
}

class RightSidePageHome extends Pages {

    public RightSidePageHome() {
        super(PanelType.GRID);
        add(new PlayFrame(), menuGridBagConstraintsX(0, LayoutSettings.get().getWeightPlayFrame()));
        add(new QueueFrame(), menuGridBagConstraintsX(1, LayoutSettings.get().getWeightQueueFrame()));
    }
}
