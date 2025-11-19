package core.cols;

import core.config.ThemeProperties;

import javax.swing.*;
import java.awt.*;

public class Col1 extends JPanel {

    public Col1() {
        super(new BorderLayout());
        setBackground(ThemeProperties.get().columnOneColor());

        JLabel lbl = new JLabel("COL 1", SwingConstants.CENTER);
        lbl.setForeground(ThemeProperties.get().textColor());
        lbl.setFont(ThemeProperties.get().cellFont().deriveFont(ThemeProperties.get().demoPanelTitleSize()));

        add(lbl, BorderLayout.CENTER);

    }
}
