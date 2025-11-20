package core.cols.col1;

import core.objects.JPanelCustom;
import core.config.ThemeProperties;

public class Col1 extends JPanelCustom {

    private static final ThemeProperties THEME = ThemeProperties.get();

    public Col1() {
        setBackground(THEME.columnOneColor());
    }
}
