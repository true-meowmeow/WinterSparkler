package core.swing.cards.manageCard;

import core.layouts.Cols;
import core.layouts.ThreeColumnLayout;
import core.objects.GPanel;

public class ManagePanel extends GPanel {
    public ManagePanel() {


        ThreeColumnLayout layout = new ThreeColumnLayout();
        setLayout(layout);

        ManagePanelsManager panelsManager = new ManagePanelsManager();



        GPanel col1 = panelsManager.getCollectionPanel();
        GPanel col2 = panelsManager.getSeriesPanel();
        ManageCol3 col3 = new ManageCol3(panelsManager);

        add(col1, Cols.COL1);
        add(col2, Cols.COL2);
        add(col3, Cols.COL3);
    }
}
