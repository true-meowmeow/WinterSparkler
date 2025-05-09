package swing.ui.pages.home;

import swing.objects.core.Pages;
import swing.objects.core.PanelType;
import swing.ui.VariablesUI;
import swing.ui.pages.home.collections.CollectionsFrame;
import swing.ui.pages.home.play.PlayFrame;
import swing.ui.pages.home.queue.QueueFrame;
import swing.ui.pages.home.series.SeriesFrame;


public class PageHome extends Pages implements VariablesUI {

    public PageHome() {
        super(PanelType.GRID, true);
        add(new LeftSidePageHome(), menuGridBagConstraintsX(0, weightLeftSidePageHome));
        add(new RightSidePageHome(), menuGridBagConstraintsX(1, weightRightSidePageHome));
    }
}

class LeftSidePageHome extends Pages implements VariablesUI {

    public LeftSidePageHome() {
        super(PanelType.GRID);
        add(new CollectionsFrame(), menuGridBagConstraintsX(0, weightCollectionsFrame));
        add(new SeriesFrame(), menuGridBagConstraintsX(1, weightSeriesFrame));

    }
}

class RightSidePageHome extends Pages implements VariablesUI {

    public RightSidePageHome() {
        super(PanelType.GRID);
        add(new PlayFrame(), menuGridBagConstraintsX(0, weightPlayFrame));
        add(new QueueFrame(), menuGridBagConstraintsX(1, weightQueueFrame));
    }
}
