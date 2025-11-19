package core;

import core.cols.col1.Col1;
import core.cols.col2.Col2;
import core.cols.col3.Col3;
import core.config.BreakpointsProperties;

import javax.swing.*;

public class JRoot extends JPanel {

    public enum Role {COL1, COL2, COL3}

    private final BreakpointsProperties breakpoints = BreakpointsProperties.get();

    public JRoot() {
        ThreeColumnLayout layout = new ThreeColumnLayout(breakpoints.threeColumnWidth());
        setLayout(layout);

        JPanel col1 = new Col1();
        JPanel col2 = new Col2();
        JPanel col3 = new Col3(layout, this);

        // Можно добавлять либо через enum Role, либо строками "col1"/"col2"/"col3"
        add(col1, Role.COL1);
        add(col2, Role.COL2);
        add(col3, Role.COL3);
    }


}
