package core.panels.obsolete;

import core.objects.JPanelCustom;
import core.config.ThemeProperties;

public class Panel3 extends JPanelCustom {  //Playlist

    private final ThemeProperties theme = ThemeProperties.get();

    public Panel3() {
        setBackground(theme.gridPanelThreeColor());
    }
}
