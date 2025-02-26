package swing.pages.home.settings;

import core.contentManager.FolderEntities;
import swing.objects.JPanelCustom;

import java.awt.*;


public class PageSettings extends JPanelCustom {
    public PageSettings(FolderEntities folderEntities) {
        super(JPanelCustom.PanelType.GRID, true);

        add(new LeftPanel(), menuGridBagConstraintsX(0, 40));
        add(new CenterPanel(), menuGridBagConstraintsX(1, 30));
        add(new RightPanel(folderEntities), menuGridBagConstraintsX(2, 30));
    }
}


class LeftPanel extends JPanelCustom {

    public LeftPanel() {
        super(PanelType.BORDER, true);
    }
}

class CenterPanel extends JPanelCustom {

    public CenterPanel() {
        super(PanelType.BORDER, true);

        setBackground(Color.LIGHT_GRAY);
    }
}

class RightPanel extends JPanelCustom {

    public RightPanel(FolderEntities folderEntities) {
        super(PanelType.BORDER, true);


        setBackground(Color.DARK_GRAY);
        add(new FolderPathsPanel(folderEntities), BorderLayout.NORTH);
    }
}

