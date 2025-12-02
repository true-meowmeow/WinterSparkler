package core.panels.cards.libraryCard;

import core.layouts.Cols;
import core.layouts.ThreeColumnLayout;
import core.panels.obsolete.PanelManager;
import core.objects.GPanel;

import javax.swing.*;

public class LibraryPanel extends GPanel {

    public LibraryPanel(PanelManager panelManager) {


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
