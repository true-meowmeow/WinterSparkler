package swing.ui.pages.settings;

import swing.objects.general.JPanelCustom;
import swing.objects.general.Pages;
import swing.objects.general.PanelType;
import swing.ui.VariablesUI;

import java.awt.*;


//todo Зарефакторить настройки, просто накидано
public class PageSettings extends Pages implements VariablesUI {
    public PageSettings() {
        super(PanelType.GRID, true);

        add(new LeftPanel(), menuGridBagConstraintsX(0, weightLeftPanelSettings));
        add(new CenterPanel(), menuGridBagConstraintsX(1, weightCenterPanelSettings));
        add(new RightPanel(), menuGridBagConstraintsX(2, weightRightPanelSettings));
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

