package core.cols.col3.panel4;

import core.config.ThemeProperties;

import javax.swing.*;
import java.awt.*;

public class Panel4 extends JPanel {

    private final ThemeProperties theme = ThemeProperties.get();

    public Panel4() {
        super(new BorderLayout());

        setBackground(theme.gridPanelFourColor());

        JLabel lbl = new JLabel("Panel 4", SwingConstants.CENTER);
        lbl.setForeground(ThemeProperties.get().textColor());
        lbl.setFont(ThemeProperties.get().cellFont());

        add(lbl, BorderLayout.CENTER);
    }
}
