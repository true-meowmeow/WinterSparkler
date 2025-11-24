package core.panels.cards.settingsCard;

import core.objects.JPanelCustom;
import core.panels.obsolete.PanelManager;

import javax.swing.*;
import java.awt.*;

public class SettingsPanel extends JPanelCustom {
    public SettingsPanel(PanelManager panelManager) {
        setBackground(Color.PINK);
        add(new JLabel("SettingsPanel"));
    }
}
