package core.ui.cards.library;

import core.ui.components.BasePanel;
import core.ui.layout.ThreeColumnLayout;
import core.ui.layout.ThreeColumnSlot;
import core.ui.transfer.ItemTransferModel;

public class LibraryPanel extends BasePanel {

    public LibraryPanel(ItemTransferModel model) {


        ThreeColumnLayout layout = new ThreeColumnLayout();
        setLayout(layout);

        LibraryPanels panelsManager = new LibraryPanels(model);

        BasePanel col1 = panelsManager.getCollectionPanel();
        BasePanel col2 = panelsManager.getSeriesPanel();
        LibraryRightPanel col3 = new LibraryRightPanel(panelsManager);


        add(col1, ThreeColumnSlot.COL1);
        add(col2, ThreeColumnSlot.COL2);
        add(col3, ThreeColumnSlot.COL3);
    }
}
