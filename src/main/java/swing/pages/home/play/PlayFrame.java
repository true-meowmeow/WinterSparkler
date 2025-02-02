package swing.pages.home.play;

import swing.objects.JPanelBorder;
import swing.objects.JPanelGrid;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static core.Methods.createBorder;

public class PlayFrame extends JPanelBorder {
    public PlayFrame() {
        super(true);
        add(new InfoPanel(), BorderLayout.NORTH);
        add(new PlaylistPanel(), BorderLayout.CENTER);
        add(new PlayerPanel(), BorderLayout.SOUTH);
    }
}

class InfoPanel extends JPanelBorder {
    public InfoPanel() {
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        {
            JTextField collectionField = new JTextField("Winter Sparkler", 20);
            collectionField.setBorder(null); // Убираем рамку
            collectionField.setOpaque(false); // Прозрачный фон
            collectionField.setFont(new Font("Roboto", Font.PLAIN, 16)); // Красивый текст

            collectionField.addActionListener(e -> {
                collectionField.getParent().requestFocusInWindow(); // Убираем фокус с поля
            });

            add(collectionField, BorderLayout.WEST);
        }
        {
            JTextField seriesField = new JTextField("Winter Sparkler", 20);
            seriesField.setBorder(null); // Убираем рамку
            seriesField.setOpaque(false); // Прозрачный фон
            seriesField.setFont(new Font("Roboto", Font.PLAIN, 16)); // Красивый текст

            seriesField.addActionListener(e -> {
                seriesField.getParent().requestFocusInWindow(); // Убираем фокус с поля
            });

            add(seriesField, BorderLayout.WEST);
        }





        {
            JPanel infoControlsPanel = new JPanelBorder();


            {
                JPanel infoPanel = new JPanelGrid();
                GridBagConstraints gbc = new GridBagConstraints();
                gbc.fill = GridBagConstraints.BOTH;
                gbc.weightx = 0.5;

                JButton btn1 = new JButton("Кнопка 1");
                gbc.gridx = 0;
                infoPanel.add(btn1, gbc);

                JButton btn2 = new JButton("Кнопка 2");
                gbc.gridx = 1;
                infoPanel.add(btn2, gbc);

                infoControlsPanel.add(infoPanel, BorderLayout.WEST);
            }

            {
                JPanel controlsPanel = new JPanelGrid();
                GridBagConstraints gbc = new GridBagConstraints();
                gbc.fill = GridBagConstraints.BOTH;
                gbc.weightx = 0.5;

                JButton btn1 = new JButton("Кнопка 1");
                gbc.gridx = 0;
                controlsPanel.add(btn1, gbc);

                JButton btn2 = new JButton("Кнопка 2");
                gbc.gridx = 1;
                controlsPanel.add(btn2, gbc);

                infoControlsPanel.add(controlsPanel, BorderLayout.EAST);
            }
            add(infoControlsPanel, BorderLayout.EAST);
        }


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