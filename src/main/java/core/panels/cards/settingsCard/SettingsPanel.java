package core.panels.cards.settingsCard;

import core.objects.GPanel;
import core.panels.obsolete.PanelManager;

import javax.swing.*;
import java.awt.*;

public class SettingsPanel extends GPanel {
    public SettingsPanel(PanelManager panelManager) {
        setBackground(Color.PINK);
        add(new JLabel("SettingsPanel"));
    }
}
