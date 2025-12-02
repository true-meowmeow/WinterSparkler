package core.main;

import core.panels.cards.homeCard.HomePanel;
import core.panels.cards.libraryCard.LibraryPanel;
import core.panels.cards.manageCard.ManagePanel;
import core.panels.cards.settingsCard.SettingsPanel;
import core.main.check.PanelType;
import core.main.titleMenuBar.Tab;
import core.objects.GPanel;

import java.awt.*;

public class Root extends GPanel {

    public Root() {
        super(PanelType.CARD);


        LibraryPanel libraryPanel = new LibraryPanel();
        HomePanel homePanel = new HomePanel();
        ManagePanel managePanel = new ManagePanel();
        SettingsPanel settingsPanel = new SettingsPanel();

        add(homePanel, Tab.HOME.name());
        add(libraryPanel, Tab.LIBRARY.name());
        add(managePanel, Tab.MANAGE.name());
        add(settingsPanel, Tab.SETTINGS.name());

        showCard(Tab.DEFAULT_TAB);
    }

    public void showCard(Tab tab) {
        CardLayout cl = (CardLayout) getLayout();
        cl.show(this, tab.name());
    }

}
