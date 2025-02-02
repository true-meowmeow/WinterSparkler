package swing.pages.home.series;

import swing.objects.JPanelBorder;
import swing.objects.JPanelGrid;

import javax.swing.*;
import java.awt.*;

public class SeriesFrame extends JPanelBorder {
    public SeriesFrame() {
        super(true);
        add(new ControlsPanel(), BorderLayout.NORTH);
        add(new SeriesPanel(), BorderLayout.CENTER);
    }
}

class ControlsPanel extends JPanelGrid {
    public ControlsPanel() {
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
class SeriesPanel extends JPanelBorder {
    public SeriesPanel() {
        setBackground(Color.LIGHT_GRAY);
    }

}
