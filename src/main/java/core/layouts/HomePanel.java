package core.layouts;

import core.config.BreakpointsProperties;
import core.objects.JPanelCustom;

import javax.swing.*;
import java.awt.*;

public class HomePanel extends JPanelCustom {
    private final BreakpointsProperties breakpoints = BreakpointsProperties.get();
    public HomePanel() {
        setBackground(Color.BLUE);
        add(new JLabel("Home"));
    }
}
