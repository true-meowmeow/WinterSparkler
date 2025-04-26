package swing.ui.pages.settings;

import core.contentManager.FolderEntities;
import swing.objects.general.panel.JPanelCustom;
import swing.objects.general.Pages;
import swing.objects.general.panel.PanelType;

import java.awt.*;


public class PageSettings extends Pages {
    public PageSettings(FolderEntities folderEntities) {
        super(PanelType.GRID, true);

        add(new LeftPanel(), menuGridBagConstraintsX(0, 40));
        add(new CenterPanel(), menuGridBagConstraintsX(1, 30));
        add(new RightPanel(folderEntities), menuGridBagConstraintsX(2, 30));
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

    public RightPanel(FolderEntities folderEntities) {
        super(true);


        setBackground(Color.DARK_GRAY);
        add(new FolderPathsPanel(folderEntities), BorderLayout.NORTH);
    }
}

