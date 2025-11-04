package swing.elements.pages.home;

import core.main.config.LayoutProperties;
import swing.core.basics.JPanelCustom;
import swing.core.basics.Pages;
import swing.core.basics.PanelType;
import swing.elements.pages.home.playground.BottomFrame;
import swing.elements.pages.home.playground.TopFrame;
import swing.elements.pages.home.repository.collections.CollectionsFrame;
import swing.elements.pages.home.repository.series.SeriesFrame;

import java.awt.*;


public class PageHome extends Pages {

    public PageHome() {
        super(PanelType.GRID, true);
        add(new LeftSidePageHome(), menuGridBagConstraintsX(0, LayoutProperties.get().getWeightLeftSidePageHome()));
        add(new RightSidePageHome(), menuGridBagConstraintsX(1, LayoutProperties.get().getWeightRightSidePageHome()));
    }
}

class LeftSidePageHome extends Pages {

    public LeftSidePageHome() {
        super(PanelType.GRID);
        add(new CollectionsFrame(), menuGridBagConstraintsX(0, LayoutProperties.get().getWeightCollectionsFrame()));
        add(new SeriesFrame(), menuGridBagConstraintsX(1, LayoutProperties.get().getWeightSeriesFrame()));

    }
}

class RightSidePageHome extends JPanelCustom {

    public RightSidePageHome() {
        add(new TopFrame(), BorderLayout.CENTER);
        add(new BottomFrame(), BorderLayout.SOUTH);
    }
}
