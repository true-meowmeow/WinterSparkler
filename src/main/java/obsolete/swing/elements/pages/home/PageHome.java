package obsolete.swing.elements.pages.home;

import obsolete.core.main.config.LayoutProperties;
import obsolete.swing.core.basics.JPanelCustom;
import obsolete.swing.core.basics.Pages;
import obsolete.swing.core.basics.PanelType;
import obsolete.swing.elements.pages.home.playground.BottomFrame;
import obsolete.swing.elements.pages.home.playground.TopFrame;
import obsolete.swing.elements.pages.home.repository.collections.CollectionsFrame;
import obsolete.swing.elements.pages.home.repository.series.SeriesFrame;

import java.awt.*;

import static obsolete.swing.elements.pages.home.RightSideMode.DEFAULT_TAB;


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
        TopFrame topFrame = new TopFrame();
        BottomFrame bottomFrame = new BottomFrame();

        RightSideContentController controller = RightSideContentController.getInstance();
        controller.reset();
        controller.register(topFrame);
        controller.register(bottomFrame);
        controller.showMode(DEFAULT_TAB);

        add(topFrame, BorderLayout.CENTER);
        add(bottomFrame, BorderLayout.SOUTH);
    }
}
