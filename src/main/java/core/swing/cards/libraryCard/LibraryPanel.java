package core.swing.cards.libraryCard;

import core.layouts.Cols;
import core.layouts.ThreeColumnLayout;
import core.objects.GPanel;

public class LibraryPanel extends GPanel {

    public LibraryPanel() {


        ThreeColumnLayout layout = new ThreeColumnLayout();
        setLayout(layout);

        LibraryPanelsManager panelsManager = new LibraryPanelsManager();

        GPanel col1 = panelsManager.getCollectionPanel();
        GPanel col2 = panelsManager.getSeriesPanel();
        LibraryCol3 col3 = new LibraryCol3(panelsManager);



        add(col1, Cols.COL1);
        add(col2, Cols.COL2);
        add(col3, Cols.COL3);
    }
}
