package core.panels.obsolete;

import core.objects.JPanelCustom;
import core.config.ThemeProperties;

public class Panel2 extends JPanelCustom {  //Series

    private final ThemeProperties theme = ThemeProperties.get();

    public Panel2() {
        setBackground(theme.gridPanelTwoColor());
    }
}
