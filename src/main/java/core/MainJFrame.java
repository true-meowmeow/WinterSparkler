package core;

import core.config.WindowConfig;

import javax.swing.*;
import java.awt.*;

public class MainJFrame extends JFrame {

    public MainJFrame() {
        new JFrame(WindowConfig.TITLE);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setSize(WindowConfig.WIDTH, WindowConfig.HEIGHT);
        setMinimumSize(new Dimension(WindowConfig.MIN_WIDTH, WindowConfig.MIN_HEIGHT));
        setLocationRelativeTo(null);
    }
}
