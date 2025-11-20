package core.cols.col2;

import core.objects.JPanelCustom;
import core.config.ThemeProperties;
public class Col2 extends JPanelCustom {

    private static final ThemeProperties THEME = ThemeProperties.get();

    public Col2() {
        setBackground(THEME.columnTwoColor());
    }
}
