package swing.pages.home.settings;

import core.contentManager.FolderEntities;
import swing.objects.JPanelCustom;

import java.awt.*;

import static swing.objects.MethodsSwing.newGridBagConstraintsX;


public class PageSettings extends JPanelCustom {
    public PageSettings(FolderEntities folderEntities) {
        super(JPanelCustom.PanelType.GRID, true);

        add(new LeftPanel(), newGridBagConstraintsX(0, 40));
        add(new CenterPanel(), newGridBagConstraintsX(1, 30));
        add(new RightPanel(folderEntities), newGridBagConstraintsX(2, 30));
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

