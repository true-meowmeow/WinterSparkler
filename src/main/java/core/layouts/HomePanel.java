package core.layouts;

import core.objects.JPanelCustom;

import javax.swing.*;
import java.awt.*;

public class HomePanel extends JPanelCustom {
    public HomePanel() {
        setBackground(Color.BLUE);
        add(new JLabel("Home"));
    }
}
