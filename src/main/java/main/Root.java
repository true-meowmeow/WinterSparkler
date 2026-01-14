package main;


import ui.cards.home.HomePanel;
import ui.cards.library.LibraryPanel;
import ui.cards.manage.ManagePanel;
import ui.cards.settings.SettingsPanel;
import ui.components.BasePanel;
import ui.layout.LayoutType;
import ui.navigation.AppTab;
import ui.transfer.ItemTransferModel;

import java.awt.*;

public class Root extends BasePanel {

    public Root() {
        super(LayoutType.CARD_LAZY);

        ItemTransferModel model = new ItemTransferModel();

        HomePanel homePanel = new HomePanel();
        LibraryPanel libraryPanel = new LibraryPanel(model);
        ManagePanel managePanel = new ManagePanel(model);
        SettingsPanel settingsPanel = new SettingsPanel();

        add(homePanel, AppTab.HOME.name());
        add(libraryPanel, AppTab.LIBRARY.name());
        add(managePanel, AppTab.MANAGE.name());
        add(settingsPanel, AppTab.SETTINGS.name());

        showCard(AppTab.DEFAULT_TAB);
    }

    public void showCard(AppTab tab) {
        CardLayout cl = (CardLayout) getLayout();
        cl.show(this, tab.name());
    }

}
