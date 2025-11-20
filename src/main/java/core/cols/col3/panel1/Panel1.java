package core.cols.col3.panel1;

import core.objects.JPanelCustom;
import core.config.ThemeProperties;

public class Panel1 extends JPanelCustom {

    private final ThemeProperties theme = ThemeProperties.get();

    public Panel1() {
        setBackground(theme.gridPanelOneColor());
    }
}
