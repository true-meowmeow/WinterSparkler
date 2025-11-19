package core.cols.col3.panel2;

import core.basics.JPanelCustom;
import core.config.ThemeProperties;

import javax.swing.*;
import java.awt.*;

public class Panel2 extends JPanelCustom {

    private final ThemeProperties theme = ThemeProperties.get();

    public Panel2() {

        setBackground(theme.gridPanelTwoColor());

        JLabel lbl = new JLabel("Panel 2", SwingConstants.CENTER);
        lbl.setForeground(ThemeProperties.get().textColor());
        lbl.setFont(ThemeProperties.get().cellFont());

        add(lbl, BorderLayout.CENTER);
    }
}
