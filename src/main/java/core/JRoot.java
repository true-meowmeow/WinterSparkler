package core;

import core.cols.Col1;
import core.cols.Col2;
import core.cols.Col3;

import javax.swing.*;

import static core.configOld.Breakpoints.*;

public class JRoot extends JPanel {


    public JRoot() {
        ThreeColumnLayout layout = new ThreeColumnLayout(THREE_COL_WIDTH);
        setLayout(layout);

        JPanel col1 = new Col1();
        JPanel col2 = new Col2();
        JPanel col3 = new Col3(layout, this);

        // Можно добавлять либо через enum Role, либо строками "col1"/"col2"/"col3"
        add(col1, Role.COL1);
        add(col2, Role.COL2);
        add(col3, Role.COL3);
    }

    public enum Role {COL1, COL2, COL3}

}
