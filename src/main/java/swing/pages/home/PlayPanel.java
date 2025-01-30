package swing.pages.home;

import swing.objects.JPanelBorder;

import javax.swing.*;
import java.awt.*;

import static core.Methods.createVerticalPanel;

public class PlayPanel extends JPanelBorder {
    public PlayPanel() {
        setLayout(new BorderLayout());



        // Центральная панель — ДОБАВЛЯЕМ ПУСТОЙ КОМПОНЕНТ ДЛЯ РАСТЯЖКИ
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(Color.LIGHT_GRAY);
        contentPanel.add(new JLabel(), BorderLayout.CENTER); // Пустой компонент
        add(contentPanel, BorderLayout.CENTER);


        add(createVerticalPanel(100), BorderLayout.SOUTH);
        //add(playerJPanel(), BorderLayout.SOUTH);

        // УБИРАЕМ ЛЮБЫЕ ФИКСИРОВАННЫЕ РАЗМЕРЫ У ПАНЕЛИ
        setMinimumSize(new Dimension(0, 0));
        setPreferredSize(new Dimension(0, 0));
    }

    private JPanel playerJPanel() { //for now its fixed
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(Box.createVerticalStrut(10));

        panel.add(new Button("fdsfds"));
        return panel;
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