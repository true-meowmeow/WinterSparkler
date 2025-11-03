package swing.elements;

import swing.core.basics.JPanelCustom;
import swing.core.basics.PanelType;
import swing.elements.pages.githublink.PageGithubLink;
import swing.elements.pages.manage.PageManage;
import swing.elements.pages.home.PageHome;
import swing.elements.pages.settings.PageSettings;

import javax.swing.*;
import java.awt.*;
import java.util.EnumMap;
import java.util.Map;

public class JPanelTabs extends JPanelCustom {
    private final Map<Tab, JComponent> pages = new EnumMap<>(Tab.class);

    public JPanelTabs() {
        super(PanelType.CARD);

        pages.put(Tab.HOME, new PageHome());
        pages.put(Tab.MANAGE, new PageManage());
        pages.put(Tab.GITHUB_LINK, new PageGithubLink());

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
