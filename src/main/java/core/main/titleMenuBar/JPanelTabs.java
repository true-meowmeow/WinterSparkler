package core.main.titleMenuBar;


import core.main.check.PanelType;
import core.objects.JPanelCustom;

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
        System.out.println("123");
        CardLayout cl = (CardLayout) getLayout();
        cl.show(this, tab.getCard());
    }

    private JComponent createPage(Tab tab) {
        JComponent dsa = new JPanel();

/*        return switch (tab) {
            case HOME, MANAGE -> {
                System.out.println("123");
            }
            case GITHUB_LINK -> {
                System.out.println("123");
            }
            case SETTINGS -> {
                System.out.println("123");
            }
        };*/
        return dsa;
    }
}
