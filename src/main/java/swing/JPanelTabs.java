package swing;

import swing.pages.Favorites;
import swing.pages.PageComponent;
import swing.pages.home.PageHome;

import javax.swing.*;
import java.awt.*;

public class JPanelTabs extends JPanel {


    CardLayout cardLayout = new CardLayout();

    PageHome pageHome = new PageHome();
    PageComponent pageComponent = new PageComponent();
    Favorites favorites = new Favorites();


    public JPanelTabs() {
        setVisible(true);

        setLayout(cardLayout);
        add(pageHome, "PageHome");
        add(pageComponent, "PageComponent");
        add(favorites, "Favorites");

    }
}
