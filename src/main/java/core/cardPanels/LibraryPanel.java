package core.cardPanels;

import core.layouts.Cols;
import core.layouts.ThreeColumnLayout;
import core.panels.PanelManager;
import core.panels.SplitInnerGridPanel;
import core.objects.JPanelCustom;
import core.panels.Panel1;
import core.panels.Panel2;

import javax.swing.*;

public class LibraryPanel extends JPanelCustom {

    public LibraryPanel(PanelManager panelManager) {


        ThreeColumnLayout layout = new ThreeColumnLayout();
        setLayout(layout);

        JPanel col1 = new Panel1();
        JPanel col2 = new Panel2();
        JPanel col3 = new SplitInnerGridPanel();

        add(col1, Cols.COL1);
        add(col2, Cols.COL2);
        add(col3, Cols.COL3);
    }
}
