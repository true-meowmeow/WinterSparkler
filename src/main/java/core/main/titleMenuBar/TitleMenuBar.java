package core.main.titleMenuBar;


import core.config.LayoutProperties;
import core.main.check.Axis;
import core.main.check.PanelType;
import core.objects.JPanelCustom;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;


public final class TitleMenuBar extends JMenuBar {

    private final JPanelTabs tabs = new JPanelTabs();
    private final ButtonGroup navGroup = new ButtonGroup();
    private final Map<Tab, JToggleButton> navButtons = new EnumMap<>(Tab.class);


    public TitleMenuBar() {
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

        style(btn, tab);
        btn.addActionListener(e -> openTab(tab));
        if (btn instanceof JToggleButton jt) navGroup.add(jt);

        if (btn instanceof JToggleButton) navButtons.put(tab, (JToggleButton) btn);
        return btn;
    }

    private void style(AbstractButton b, Tab tab) {
        if (tab == Tab.SETTINGS) {
            styleNavButton(b);
        } else {
            styleNavButton(b);
        }
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
        switch (tab) {
            case HOME -> System.out.println("HOME");
            case LIBRARY -> System.out.println("LIBRARY");
            case MANAGE -> System.out.println("MANAGE");
            case GITHUB_LINK -> System.out.println("GITHUB_LINK");
            case SEARCH -> System.out.println("SEARCH");
            case SETTINGS -> System.out.println("SETTINGS");
            default -> {
            }
        }

        //tabs.showTab(tab);
    }
}

