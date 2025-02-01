package swing.pages.home;

import swing.objects.JPanelBorder;

import javax.swing.*;
import java.awt.*;

import static core.Methods.createBorder;
import static core.Methods.createVerticalPanel;

public class QueuePanel extends JPanelBorder {

    public QueuePanel() {
        setLayout(new BorderLayout());



        // Центральная панель — ДОБАВЛЯЕМ ПУСТОЙ КОМПОНЕНТ ДЛЯ РАСТЯЖКИ
        JPanel contentPanel = new JPanel(new BorderLayout());
        createBorder(contentPanel);
        contentPanel.setBackground(Color.LIGHT_GRAY);
        contentPanel.add(new JLabel(), BorderLayout.CENTER); // Пустой компонент
        add(contentPanel, BorderLayout.CENTER);

        JPanel bottomPanel = createVerticalPanel(250);

        createBorder(bottomPanel);
        add(bottomPanel, BorderLayout.SOUTH);
        //huinya(bottomPanel);

        // УБИРАЕМ ЛЮБЫЕ ФИКСИРОВАННЫЕ РАЗМЕРЫ У ПАНЕЛИ
        setMinimumSize(new Dimension(0, 0));
        setPreferredSize(new Dimension(0, 0));
    }
}
