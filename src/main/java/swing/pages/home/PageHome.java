package swing.pages.home;

import swing.objects.JPanelGrid;
import swing.pages.home.collections.CollectionsPanel;
import swing.pages.home.play.PlayPanel;
import swing.pages.home.queue.QueuePanel;
import swing.pages.home.series.SeriesPanel;

import static core.Methods.newGridBagConstraintsX;


public class PageHome extends JPanelGrid {



    public PageHome() {

        add(new CollectionPageHome(), newGridBagConstraintsX(0, 38));
        add(new PlayerPageHome(), newGridBagConstraintsX(1, 62));
    }


}

class CollectionPageHome extends JPanelGrid {

    public CollectionPageHome() {
        add(new CollectionsPanel(), newGridBagConstraintsX(0, 50));
        add(new SeriesPanel(), newGridBagConstraintsX(1, 50));

    }
}

class PlayerPageHome extends JPanelGrid {

    public PlayerPageHome() {
        add(new PlayPanel(), newGridBagConstraintsX(0, 70));
        add(new QueuePanel(), newGridBagConstraintsX(1, 30));
    }
}
