package swing.pages.home.play;

import swing.objects.JPanelBorder;

import javax.swing.*;
import java.awt.*;

import static core.Methods.createBorder;

public class PlayPanel extends JPanelBorder {
    public PlayPanel() {
        super(true);
        add(new PlaylistPanel(), BorderLayout.CENTER);
        add(new PlayerPanel(), BorderLayout.SOUTH);

        setPreferredSize(new Dimension(0, 0));
        setMinimumSize(new Dimension(0, 0));
    }
    private void huinya(JPanel panel) { //movablya resizing panol

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
        add(Box.createVerticalStrut(400));
    }
}