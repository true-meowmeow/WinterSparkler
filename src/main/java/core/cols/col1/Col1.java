package core.cols.col1;

import core.basics.JPanelCustom;
import core.config.ThemeProperties;

import javax.swing.*;
import java.awt.*;

public class Col1 extends JPanelCustom {

    public Col1() {
        setBackground(ThemeProperties.get().columnOneColor());

        JLabel lbl = new JLabel("COL 1", SwingConstants.CENTER);
        lbl.setForeground(ThemeProperties.get().textColor());
        lbl.setFont(ThemeProperties.get().cellFont().deriveFont(ThemeProperties.get().demoPanelTitleSize()));

        add(lbl, BorderLayout.CENTER);

    }
}
