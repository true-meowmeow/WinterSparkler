package core.panels.obsolete;

import core.objects.GPanel;
import core.config.ThemeProperties;

public class Panel1 extends GPanel {  //Collection

    private final ThemeProperties theme = ThemeProperties.get();

    public Panel1() {
        setBackground(theme.gridPanelOneColor());
    }
}
