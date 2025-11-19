package core.cols;

import core.config.ThemeProperties;

import javax.swing.*;
import java.awt.*;

public class Cell extends JPanel {

    public Cell(String title, Color bg) {
        super(new BorderLayout());

        setBackground(bg);

        JLabel lbl = new JLabel("Panel " + title, SwingConstants.CENTER);
        lbl.setForeground(ThemeProperties.get().textColor());
        lbl.setFont(ThemeProperties.get().cellFont());

        add(lbl, BorderLayout.CENTER);
    }
}
