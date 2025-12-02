package core.panels.obsolete;

import core.objects.GPanel;
import core.config.ThemeProperties;

public class Panel2 extends GPanel {  //Series

    private final ThemeProperties theme = ThemeProperties.get();

    public Panel2() {
        setBackground(theme.gridPanelTwoColor());
    }
}
