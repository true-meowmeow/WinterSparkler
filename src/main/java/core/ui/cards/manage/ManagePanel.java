package core.ui.cards.manage;

import core.ui.components.BasePanel;
import core.ui.layout.ThreeColumnLayout;
import core.ui.layout.ThreeColumnSlot;
import core.ui.transfer.ItemTransferModel;

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
