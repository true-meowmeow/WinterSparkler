package swing.pages.home.settings;

import swing.objects.JPanelCustom;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import static swing.objects.MethodsSwing.newGridBagConstraintsX;


public class PageSettings extends JPanelCustom {
    public PageSettings() {
        super(JPanelCustom.PanelType.GRID, true);

        add(new LeftPanel(), newGridBagConstraintsX(0, 40));
        add(new CenterPanel(), newGridBagConstraintsX(1, 30));
        add(new RightPanel(), newGridBagConstraintsX(2, 30));
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

    public RightPanel() {
        super(PanelType.BORDER, true);


        setBackground(Color.DARK_GRAY);
        add(new FolderPathsPanel(), BorderLayout.NORTH);
    }
}

