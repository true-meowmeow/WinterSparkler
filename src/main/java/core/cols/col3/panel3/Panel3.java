package core.cols.col3.panel3;

import core.objects.JPanelCustom;
import core.config.ThemeProperties;

public class Panel3 extends JPanelCustom {

    private final ThemeProperties theme = ThemeProperties.get();

    public Panel3() {
        setBackground(theme.gridPanelThreeColor());
    }
}
