package swing.ui.pages.home.queue;

import swing.objects.core.JPanelCustom;
import swing.objects.core.PanelType;

import javax.swing.*;
import java.awt.*;

public class QueueFrame extends JPanelCustom {

    public QueueFrame() {
        super(true);

        add(new InfoQueuePanel(), BorderLayout.NORTH);
        add(new QueuePanel(), BorderLayout.CENTER);
        add(new ImagePanel(), BorderLayout.SOUTH);
    }
}

class InfoQueuePanel extends JPanelCustom {
    public InfoQueuePanel() {
        super(PanelType.GRID);
        setBorder(10, 10, 10, 10);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 0.5;

        JButton btn1 = new JButton("Кнопка 1");
        btn1.setFocusable(false);
        btn1.setFocusPainted(false);
        gbc.gridx = 0;
        add(btn1, gbc);

        JButton btn2 = new JButton("Кнопка 2");
        btn2.setFocusable(false);
        btn2.setFocusPainted(false);
        gbc.gridx = 1;
        add(btn2, gbc);
    }
}

class QueuePanel extends JPanelCustom {

    public QueuePanel() {

        setBackground(Color.LIGHT_GRAY);


    }
}

class ImagePanel extends JPanelCustom {
    public ImagePanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(Color.GRAY);
        add(Box.createVerticalStrut(300));
    }
}
