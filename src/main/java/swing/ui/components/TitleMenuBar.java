package swing.ui.components;

import swing.objects.general.panel.Axis;
import swing.objects.general.panel.JPanelCustom;
import swing.objects.general.panel.PanelType;
import swing.ui.JPanelTabs;
import swing.ui.VariablesUI;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;


public final class TitleMenuBar extends JMenuBar implements VariablesUI {

    private final JPanelTabs tabs;
    private final ButtonGroup navGroup = new ButtonGroup();
    private final Map<Tab, JToggleButton> navButtons = new EnumMap<>(Tab.class);

    private final Deque<Tab> recent = new ArrayDeque<>(2);  // история  вкладок

    {
        recent.addLast(Tab.DEFAULT_TAB);
        recent.addLast(Tab.SETTINGS);
    }

    private Tab current = Tab.DEFAULT_TAB;
    private Tab beforeSettings = Tab.DEFAULT_TAB;

    public TitleMenuBar(JPanelTabs tabs) {
        this.tabs = tabs;
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        //setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

        add(new BuildPanel(Tab.navTabs(), Axis.LEFT));   // навигация слева
        add(Box.createHorizontalGlue());
        add(new BuildPanel(List.of(Tab.SETTINGS), Axis.RIGHT)); // Settings справа

        navButtons.get(Tab.DEFAULT_TAB).setSelected(true);
    }

    private class BuildPanel extends JPanelCustom {
        public BuildPanel(List<Tab> tabs, Axis align) {
            super(PanelType.FLOW, align, 0, 0, false);
            for (Tab t : tabs) {
                add(createButton(t));
            }
            setOpaque(false);
        }
    }

    private AbstractButton createButton(Tab tab) {
        AbstractButton btn = tab == Tab.SETTINGS ? new JButton(tab.label) : new JToggleButton(tab.label);

        style(btn, tab);
        btn.addActionListener(e -> handleClick(tab));
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
        button.setPreferredSize(TITLE_NAVIGATION_BUTTON);
    }

    private void styleSettingsButton(JButton button) {
        button.setFocusable(false);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(4, 8, 4, 8)); // при желании можно другое
        button.setFont(button.getFont().deriveFont(Font.PLAIN, 12));         // и другой шрифт/размер
        button.setPreferredSize(TITLE_NAVIGATION_SETTINGS_BUTTON);
        // тут можно добавить уникальные для Settings эффекты:
        button.setOpaque(true);
    }

    private void handleClick(Tab tab) {
        if (tab == Tab.SETTINGS) toggleSettings();
        else open(tab);
    }

    /* ---------- навигация ---------- */

    public void toggleSettings() {
        if (current == Tab.SETTINGS) {
            open(beforeSettings != null ? beforeSettings : Tab.HOME);
        } else {
            beforeSettings = current;
            // сбросить выделение у навигационных кнопок
            navGroup.clearSelection();
            showCard(Tab.SETTINGS);
            addToRecent(Tab.SETTINGS);
        }
    }

    public void open(Tab tab) {
        if (tab == current) return;
        showCard(tab);
        addToRecent(tab);
        if (tab != Tab.SETTINGS && navButtons.get(tab) != null)
            navButtons.get(tab).setSelected(true);
    }

    private void addToRecent(Tab tab) {
        recent.remove(tab);               // убираем дубликат
        if (recent.size() == 2) recent.removeFirst();
        recent.addLast(tab);
    }

    private void showCard(Tab tab) {
        tabs.cardLayout.show(tabs, tab.card);
        current = tab;
    }

    public void switchToOtherRecentTab() {
        if (recent.size() < 2) return;

        Iterator<Tab> it = recent.descendingIterator(); // last, prev-last
        Tab last = it.next();
        Tab prev = it.next();
        Tab target = (current == last ? prev : last);

        if (target == current) return;
        if (target == Tab.SETTINGS) toggleSettings();
        else navButtons.get(target).doClick();
    }
}
