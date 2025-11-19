package core.cols;

import core.configOld.Colors;
import core.configOld.Fonts;

import javax.swing.*;
import java.awt.*;

public class Cell extends JPanel {

    public Cell(String title, Color bg) {
        super(new BorderLayout());

        setBackground(bg);

        JLabel lbl = new JLabel("Panel " + title, SwingConstants.CENTER);
        lbl.setForeground(Colors.TEXT_COLOR);
        lbl.setFont(Fonts.CELL);

        add(lbl, BorderLayout.CENTER);
    }
}
