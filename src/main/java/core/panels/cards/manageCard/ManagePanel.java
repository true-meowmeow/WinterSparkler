package core.panels.cards.manageCard;

import core.layouts.Cols;
import core.layouts.ThreeColumnLayout;
import core.objects.JPanelCustom;
import core.panels.obsolete.Panel1;
import core.panels.obsolete.Panel2;
import core.panels.obsolete.PanelManager;

import javax.swing.*;

public class ManagePanel extends JPanelCustom {
    public ManagePanel(PanelManager panelManager) {


        ThreeColumnLayout layout = new ThreeColumnLayout();
        setLayout(layout);

        JPanel col1 = new Panel1();
        JPanel col2 = new Panel2();
        JPanel col3 = new ManageCol3();

        add(col1, Cols.COL1);
        add(col2, Cols.COL2);
        add(col3, Cols.COL3);
    }
}
