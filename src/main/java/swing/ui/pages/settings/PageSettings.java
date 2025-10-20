package swing.ui.pages.settings;

import swing.objects.core.JPanelCustom;
import swing.objects.core.Pages;
import swing.objects.core.PanelType;
import swing.ui.VariablesUI;

import java.awt.*;


//todo Зарефакторить настройки, просто накидано
public class PageSettings extends Pages implements VariablesUI {
    public PageSettings() {
        super(PanelType.GRID, true);

        add(new LeftPanel(), menuGridBagConstraintsX(0, VariablesUI.weightLeftPanelSettings()));
        add(new CenterPanel(), menuGridBagConstraintsX(1, VariablesUI.weightCenterPanelSettings()));
        add(new RightPanel(), menuGridBagConstraintsX(2, VariablesUI.weightRightPanelSettings()));
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

