package swing.pages.home.play;

import swing.objects.JPanelBorder;
import swing.objects.JPanelGrid;

import javax.swing.*;
import java.awt.*;

public class PlayFrame extends JPanelBorder {
    public PlayFrame() {
        super(true);
        add(new InfoPanel(), BorderLayout.NORTH);
        add(new PlaylistPanel(), BorderLayout.CENTER);
        add(new PlayerPanel(), BorderLayout.SOUTH);
    }
}

class InfoPanel extends JPanelGrid {
    public InfoPanel() {
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

class PlaylistPanel extends JPanelBorder {
    public PlaylistPanel() {
        setBackground(Color.LIGHT_GRAY);
        add(new JLabel(), BorderLayout.CENTER); // Пустой компонент
    }
}

class PlayerPanel extends JPanelBorder {
    public PlayerPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(Box.createVerticalStrut(300));
    }
}
/*    private void huinya(JPanel panel) { //movablya resizing panol

        Component verticalStrut = Box.createVerticalStrut(100);
        panel.add(verticalStrut);

        JButton increaseButton = new JButton("Увеличить");
        JButton decreaseButton = new JButton("Уменьшить");

        increaseButton.addActionListener(e -> changeStrutSize(verticalStrut, 150));
        decreaseButton.addActionListener(e -> changeStrutSize(verticalStrut, 50));
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(increaseButton);
        buttonPanel.add(decreaseButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);
    }
    private void changeStrutSize(Component strut, int newHeight) {
        strut.setPreferredSize(new Dimension(0, newHeight));
        strut.revalidate(); // Пересчитать размеры
        strut.getParent().repaint(); // Перерисовать
    }*/