package swing;

import core.contentManager.FolderEntities;
import swing.pages.Favorites;
import swing.pages.PageComponent;
import swing.pages.home.play.CombinedPanel;
import swing.pages.home.play.PlaylistPanel;
import swing.pages.home.settings.PageSettings; // Добавляем импорт
import swing.pages.home.PageHome;

import javax.swing.*;
import java.awt.*;

public class JPanelTabs extends JPanel {
    CardLayout cardLayout = new CardLayout();

    //Objects
    FolderEntities folderEntities = new FolderEntities();
    CombinedPanel combinedPanel = new CombinedPanel(folderEntities);

    //Swing
    PageHome pageHome = new PageHome(combinedPanel);
    PageComponent pageComponent = new PageComponent();
    Favorites favorites = new Favorites();
    PageSettings pageSettings = new PageSettings(folderEntities);


    public JPanelTabs() {
        setVisible(true);
        setLayout(cardLayout);

        add(pageHome, "PageHome");
        add(pageComponent, "PageComponent");
        add(favorites, "Favorites");
        add(pageSettings, "Settings"); // Добавляем в CardLayout
    }
}