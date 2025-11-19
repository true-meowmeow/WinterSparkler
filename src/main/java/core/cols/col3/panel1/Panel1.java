package core.cols.col3.panel1;

import core.basics.JPanelCustom;
import core.config.ThemeProperties;

import javax.swing.*;
import java.awt.*;

public class Panel1 extends JPanelCustom {

    private final ThemeProperties theme = ThemeProperties.get();

    public Panel1() {

        setBackground(theme.gridPanelOneColor());

        JLabel lbl = new JLabel("Panel 1", SwingConstants.CENTER);
        lbl.setForeground(ThemeProperties.get().textColor());
        lbl.setFont(ThemeProperties.get().cellFont());

        add(lbl, BorderLayout.CENTER);
    }
}
