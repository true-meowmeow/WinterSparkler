package swing.ui;

import javax.imageio.ImageIO;
import javax.swing.*;

import core.MainJFrameElements;
import core.Variables;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainJFrame extends MainJFrameElements implements Variables {

    static int controlButtonSizeHeight = 28;
    static int controlButtonSizeWidth = 47;
    static int buttonPreferredSizeWidth = 90;

    private ButtonGroup navButtonGroup;
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

        try {
            setIconImages(loadAndScaleIcons("/WinterSparkler.png", new int[]{16, 32, 48, 64, 128, 256, 512, 1024}));
        } catch (IOException e) {
            e.printStackTrace();
        }

        configureMenuBar();
        jPanelTabs = new JPanelTabs();
        add(jPanelTabs);
    }


    private List<Image> loadAndScaleIcons(String resourcePath, int[] sizes) throws IOException {
        BufferedImage original = ImageIO.read(getClass().getResourceAsStream(resourcePath));
        List<Image> icons = new ArrayList<>(sizes.length);
        for (int sz : sizes) {
            BufferedImage scaled = new BufferedImage(sz, sz, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g = scaled.createGraphics();
            g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                    RenderingHints.VALUE_INTERPOLATION_BICUBIC);
            g.drawImage(original, 0, 0, sz, sz, null);
            g.dispose();
            icons.add(scaled);
        }
        return icons;
    }

    private void configureMenuBar() {
        menuBar.setBorder(BorderFactory.createEmptyBorder(0, 2, 0, 2));
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

        btnHome = createNavButton("Home", bgColor, selectedColor);
        btnHome.setSelected(true);
        btnComponent = createNavButton("Component", bgColor, selectedColor);
        btnFavorites = createNavButton("Favorites", bgColor, selectedColor);

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
