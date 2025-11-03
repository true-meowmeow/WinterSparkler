package swing.elements.pages.settings;

import core.main.config.LayoutProperties;
import swing.core.basics.JPanelCustom;
import swing.core.basics.Pages;
import swing.core.basics.PanelType;

import java.awt.*;


//todo Зарефакторить настройки, просто накидано
public class PageSettings extends Pages {

    public PageSettings() {
        super(PanelType.GRID, true);

        add(new LeftPanel(), menuGridBagConstraintsX(0, LayoutProperties.get().getWeightLeftPanelSettings()));
        add(new CenterPanel(), menuGridBagConstraintsX(1, LayoutProperties.get().getWeightCenterPanelSettings()));
        add(new RightPanel(), menuGridBagConstraintsX(2, LayoutProperties.get().getWeightRightPanelSettings()));
    }
}


class LeftPanel extends JPanelCustom {

    public LeftPanel() {
        super(true);
    }
}

class CenterPanel extends JPanelCustom {

    public CenterPanel() {
        super(true);

        setBackground(Color.LIGHT_GRAY);
    }
}

class RightPanel extends JPanelCustom {

    public RightPanel() {
        super(true);

        setBackground(Color.DARK_GRAY);
        add(new FolderPathsPanel(), BorderLayout.NORTH);
    }
}

