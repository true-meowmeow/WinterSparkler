package swing.ui;

import core.contentManager.FolderEntities;
import swing.objects.general.panel.JPanelCustom;
import swing.objects.general.panel.PanelType;
import swing.ui.components.Tab;
import swing.ui.pages.PageFavorites;
import swing.ui.pages.PageComponent;
import swing.ui.pages.home.PageHome;
import swing.ui.pages.home.play.CombinedPanel;
import swing.ui.pages.settings.PageSettings;

import javax.swing.*;
import java.awt.*;
import java.util.EnumMap;
import java.util.Map;

public class JPanelTabs extends JPanelCustom {
    private final Map<Tab, JComponent> pages = new EnumMap<>(Tab.class);

    public JPanelTabs() {
        super(PanelType.CARD);

        // подготовим контент
        FolderEntities folderEntities = new FolderEntities();
        CombinedPanel combinedPanel = new CombinedPanel(folderEntities);

        pages.put(Tab.HOME,      new PageHome(combinedPanel));
        pages.put(Tab.COMPONENT, new PageComponent());
        pages.put(Tab.FAVORITES, new PageFavorites());
        pages.put(Tab.SETTINGS,  new PageSettings(folderEntities));

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
