package swing.pages.home.series;

import swing.objects.JPanelBorder;
import swing.objects.JPanelGrid;

import javax.swing.*;
import java.awt.*;

public class SeriesPanel extends JPanelBorder {
    public SeriesPanel() {
        super(true);
        add(new ButtonsNorthPanel(), BorderLayout.NORTH);
        add(new SeriesMainPanel(), BorderLayout.CENTER);

        setPreferredSize(new Dimension(0, 0));
        setMinimumSize(new Dimension(0, 0));
    }
}

class SeriesMainPanel extends JPanelBorder {
    public SeriesMainPanel() {
        setBackground(Color.LIGHT_GRAY);
    }
}

class ButtonsNorthPanel extends JPanelGrid {
    public ButtonsNorthPanel() {
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 0.5;

        JButton btn1 = new JButton("Кнопка 1");
        gbc.gridx = 0;
        add(btn1, gbc);

        JButton btn2 = new JButton("Кнопка 2");
        gbc.gridx = 1;
        add(btn2, gbc);
    }
}