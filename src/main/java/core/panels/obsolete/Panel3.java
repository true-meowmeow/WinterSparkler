package core.panels.obsolete;

import core.objects.GPanel;
import core.config.ThemeProperties;

public class Panel3 extends GPanel {  //Playlist

    private final ThemeProperties theme = ThemeProperties.get();

    public Panel3() {
        setBackground(theme.gridPanelThreeColor());
    }
}
