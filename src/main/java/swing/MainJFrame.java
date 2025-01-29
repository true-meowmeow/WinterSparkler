package swing;

import javax.swing.*;

import core.FlatSVGUtils;
import core.MainJFrameElements;
import core.Variables;
import java.awt.*;

public class MainJFrame extends MainJFrameElements implements Variables {

    static int controlButtonSizeHeight = 28;
    static int controlButtonSizeWidth = 47;
    static int buttonPreferredSizeWidth = 90;

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

        menuButtonSettings.setPreferredSize(new Dimension(80, 20));
        menuButtonSettings.setMargin(new Insets(0, 4, 0, 4));

        menuBar.add(leftPanel);
        menuBar.add(Box.createGlue());
        menuBar.add(rightPanel);

        setJMenuBar(menuBar);
    }

    private JPanel createNavigationButtons() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        ButtonGroup group = new ButtonGroup();

        // Стиль для кнопок навигации
        Color bgColor = UIManager.getColor("MenuBar.background");
        Color selectedColor = UIManager.getColor("Component.focusedBorderColor");

        // Кнопки
        btnHome = createNavButton("Home", group, bgColor, selectedColor);
        btnHome.setSelected(true); // Активная по умолчанию

        btnComponent = createNavButton("Component", group, bgColor, selectedColor);
        btnFavorites = createNavButton("Favorites", group, bgColor, selectedColor);

        panel.add(btnHome);
        //panel.add(Box.createHorizontalStrut(5));
        panel.add(btnComponent);
        //panel.add(Box.createHorizontalStrut(5));
        panel.add(btnFavorites);

        return panel;
    }

    private JToggleButton createNavButton(String title, ButtonGroup group, Color bg, Color selectedColor) {
        JToggleButton button = new JToggleButton(title);
        button.setFocusable(false);
        button.setBackground(bg);
        button.setOpaque(false);
        button.setBorder(BorderFactory.createEmptyBorder(4, 8, 4, 8));
        button.setFont(button.getFont().deriveFont(Font.PLAIN, 12));
        button.setPreferredSize(new Dimension(buttonPreferredSizeWidth, controlButtonSizeHeight));

        // Стиль для выбранного состояния
        button.addItemListener(e -> {
            if (button.isSelected()) {
                button.setOpaque(true);
                button.setBackground(selectedColor);
            } else {
                button.setOpaque(false);
                button.setBackground(bg);
            }
        });

        // Обработчик переключения страниц
        button.addActionListener(e -> {
            switch (title) {
                case "Home" -> jPanelTabs.cardLayout.show(jPanelTabs, "PageHome");
                case "Component" -> jPanelTabs.cardLayout.show(jPanelTabs, "PageComponent");
                case "Favorites" -> jPanelTabs.cardLayout.show(jPanelTabs, "Favorites");
            }
        });

        group.add(button);
        return button;
    }

    JPanelTabs jPanelTabs;
}