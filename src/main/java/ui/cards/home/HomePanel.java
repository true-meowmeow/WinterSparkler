package ui.cards.home;

import config.BreakpointsProperties;
import ui.components.BasePanel;

import javax.swing.*;
import java.awt.*;

public class HomePanel extends BasePanel {
    private final BreakpointsProperties breakpoints = BreakpointsProperties.get();
    public HomePanel() {
        setBackground(Color.BLUE);
        add(new JLabel("Home"));
    }
}
