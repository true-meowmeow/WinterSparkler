package core.swing.cards.libraryCard;

import core.layouts.Cols;
import core.layouts.ThreeColumnLayout;
import core.objects.GPanel;
import core.swing.data.transferTrain.ExplorerModel;

public class LibraryPanel extends GPanel {

    public LibraryPanel(ExplorerModel model) {


        ThreeColumnLayout layout = new ThreeColumnLayout();
        setLayout(layout);

        LibraryPanelsManager panelsManager = new LibraryPanelsManager(model);

        GPanel col1 = panelsManager.getCollectionPanel();
        GPanel col2 = panelsManager.getSeriesPanel();
        LibraryCol3 col3 = new LibraryCol3(panelsManager);


        add(col1, Cols.COL1);
        add(col2, Cols.COL2);
        add(col3, Cols.COL3);
    }
}
