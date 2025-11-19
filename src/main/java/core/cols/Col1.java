package core.cols;

import core.configOld.Colors;
import core.configOld.Fonts;

import javax.swing.*;
import java.awt.*;

public class Col1 extends JPanel {

    public Col1() {
        super(new BorderLayout());
        setBackground(Colors.COL1);

        JLabel lbl = new JLabel("COL 1", SwingConstants.CENTER);
        lbl.setForeground(Colors.TEXT_COLOR);
        lbl.setFont(Fonts.CELL.deriveFont(Fonts.DEMO_PANEL_TITLE_SIZE));

        add(lbl, BorderLayout.CENTER);

    }
}
