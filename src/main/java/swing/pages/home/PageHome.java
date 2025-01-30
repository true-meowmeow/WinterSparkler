package swing.pages.home;

import swing.objects.JPanelGrid;

import java.awt.*;

import static core.Methods.newGridBagConstraintsX;


public class PageHome extends JPanelGrid {



    public PageHome() {

        add(new LeftPageHome(), newGridBagConstraintsX(0, 38));
        add(new RightPageHome(), newGridBagConstraintsX(1, 62));
    }


}

class LeftPageHome extends JPanelGrid {

    public LeftPageHome() {
        add(new ArtistsPanel(), newGridBagConstraintsX(0, 50));
        add(new CollectionsPanel(), newGridBagConstraintsX(1, 50));

    }
}

class RightPageHome extends JPanelGrid {

    public RightPageHome() {
        add(new PlayPanel(), newGridBagConstraintsX(0, 70));
        add(new QueuePanel(), newGridBagConstraintsX(1, 30));
    }
}
