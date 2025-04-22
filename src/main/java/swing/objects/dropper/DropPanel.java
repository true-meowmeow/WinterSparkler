package swing.objects.dropper;

import swing.objects.general.JPanelCustom;

import javax.swing.*;

public class DropPanel extends JPanelCustom {
    public DropPanelAbstract dropTargetPanel;

    public DropPanel(String name, DropPanelAbstract dropTargetPanel) {
        this.dropTargetPanel = dropTargetPanel;

        DropPanelRegistry.register(name, this);
    }

    public DropPanel() {}

    public void setDropTargetPanel(DropPanelAbstract dropTargetPanel) {
        this.dropTargetPanel = dropTargetPanel;
    }

    public void registerCollectionID(int any) {
        DropPanelRegistry.register(String.valueOf(any), this);
    }
    public void registerCollectionID(String any) {
        DropPanelRegistry.register(any, this);
    }
}

