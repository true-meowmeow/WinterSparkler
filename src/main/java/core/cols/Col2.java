package core.cols;

import core.config.ThemeProperties;

import javax.swing.*;
import java.awt.*;

public class Col2 extends JPanel {

    private static final ThemeProperties THEME = ThemeProperties.get();

    public Col2() {
        super(new BorderLayout());
        setBackground(THEME.columnTwoColor());

        JLabel lbl = new JLabel("COL 2", SwingConstants.CENTER);
        lbl.setForeground(THEME.textColor());
        lbl.setFont(THEME.cellFont().deriveFont(THEME.demoPanelTitleSize()));

        add(lbl, BorderLayout.CENTER);

    }
}
