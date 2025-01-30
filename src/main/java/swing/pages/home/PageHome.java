package swing.pages.home;

import swing.objects.JPanelGrid;

import java.awt.*;

import static core.Methods.newGridBagConstraintsX;


public class PageHome extends JPanelGrid {      //todo изменить структуру, надо разделить первые два на JPanel и разделить их, с 3 и 4 также, чтобы они были через другую Jpanel по парам

    private final double weight1 = 0.19;
    private final double weight2 = 0.19;
    private final double weight3 = 0.42;
    private final double weight4 = 0.2;

    public PageHome() {

        setLayout(new GridBagLayout());

/*        addPanel(0, weight1, new ArtistsPanel());
        addPanel(1, weight2, new CollectionsPanel());
        addPanel(2, weight3, new PlayPanel());
        addPanel(3, weight4, new PlayPanel());*/

        add(new LeftPageHome(), newGridBagConstraintsX(0, 0.38));
        add(new RightPageHome(), newGridBagConstraintsX(1, 0.62));

    }


}

class LeftPageHome extends JPanelGrid {

    public LeftPageHome() {
        add(new ArtistsPanel(), newGridBagConstraintsX(0, 0.5));
        add(new CollectionsPanel(), newGridBagConstraintsX(1, 0.5));

    }
}

class RightPageHome extends JPanelGrid {

    public RightPageHome() {
        add(new PlayPanel(), newGridBagConstraintsX(0, 0.7));
        add(new PlayPanel(), newGridBagConstraintsX(1, 0.3));

    }
}
