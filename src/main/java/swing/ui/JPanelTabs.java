package swing.ui;

import swing.objects.general.JPanelCustom;
import swing.objects.general.PanelType;
import swing.ui.components.Tab;
import swing.ui.pages.PageFavorites;
import swing.ui.pages.PageComponent;
import swing.ui.pages.home.PageHome;
import swing.ui.pages.settings.PageSettings;

import javax.swing.*;
import java.awt.*;
import java.util.EnumMap;
import java.util.Map;

public class JPanelTabs extends JPanelCustom {
    private final Map<Tab, JComponent> pages = new EnumMap<>(Tab.class);

    public JPanelTabs() {
        super(PanelType.CARD);

        pages.put(Tab.HOME, new PageHome());
        pages.put(Tab.COMPONENT, new PageComponent());
        pages.put(Tab.FAVORITES, new PageFavorites());
        pages.put(Tab.SETTINGS, new PageSettings());

        // добавляем все вкладки из enum
        for (Tab tab : Tab.values()) {
            add(pages.get(tab), tab.getCard());
        }
    }

    public void showTab(Tab tab) {
        CardLayout cl = (CardLayout) getLayout();
        cl.show(this, tab.getCard());
    }
}
