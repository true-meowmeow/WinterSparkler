package core.main.titleMenuBar;


import core.config.LayoutProperties;
import core.main.check.Axis;
import core.main.check.PanelType;
import core.objects.JPanelCustom;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.function.Consumer;


public final class TitleMenuBar extends JMenuBar {

    private final ButtonGroup navGroup = new ButtonGroup();
    private final Map<Tab, JToggleButton> navButtons = new EnumMap<>(Tab.class);
    private final Consumer<Tab> tabChangeHandler;


    public TitleMenuBar(Consumer<Tab> tabChangeHandler) {
        this.tabChangeHandler = tabChangeHandler;
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));


        add(new BuildPanel(Tab.navTabs(), Axis.LEFT));   // навигация слева
        add(Box.createHorizontalGlue());
        add(new BuildPanel(Tab.sideTabs(), Axis.RIGHT)); // Settings справа

        navButtons.get(Tab.DEFAULT_TAB).setSelected(true);


    }

    private class BuildPanel extends JPanelCustom {
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
}

