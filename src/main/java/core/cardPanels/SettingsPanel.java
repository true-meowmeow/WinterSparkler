package core.cardPanels;

import core.objects.JPanelCustom;
import core.panels.PanelManager;

import javax.swing.*;
import java.awt.*;

public class SettingsPanel extends JPanelCustom {
    public SettingsPanel(PanelManager panelManager) {
        setBackground(Color.PINK);
        add(new JLabel("SettingsPanel"));
    }
}
