package core.cardPanels;

import core.config.BreakpointsProperties;
import core.objects.JPanelCustom;
import core.panels.PanelManager;

import javax.swing.*;
import java.awt.*;

public class HomePanel extends JPanelCustom {
    private final BreakpointsProperties breakpoints = BreakpointsProperties.get();
    public HomePanel(PanelManager panelManager) {
        setBackground(Color.BLUE);
        add(new JLabel("Home"));
    }
}
