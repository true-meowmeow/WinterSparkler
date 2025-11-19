package core.cols.col3.panel3;

import core.config.ThemeProperties;

import javax.swing.*;
import java.awt.*;

public class Panel3 extends JPanel {

    private final ThemeProperties theme = ThemeProperties.get();

    public Panel3() {
        super(new BorderLayout());

        setBackground(theme.gridPanelThreeColor());

        JLabel lbl = new JLabel("Panel 3", SwingConstants.CENTER);
        lbl.setForeground(ThemeProperties.get().textColor());
        lbl.setFont(ThemeProperties.get().cellFont());

        add(lbl, BorderLayout.CENTER);
    }
}
