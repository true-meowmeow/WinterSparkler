package core.panels.cards.libraryCard;

import core.layouts.Cols;
import core.layouts.ThreeColumnLayout;
import core.panels.obsolete.PanelManager;
import core.objects.JPanelCustom;
import core.panels.obsolete.Panel1;
import core.panels.obsolete.Panel2;

import javax.swing.*;

public class LibraryPanel extends JPanelCustom {

    public LibraryPanel(PanelManager panelManager) {


        ThreeColumnLayout layout = new ThreeColumnLayout();
        setLayout(layout);

        JPanel col1 = new Panel1();
        JPanel col2 = new Panel2();
        JPanel col3 = new LibraryCol3();

        add(col1, Cols.COL1);
        add(col2, Cols.COL2);
        add(col3, Cols.COL3);
    }
}
