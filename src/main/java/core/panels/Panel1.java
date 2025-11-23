package core.panels;

import core.objects.JPanelCustom;
import core.config.ThemeProperties;

public class Panel1 extends JPanelCustom {  //Collection

    private final ThemeProperties theme = ThemeProperties.get();

    public Panel1() {
        setBackground(theme.gridPanelOneColor());
    }
}
