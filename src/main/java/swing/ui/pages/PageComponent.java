package swing.ui.pages;

import javax.swing.*;
import java.awt.*;

public class PageComponent extends JPanel {

    public PageComponent() {

        setLayout(new GridBagLayout());

        // Создаем 4 панели, которые будем размещать на основной панели
        JPanel part1 = new JPanel();
        part1.setBackground(Color.GREEN); // Цвет для наглядности
        JPanel part2 = new JPanel();
        part2.setBackground(Color.GRAY); // Цвет для наглядности
        JPanel part3 = new JPanel();
        part3.setBackground(Color.BLUE); // Цвет для наглядности
        JPanel part4 = new JPanel();
        part4.setBackground(Color.RED); // Цвет для наглядности

        // Создаем объект GridBagConstraints для настройки позиций и размеров
        GridBagConstraints gbc = new GridBagConstraints();

        // Настроим параметры для первой части
        gbc.gridx = 0; // Первая колонка
        gbc.gridy = 0; // Первая строка
        gbc.gridwidth = 1; // Эта панель занимает одну ячейку по горизонтали
        gbc.gridheight = 1; // Эта панель занимает одну ячейку по вертикали
        gbc.weightx = 0.2; // Задаем вес для изменения размера
        gbc.weighty = 1;   // Панель растягивается по вертикали
        gbc.fill = GridBagConstraints.BOTH; // Заполнение всей доступной области
        add(part1, gbc);

        // Настроим параметры для второй части
        gbc.gridx = 1; // Вторая колонка
        gbc.gridy = 0; // Первая строка
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 0.3; // Эта часть будет занимать 30% ширины
        gbc.weighty = 1;
        add(part2, gbc);

        // Настроим параметры для третьей части
        gbc.gridx = 2; // Третья колонка
        gbc.gridy = 0; // Первая строка
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 0.25; // Эта часть будет занимать 25% ширины
        gbc.weighty = 1;
        add(part3, gbc);

        // Настроим параметры для четвертой части
        gbc.gridx = 3; // Четвертая колонка
        gbc.gridy = 0; // Первая строка
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 0.25; // Эта часть будет занимать 25% ширины
        gbc.weighty = 1;
        add(part4, gbc);



        setPageVisibility(true);
    }

    public void setPageVisibility(boolean visible) {
        setVisible(visible);
    }
}
