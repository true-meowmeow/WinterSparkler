package ui.cards.manage;

import ui.components.BasePanel;
import ui.layout.ThreeColumnLayout;
import ui.layout.ThreeColumnSlot;
import ui.transfer.ItemTransferModel;

public class ManagePanel extends BasePanel {

    public ManagePanel(ItemTransferModel model) {


        ThreeColumnLayout layout = new ThreeColumnLayout();
        setLayout(layout);

        ManagePanels panelsManager = new ManagePanels(model);


        BasePanel col1 = panelsManager.getCollectionPanel();
        BasePanel col2 = panelsManager.getSeriesPanel();
        ManageRightPanel col3 = new ManageRightPanel(panelsManager);

        add(col1, ThreeColumnSlot.COL1);
        add(col2, ThreeColumnSlot.COL2);
        add(col3, ThreeColumnSlot.COL3);
    }
}
