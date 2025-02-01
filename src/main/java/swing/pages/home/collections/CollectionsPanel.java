package swing.pages.home.collections;

import swing.objects.JPanelBorder;

import javax.swing.*;
import java.awt.*;

public class CollectionsPanel extends JPanelBorder {
    public CollectionsPanel() {

        // Поисковая панель (оставляем как есть)
        JTextField searchField = new JTextField("Введите текст...");
        JPanel searchPanel = new JPanel(new BorderLayout());
        searchPanel.add(searchField, BorderLayout.CENTER);
        searchPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(searchPanel, BorderLayout.NORTH);

        // Центральная панель — ДОБАВЛЯЕМ ПУСТОЙ КОМПОНЕНТ ДЛЯ РАСТЯЖКИ
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(Color.LIGHT_GRAY);
        contentPanel.add(new JLabel(), BorderLayout.CENTER); // Пустой компонент
        add(contentPanel, BorderLayout.CENTER);

        // Кнопка внизу (оставляем как есть)
        JButton button = new JButton("Действие");
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(button);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(buttonPanel, BorderLayout.SOUTH);

        // УБИРАЕМ ЛЮБЫЕ ФИКСИРОВАННЫЕ РАЗМЕРЫ У ПАНЕЛИ
        setMinimumSize(new Dimension(0, 0));
        setPreferredSize(new Dimension(0, 0));
    }
}