package swing.pages.home.queue;

import swing.objects.JPanelBorder;

import javax.swing.*;
import java.awt.*;

import static core.Methods.createBorder;

public class QueuePanel extends JPanelBorder {

    public QueuePanel() {
        super(true);
        JPanel contentPanel = new JPanel(new BorderLayout());

        contentPanel.setBackground(Color.LIGHT_GRAY);
        contentPanel.add(new JLabel(), BorderLayout.CENTER); // Пустой компонент
        add(contentPanel, BorderLayout.CENTER);

        setPreferredSize(new Dimension(0, 0));
        setMinimumSize(new Dimension(0, 0));
    }
}
