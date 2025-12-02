package core.main.titleMenuBar;


import core.config.LayoutProperties;
import core.main.check.Axis;
import core.main.check.PanelType;
import core.objects.GPanel;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.function.Consumer;


public final class TitleMenuBar extends JMenuBar {

    private final ButtonGroup navGroup = new ButtonGroup();
    private final Map<Tab, JToggleButton> navButtons = new EnumMap<>(Tab.class);
    private final List<AbstractButton> allButtons = new ArrayList<>();
    private final Consumer<Tab> tabChangeHandler;
    private final int collapseWidth = LayoutProperties.get().getTitleMenuBarCollapseWidth();


    public TitleMenuBar(Consumer<Tab> tabChangeHandler) {
        this.tabChangeHandler = tabChangeHandler;
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        setBorder(BorderFactory.createEmptyBorder());

        add(new BuildPanel(Tab.navTabs(), Axis.LEFT));   // навигация слева
        add(Box.createHorizontalGlue());
        add(new BuildPanel(Tab.sideTabs(), Axis.RIGHT)); // Settings справа

        navButtons.get(Tab.DEFAULT_TAB).setSelected(true);
        addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentResized(java.awt.event.ComponentEvent e) {
                System.out.println(e);
                updateButtonVisibility();
            }
        });
        SwingUtilities.invokeLater(this::updateButtonVisibility);
    }

    private class BuildPanel extends GPanel {
        public BuildPanel(List<Tab> tabs, Axis align) {
            super(PanelType.FLOW, align, 0, 0, true);
            for (Tab t : tabs) {
                add(createButton(t));
            }
            setOpaque(false);
        }
    }

    private AbstractButton createButton(Tab tab) {
        AbstractButton btn = tab == Tab.SEARCH ? new JButton(tab.getLabel()) : new JToggleButton(tab.getLabel());

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

    private Tab currentTab = Tab.DEFAULT_TAB;

    public void openTab(Tab tab) {
        if (tab == currentTab) return;
        if (tab != Tab.SEARCH) currentTab = tab;
        showCard(tab);
    }

    private void showCard(Tab tab) {
        if (tab == Tab.SEARCH) {
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

