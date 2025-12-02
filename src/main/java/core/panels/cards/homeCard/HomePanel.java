package core.panels.cards.homeCard;

import core.config.BreakpointsProperties;
import core.objects.GPanel;

import javax.swing.*;
import java.awt.*;

public class HomePanel extends GPanel {
    private final BreakpointsProperties breakpoints = BreakpointsProperties.get();
    public HomePanel() {
        setBackground(Color.BLUE);
        add(new JLabel("Home"));
    }
}
