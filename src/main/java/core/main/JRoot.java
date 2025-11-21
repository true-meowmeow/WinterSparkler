package core.main;

import core.layouts.HomePanel;
import core.layouts.LibraryPanel;
import core.layouts.ManagePanel;
import core.layouts.SettingsPanel;
import core.main.check.PanelType;
import core.main.titleMenuBar.Tab;
import core.objects.JPanelCustom;

import javax.swing.*;
import java.awt.*;

public class JRoot extends JPanelCustom {


    public JRoot() {
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
