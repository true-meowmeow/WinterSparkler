package core.panels.obsolete;

import core.objects.JPanelCustom;
import core.config.ThemeProperties;

public class Panel4 extends JPanelCustom {  //Queue

    private final ThemeProperties theme = ThemeProperties.get();

    public Panel4() {
        setBackground(theme.gridPanelFourColor());
    }
}
