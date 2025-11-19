package core;

import javax.swing.*;
import java.awt.*;

public class JPanelCustom extends JPanel {


    public JPanelCustom(LayoutManager layout) {
        super(layout);
    }

    public JPanelCustom() {
    }

    public void showComp(Component c, int x, int y, int w, int h) {
        c.setVisible(true);
        c.setBounds(x, y, w, h);
    }

    public void hideComp(Component c) {
        c.setVisible(false);
        c.setBounds(0, 0, 0, 0);
    }

}
