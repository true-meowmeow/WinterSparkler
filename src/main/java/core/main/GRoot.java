package core.main;

import core.panels.cards.homeCard.HomePanel;
import core.panels.cards.libraryCard.LibraryPanel;
import core.panels.cards.manageCard.ManagePanel;
import core.panels.cards.settingsCard.SettingsPanel;
import core.main.check.PanelType;
import core.main.titleMenuBar.Tab;
import core.objects.GPanel;
import core.panels.obsolete.PanelManager;

import java.awt.*;

public class GRoot extends GPanel {

    PanelManager panelManager = new PanelManager();

    public GRoot() {
        super(PanelType.CARD);


        LibraryPanel libraryPanel = new LibraryPanel(panelManager);
        HomePanel homePanel = new HomePanel(panelManager);
        ManagePanel managePanel = new ManagePanel(panelManager);
        SettingsPanel settingsPanel = new SettingsPanel(panelManager);

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
