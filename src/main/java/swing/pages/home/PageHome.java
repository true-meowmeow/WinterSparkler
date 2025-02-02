package swing.pages.home;

import swing.objects.JPanelCustom;
import swing.pages.home.collections.CollectionsFrame;
import swing.pages.home.play.PlayFrame;
import swing.pages.home.queue.QueueFrame;
import swing.pages.home.series.SeriesFrame;

import static swing.objects.MethodsSwing.newGridBagConstraintsX;


public class PageHome extends JPanelCustom {

    public PageHome() {
        super(PanelType.GRID, true);
        add(new CollectionPageHome(), newGridBagConstraintsX(0, 37));
        add(new PlayerPageHome(), newGridBagConstraintsX(1, 63));
    }
}

class CollectionPageHome extends JPanelCustom {

    public CollectionPageHome() {
        super(PanelType.GRID);
        add(new CollectionsFrame(), newGridBagConstraintsX(0, 50));
        add(new SeriesFrame(), newGridBagConstraintsX(1, 50));

    }
}

class PlayerPageHome extends JPanelCustom {

    public PlayerPageHome() {
        super(PanelType.GRID);
        add(new PlayFrame(), newGridBagConstraintsX(0, 75));
        add(new QueueFrame(), newGridBagConstraintsX(1, 25));
    }
}
