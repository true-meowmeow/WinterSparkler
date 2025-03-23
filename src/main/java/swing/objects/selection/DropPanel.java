package swing.objects.selection;

import swing.objects.JPanelCustom;

import javax.swing.*;

public class DropPanel extends JPanelCustom {
    private static DropPanel instance;

    public static DropPanel DropPanelInstance() {
        return instance;
    }

    public DropTargetPanel dropTargetPanel;

    public DropPanel() {
        super(PanelType.BORDER);
        instance = this;
        dropTargetPanel = new DropTargetPanel();
        JScrollPane dropScroll = new JScrollPane(dropTargetPanel);
        add(dropScroll);

    }
}
