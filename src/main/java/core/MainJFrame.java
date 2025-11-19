package core;

import core.config.CoreProperties;

import javax.swing.*;
import java.awt.*;

public class MainJFrame extends JFrame {

    public MainJFrame() {
        super(CoreProperties.get().windowTitle());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        CoreProperties props = CoreProperties.get();
        setSize(props.windowWidth(), props.windowHeight());
        setMinimumSize(new Dimension(props.windowMinWidth(), props.windowMinHeight()));
        setLocationRelativeTo(null);
    }
}
