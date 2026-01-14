package core.main;


import core.ui.cards.home.HomePanel;
import core.ui.cards.library.LibraryPanel;
import core.ui.cards.manage.ManagePanel;
import core.ui.cards.settings.SettingsPanel;
import core.ui.components.BasePanel;
import core.ui.layout.LayoutType;
import core.ui.navigation.AppTab;
import core.ui.transfer.ItemTransferModel;

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
