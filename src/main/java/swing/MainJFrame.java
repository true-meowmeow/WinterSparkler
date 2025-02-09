package swing;

import javax.swing.*;

import core.FlatSVGUtils;
import core.MainJFrameElements;
import core.Variables;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainJFrame extends MainJFrameElements implements Variables {

    static int controlButtonSizeHeight = 28;
    static int controlButtonSizeWidth = 47;
    static int buttonPreferredSizeWidth = 90;

    private ButtonGroup navButtonGroup; // Добавляем поле для группы переключателей

    private JToggleButton btnHome;
    private JToggleButton btnComponent;
    private JToggleButton btnFavorites;

    static {
        JFrame.setDefaultLookAndFeelDecorated(true);
        UIManager.put("TitlePane.buttonSize", new Dimension(controlButtonSizeWidth, controlButtonSizeHeight));
    }

    public MainJFrame() {
        setSize(1600, 900);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setTitle(appName + "  " + appVersion);
        setIconImages(FlatSVGUtils.createWindowIconImages("/FlatLaf.svg"));

        configureMenuBar();
        jPanelTabs = new JPanelTabs();
        add(jPanelTabs);
    }

    private void configureMenuBar() {
        menuBar.setBorder(BorderFactory.createEmptyBorder(0, 2, 0, 2));
        //menuBar.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

        // Создаем панель для кнопок навигации
        JPanel navButtonPanel = createNavigationButtons();

        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        leftPanel.add(navButtonPanel);

        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 4, 0));
        rightPanel.add(menuButtonSettings);


        menuButtonSettings.setFocusable(false);
        menuButtonSettings.setFocusPainted(false);

        menuButtonSettings.setPreferredSize(new Dimension(80, 20));
        menuButtonSettings.setMargin(new Insets(0, 4, 0, 4));

        menuButtonSettings.addActionListener(e -> {
            jPanelTabs.cardLayout.show(jPanelTabs, "Settings");
            deselectNavigationButtons();
        });

        menuBar.add(leftPanel);
        menuBar.add(Box.createGlue());
        menuBar.add(rightPanel);

        setJMenuBar(menuBar);
    }

    private void deselectNavigationButtons() {
        // Снимаем выделение через ButtonGroup
        if (navButtonGroup != null) {
            navButtonGroup.clearSelection();
        }

        // Можно обновить стиль кнопок, если требуется:
        Color bgColor = UIManager.getColor("MenuBar.background");
        updateButtonStyle(btnHome, bgColor);
        updateButtonStyle(btnComponent, bgColor);
        updateButtonStyle(btnFavorites, bgColor);
    }

    private void updateButtonStyle(JToggleButton button, Color bgColor) {
        button.setOpaque(false);
        button.setBackground(bgColor);
        button.repaint();
    }

    private JPanel createNavigationButtons() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        navButtonGroup = new ButtonGroup(); // Инициализируем группу

        // Стиль для кнопок навигации
        Color bgColor = UIManager.getColor("MenuBar.background");
        Color selectedColor = UIManager.getColor("Component.focusedBorderColor");

        // Создаём кнопки
        btnHome = createNavButton("Home", bgColor, selectedColor);
        btnHome.setSelected(true); // Активная по умолчанию

        btnComponent = createNavButton("Component", bgColor, selectedColor);
        btnFavorites = createNavButton("Favorites", bgColor, selectedColor);

        // Добавляем кнопки в группу и панель
        navButtonGroup.add(btnHome);
        navButtonGroup.add(btnComponent);
        navButtonGroup.add(btnFavorites);

        panel.add(btnHome);
        panel.add(btnComponent);
        panel.add(btnFavorites);

        return panel;
    }

    private JToggleButton createNavButton(String title, Color bg, Color selectedColor) {
        JToggleButton button = new JToggleButton(title);
        button.setFocusable(false);
        button.setBackground(bg);
        button.setOpaque(false);
        button.setBorder(BorderFactory.createEmptyBorder(4, 8, 4, 8));
        button.setFont(button.getFont().deriveFont(Font.PLAIN, 12));
        button.setPreferredSize(new Dimension(buttonPreferredSizeWidth, controlButtonSizeHeight));

        // Слушатель для изменения стиля при выборе
        button.addItemListener(e -> {
            if (button.isSelected()) {
                button.setOpaque(true);
                button.setBackground(selectedColor);
            } else {
                button.setOpaque(false);
                button.setBackground(bg);
            }
            button.repaint();
        });

        // Обработчик переключения страниц
        button.addActionListener(e -> {
            switch (title) {
                case "Home" -> jPanelTabs.cardLayout.show(jPanelTabs, "PageHome");
                case "Component" -> jPanelTabs.cardLayout.show(jPanelTabs, "PageComponent");
                case "Favorites" -> jPanelTabs.cardLayout.show(jPanelTabs, "Favorites");
            }
        });

        return button;
    }

    JPanelTabs jPanelTabs;
}