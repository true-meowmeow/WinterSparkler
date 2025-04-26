package swing.ui;

import javax.imageio.ImageIO;
import javax.swing.*;
import core.Variables;
import core.VariablesUI;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.*;

public class MainJFrame extends JFrame implements Variables, VariablesUI {
    private ButtonGroup navButtonGroup;
    private JToggleButton btnHome;
    private JToggleButton btnComponent;
    private JToggleButton btnFavorites;
    JPanelTabs jPanelTabs;

    // управление историей вкладок (кроме Settings)
    private final Deque<String> recentTabs = new ArrayDeque<>(2);
    private String tabBeforeSettings;
    private String currentTab = "PageHome";

    public MainJFrame() {
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setTitle(TITLE_NAME);

        try {
            setIconImages(loadAndScaleIcons("/icon/Winter Sparkler and Musical Glow2.png", new int[]{16, 32, 48, 64, 128, 256, 512, 1024}));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // init recent list: по умолчанию Home + Component
        recentTabs.add("PageHome");
        recentTabs.add("PageComponent");

        configureMenuBar();

        jPanelTabs = new JPanelTabs();
        add(jPanelTabs);

        registerKeyBindings();
        setFocusTraversalKeysEnabled(false); // позволяем перехватывать TAB
    }

    // ======== hotkeys ========
    private void registerKeyBindings() {
        InputMap im = getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap am = getRootPane().getActionMap();

        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0), "navHome");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_F2, 0), "navComponent");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_F3, 0), "navFavorites");

        // Settings
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_F12, 0), "toggleSettings");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_BACK_QUOTE, 0), "toggleSettings");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_BACK_QUOTE, InputEvent.SHIFT_DOWN_MASK), "toggleSettings");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_DEAD_GRAVE, 0), "toggleSettings");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_DEAD_TILDE, 0), "toggleSettings");
        im.put(KeyStroke.getKeyStroke('ё'), "toggleSettings");
        im.put(KeyStroke.getKeyStroke('Ё'), "toggleSettings");

        // toggle two last non‑settings tabs
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_TAB, 0), "toggleRecentTabs");

        am.put("navHome", new AbstractAction() {
            @Override public void actionPerformed(ActionEvent e) { btnHome.doClick(); }
        });
        am.put("navComponent", new AbstractAction() {
            @Override public void actionPerformed(ActionEvent e) { btnComponent.doClick(); }
        });
        am.put("navFavorites", new AbstractAction() {
            @Override public void actionPerformed(ActionEvent e) { btnFavorites.doClick(); }
        });

        am.put("toggleSettings", new AbstractAction() {
            @Override public void actionPerformed(ActionEvent e) { toggleSettings(); }
        });
        am.put("toggleRecentTabs", new AbstractAction() {
            @Override public void actionPerformed(ActionEvent e) { switchToOtherRecentTab(); }
        });
    }

    // переключает на Settings и обратно
    private void toggleSettings() {
        if ("Settings".equals(currentTab)) {
            // вернуться на пред. вкладку
            if (tabBeforeSettings != null) {
                openCard(tabBeforeSettings);
                selectNavButton(tabBeforeSettings);
            }
        } else {
            tabBeforeSettings = currentTab;
            openCard("Settings");
            deselectNavigationButtons();
        }
    }

    // TAB -> переключение между двумя последними вкладками
    private void switchToOtherRecentTab() {
        if (recentTabs.size() < 2) return;
        Iterator<String> it = recentTabs.iterator();
        String first = it.next();
        String second = it.next();
        String target = currentTab.equals(first) ? second : first;
        selectNavButton(target); // вызовет openCard через doClick()
    }

    private void selectNavButton(String cardName) {
        switch (cardName) {
            case "PageHome" -> btnHome.doClick();
            case "PageComponent" -> btnComponent.doClick();
            case "Favorites" -> btnFavorites.doClick();
        }
    }

    // centralized show card + учёт истории
    private void openCard(String cardName) {
        jPanelTabs.cardLayout.show(jPanelTabs, cardName);
        if (!"Settings".equals(cardName)) {
            if (!cardName.equals(currentTab)) {
                currentTab = cardName;
                if (recentTabs.isEmpty() || !recentTabs.peekLast().equals(cardName)) {
                    if (recentTabs.size() == 2) recentTabs.removeFirst();
                    recentTabs.addLast(cardName);
                }
            }
        } else {
            currentTab = "Settings";
        }
    }

    private java.util.List<Image> loadAndScaleIcons(String resourcePath, int[] sizes) throws IOException {
        BufferedImage original = ImageIO.read(getClass().getResourceAsStream(resourcePath));
        java.util.List<Image> icons = new ArrayList<>(sizes.length);
        for (int sz : sizes) {
            BufferedImage scaled = new BufferedImage(sz, sz, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g = scaled.createGraphics();
            g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
            g.drawImage(original, 0, 0, sz, sz, null);
            g.dispose();
            icons.add(scaled);
        }
        return icons;
    }

    private void configureMenuBar() {
        menuBar.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 2));

        JPanel navButtonPanel = createNavigationButtons();
        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        leftPanel.add(navButtonPanel);

        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 4, 0));
        rightPanel.add(menuButtonSettings);

        menuButtonSettings.setFocusable(false);
        menuButtonSettings.setFocusPainted(false);
        menuButtonSettings.setPreferredSize(new Dimension(80, 20));
        menuButtonSettings.setMargin(new Insets(0, 4, 0, 4));
        menuButtonSettings.addActionListener(e -> toggleSettings());

        menuBar.add(leftPanel);
        menuBar.add(Box.createGlue());
        menuBar.add(rightPanel);
        setJMenuBar(menuBar);
    }

    JButton menuButtonSettings = new JButton("Settings");
    JMenuBar menuBar = new JMenuBar();

    private void deselectNavigationButtons() {
        if (navButtonGroup != null) navButtonGroup.clearSelection();
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
        navButtonGroup = new ButtonGroup();

        Color bgColor = UIManager.getColor("MenuBar.background");
        Color selectedColor = UIManager.getColor("Component.focusedBorderColor");

        btnHome = createNavButton("Home", "PageHome", bgColor, selectedColor);
        btnHome.setSelected(true);
        btnComponent = createNavButton("Component", "PageComponent", bgColor, selectedColor);
        btnFavorites = createNavButton("Favorites", "Favorites", bgColor, selectedColor);

        navButtonGroup.add(btnHome);
        navButtonGroup.add(btnComponent);
        navButtonGroup.add(btnFavorites);

        panel.add(btnHome);
        panel.add(btnComponent);
        panel.add(btnFavorites);
        return panel;
    }

    private JToggleButton createNavButton(String title, String cardName, Color bg, Color selectedColor) {
        JToggleButton button = new JToggleButton(title);
        button.setFocusable(false);
        button.setBackground(bg);
        button.setOpaque(false);
        button.setBorder(BorderFactory.createEmptyBorder(4, 8, 4, 8));
        button.setFont(button.getFont().deriveFont(Font.PLAIN, 12));
        button.setPreferredSize(new Dimension(NAVIGATION_BUTTON_WIDTH, NAVIGATION_BUTTON_HEIGHT));

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

        button.addActionListener(e -> openCard(cardName));
        return button;
    }
}
