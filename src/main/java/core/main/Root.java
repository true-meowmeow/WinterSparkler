package core.main;

import core.swing.cards.homeCard.HomePanel;
import core.swing.cards.libraryCard.LibraryPanel;
import core.swing.cards.manageCard.ManagePanel;
import core.swing.cards.settingsCard.SettingsPanel;
import core.main.check.PanelType;
import core.main.titleMenuBar.Tab;
import core.objects.GPanel;
import core.swing.data.transferTrain.ExplorerModel;
import core.swing.data.transferTrain.Item;
import core.swing.data.transferTrain.ItemsPanel;

import java.awt.*;

public class Root extends GPanel {

    public Root() {
        super(PanelType.CARD_LAZY);

        ExplorerModel model = new ExplorerModel();

        HomePanel homePanel = new HomePanel();
        LibraryPanel libraryPanel = new LibraryPanel(model);
        ManagePanel managePanel = new ManagePanel(model);
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
