package swing.pages.home;

import swing.objects.JPanelBorder;

import javax.swing.*;
import java.awt.*;

public class CollectionsPanel extends JPanelBorder {
    public CollectionsPanel() {
        setLayout(new BorderLayout());

        // Кнопки — ИСПОЛЬЗУЕМ ПАНЕЛЬ С ВЕСАМИ
        JPanel buttonsPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 0.5;

        JButton btn1 = new JButton("Кнопка 1");
        gbc.gridx = 0;
        buttonsPanel.add(btn1, gbc);

        JButton btn2 = new JButton("Кнопка 2");
        gbc.gridx = 1;
        buttonsPanel.add(btn2, gbc);

        buttonsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(buttonsPanel, BorderLayout.NORTH);

        // Центральная панель — ДОБАВЛЯЕМ ПУСТОЙ КОМПОНЕНТ
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(Color.LIGHT_GRAY);
        contentPanel.add(new JLabel(), BorderLayout.CENTER);
        add(contentPanel, BorderLayout.CENTER);

        // Кнопка внизу (оставляем)
        JButton button = new JButton("Действие");
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(button);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(buttonPanel, BorderLayout.SOUTH);

        // УБИРАЕМ ФИКСИРОВАННЫЕ РАЗМЕРЫ
        setMinimumSize(new Dimension(0, 0));
        setPreferredSize(new Dimension(0, 0));
    }
}