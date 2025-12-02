package core.panels.cards.homeCard;

import core.config.BreakpointsProperties;
import core.objects.GPanel;
import core.panels.obsolete.PanelManager;

import javax.swing.*;
import java.awt.*;

public class HomePanel extends GPanel {
    private final BreakpointsProperties breakpoints = BreakpointsProperties.get();
    public HomePanel(PanelManager panelManager) {
        setBackground(Color.BLUE);
        add(new JLabel("Home"));
    }
}
