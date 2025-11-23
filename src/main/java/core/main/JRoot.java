package core.main;

import core.cardPanels.HomePanel;
import core.cardPanels.LibraryPanel;
import core.cardPanels.ManagePanel;
import core.cardPanels.SettingsPanel;
import core.main.check.PanelType;
import core.main.titleMenuBar.Tab;
import core.objects.JPanelCustom;
import core.panels.PanelManager;

import java.awt.*;

public class JRoot extends JPanelCustom {

    PanelManager panelManager = new PanelManager();

    public JRoot() {
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
