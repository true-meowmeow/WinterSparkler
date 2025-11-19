package core.cols;

import core.configOld.Colors;
import core.configOld.Fonts;

import javax.swing.*;
import java.awt.*;

public class Col2 extends JPanel {

    public Col2() {
        super(new BorderLayout());
        setBackground(Colors.COL2);

        JLabel lbl = new JLabel("COL 2", SwingConstants.CENTER);
        lbl.setForeground(Colors.TEXT_COLOR);
        lbl.setFont(Fonts.CELL.deriveFont(Fonts.DEMO_PANEL_TITLE_SIZE));

        add(lbl, BorderLayout.CENTER);

    }
}
