package ui.navigation;


import config.LayoutProperties;
import ui.layout.Axis;
import ui.layout.LayoutType;
import ui.components.BasePanel;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.function.Consumer;


public final class TitleMenuBar extends JMenuBar {

    private final ButtonGroup navGroup = new ButtonGroup();
    private final Map<AppTab, JToggleButton> navButtons = new EnumMap<>(AppTab.class);
    private final List<AbstractButton> allButtons = new ArrayList<>();
    private final Consumer<AppTab> tabChangeHandler;
    private final int collapseWidth = LayoutProperties.get().getTitleMenuBarCollapseWidth();


    public TitleMenuBar(Consumer<AppTab> tabChangeHandler) {
        this.tabChangeHandler = tabChangeHandler;
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        setBorder(BorderFactory.createEmptyBorder());

        add(new BuildPanel(AppTab.navTabs(), Axis.LEFT));   // навигация слева
        add(Box.createHorizontalGlue());
        add(new BuildPanel(AppTab.sideTabs(), Axis.RIGHT)); // Settings справа

        navButtons.get(AppTab.DEFAULT_TAB).setSelected(true);
        addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentResized(java.awt.event.ComponentEvent e) {
                System.out.println(e);
                updateButtonVisibility();
            }
        });
        SwingUtilities.invokeLater(this::updateButtonVisibility);
    }

    private class BuildPanel extends BasePanel {
        public BuildPanel(List<AppTab> tabs, Axis align) {
            super(LayoutType.FLOW, align, 0, 0, true);
            for (AppTab t : tabs) {
                add(createButton(t));
            }
            setOpaque(false);
        }
    }

    private AbstractButton createButton(AppTab tab) {
        AbstractButton btn = tab == AppTab.SEARCH ? new JButton(tab.getLabel()) : new JToggleButton(tab.getLabel());

        styleNavButton(btn);
        btn.addActionListener(e -> openTab(tab));
        if (btn instanceof JToggleButton jt) navGroup.add(jt);

        if (btn instanceof JToggleButton) navButtons.put(tab, (JToggleButton) btn);
        allButtons.add(btn);
        return btn;
    }

    private void styleNavButton(AbstractButton button) {
        button.setFocusable(false);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(4, 8, 4, 8));
        button.setFont(button.getFont().deriveFont(Font.PLAIN, 12));
        button.setPreferredSize(LayoutProperties.get().getTitleNavigationButton());
    }

    private AppTab currentTab = AppTab.DEFAULT_TAB;

    public void openTab(AppTab tab) {
        if (tab == currentTab) return;
        if (tab != AppTab.SEARCH) currentTab = tab;
        showCard(tab);
    }

    private void showCard(AppTab tab) {
        if (tab == AppTab.SEARCH) {
            return;
        }

        if (tabChangeHandler != null) {
            tabChangeHandler.accept(tab);
        }
    }

    private void updateButtonVisibility() {
        int width = getWidth();
        boolean shouldHide = width > 0 && width <= collapseWidth;
        for (AbstractButton button : allButtons) {
            button.setVisible(!shouldHide);
        }
        revalidate();
        repaint();
    }
}

