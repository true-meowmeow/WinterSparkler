package core.cardPanels;

import core.layouts.Cols;
import core.layouts.ThreeColumnLayout;
import core.panels.*;
import core.objects.JPanelCustom;

import javax.swing.*;
import java.awt.*;

public class ManagePanel extends JPanelCustom {
    public ManagePanel(PanelManager panelManager) {


        ThreeColumnLayout layout = new ThreeColumnLayout();
        setLayout(layout);

        JPanel col1 = new Panel1();
        JPanel col2 = new Panel2();
        JPanel col3 = new MergedInnerGridPanel();

        add(col1, Cols.COL1);
        add(col2, Cols.COL2);
        add(col3, Cols.COL3);
    }
}
