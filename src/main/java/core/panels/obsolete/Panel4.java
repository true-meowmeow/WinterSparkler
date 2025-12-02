package core.panels.obsolete;

import core.objects.GPanel;
import core.config.ThemeProperties;

public class Panel4 extends GPanel {  //Queue

    private final ThemeProperties theme = ThemeProperties.get();

    public Panel4() {
        setBackground(theme.gridPanelFourColor());
    }
}
