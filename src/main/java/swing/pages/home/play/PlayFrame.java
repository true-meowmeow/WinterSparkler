package swing.pages.home.play;

import swing.objects.JPanelBorder;
import swing.objects.JPanelGrid;
import swing.objects.MethodsSwing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

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
            JPanel infoControlsPanel = new JPanelBorder();


            {
                JPanel infoPanel = new JPanelGrid();
                GridBagConstraints gbc = new GridBagConstraints();
                gbc.fill = GridBagConstraints.BOTH;
                gbc.weightx = 0.5;

                JButton btn1 = new JButton("1 1");
                gbc.gridx = 0;
                infoPanel.add(btn1, gbc);

                JButton btn2 = new JButton("2 2");
                gbc.gridx = 1;
                infoPanel.add(btn2, gbc);

                infoControlsPanel.add(infoPanel, BorderLayout.WEST);
            }

            {
                JPanel controlsPanel = new JPanelGrid();
                GridBagConstraints gbc = new GridBagConstraints();
                gbc.fill = GridBagConstraints.BOTH;
                gbc.weightx = 0.5;

                JButton btn1 = new JButton("21 1");
                gbc.gridx = 0;
                controlsPanel.add(btn1, gbc);

                JButton btn2 = new JButton("22 2");
                gbc.gridx = 1;
                controlsPanel.add(btn2, gbc);

                infoControlsPanel.add(controlsPanel, BorderLayout.EAST);
            }
            add(infoControlsPanel, BorderLayout.EAST);
        }

        {


            JPanel infoTextPanel = new JPanel(new BorderLayout());

            JPanel info2 = new JPanel(new FlowLayout()); // Используем FlowLayout вместо BorderLayout
            info2.setBorder(BorderFactory.createLineBorder(Color.GREEN));

            JTextArea textArea5 = MethodsSwing.createTextArea("Asu");

            JPanel info3 = new JPanel(new FlowLayout()); // Аналогично
            info3.setBorder(BorderFactory.createLineBorder(Color.RED));

            JTextArea textArea6 = MethodsSwing.createTextArea("Winter Sparkler");

            info2.add(textArea5);
            info3.add(textArea6);

            infoTextPanel.add(info2, BorderLayout.WEST);
            infoTextPanel.add(info3, BorderLayout.EAST);

            add(infoTextPanel, BorderLayout.WEST);

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