package core.layouts;

import core.cols.col1.Col1;
import core.cols.col2.Col2;
import core.cols.col3.Col3;
import core.objects.JPanelCustom;

import javax.swing.*;

public class LibraryPanel extends JPanelCustom {

    public enum Role {COL1, COL2, COL3}

    public LibraryPanel() {


        ThreeColumnLayout layout = new ThreeColumnLayout();
        setLayout(layout);

        JPanel col1 = new Col1();
        JPanel col2 = new Col2();
        JPanel col3 = new Col3();

        add(col1, Role.COL1);
        add(col2, Role.COL2);
        add(col3, Role.COL3);
    }
}
