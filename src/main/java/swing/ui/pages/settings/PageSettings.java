package swing.ui.pages.settings;

import core.config.LayoutSettings;
import swing.objects.core.JPanelCustom;
import swing.objects.core.Pages;
import swing.objects.core.PanelType;

import java.awt.*;


//todo Зарефакторить настройки, просто накидано
public class PageSettings extends Pages {

    public PageSettings() {
        super(PanelType.GRID, true);

        add(new LeftPanel(), menuGridBagConstraintsX(0, LayoutSettings.get().getWeightLeftPanelSettings()));
        add(new CenterPanel(), menuGridBagConstraintsX(1, LayoutSettings.get().getWeightCenterPanelSettings()));
        add(new RightPanel(), menuGridBagConstraintsX(2, LayoutSettings.get().getWeightRightPanelSettings()));
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

