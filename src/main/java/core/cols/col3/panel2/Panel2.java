package core.cols.col3.panel2;

import core.config.ThemeProperties;

import javax.swing.*;
import java.awt.*;

public class Panel2 extends JPanel {

    private final ThemeProperties theme = ThemeProperties.get();

    public Panel2() {
        super(new BorderLayout());

        setBackground(theme.gridPanelTwoColor());

        JLabel lbl = new JLabel("Panel 2", SwingConstants.CENTER);
        lbl.setForeground(ThemeProperties.get().textColor());
        lbl.setFont(ThemeProperties.get().cellFont());

        add(lbl, BorderLayout.CENTER);
    }
}
