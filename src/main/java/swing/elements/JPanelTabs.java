package swing.elements;

import swing.core.basics.JPanelCustom;
import swing.core.basics.PanelType;
import swing.elements.pages.githublink.PageGithubLink;
import swing.elements.pages.home.PageHome;
import swing.elements.pages.settings.PageSettings;

import javax.swing.*;
import java.awt.*;
import java.util.HashSet;
import java.util.Set;

public class JPanelTabs extends JPanelCustom {
    private final Set<String> registeredCards = new HashSet<>();

    public JPanelTabs() {
        super(PanelType.CARD);

        for (Tab tab : Tab.values()) {
            String card = tab.getCard();
            if (registeredCards.add(card)) {
                add(createPage(tab), card);
            }
        }
    }

    public void showTab(Tab tab) {
        CardLayout cl = (CardLayout) getLayout();
        cl.show(this, tab.getCard());
    }

    private JComponent createPage(Tab tab) {
        return switch (tab) {
            case HOME, MANAGE -> new PageHome();
            case GITHUB_LINK -> new PageGithubLink();
            case SETTINGS -> new PageSettings();
        };
    }
}
