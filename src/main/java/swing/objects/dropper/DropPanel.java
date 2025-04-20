package swing.objects.dropper;

import swing.objects.general.JPanelCustom;

import javax.swing.*;

public class DropPanel extends JPanelCustom {
    public DropPanelAbstract dropTargetPanel;

    public DropPanel(String name, DropPanelAbstract dropTargetPanel) {
        this.dropTargetPanel = dropTargetPanel;

        DropPanelRegistry.register(name, this);
    }
}

