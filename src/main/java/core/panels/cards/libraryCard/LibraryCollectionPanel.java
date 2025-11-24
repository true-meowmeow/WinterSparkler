package core.panels.cards.libraryCard;

import core.config.ThemeProperties;
import core.objects.JPanelCustom;

public class LibraryCollectionPanel extends JPanelCustom {  //Collection

    private final ThemeProperties theme = ThemeProperties.get();

    public LibraryCollectionPanel() {
        setBackground(theme.gridPanelOneColor());
    }
}
