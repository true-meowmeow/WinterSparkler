package core.main;

import core.config.CoreProperties;
import core.main.titleMenuBar.JPanelTabs;
import core.main.titleMenuBar.TitleMenuBar;

import javax.swing.*;
import java.awt.*;

public class MainJFrame extends JFrame {

    private final CoreProperties props = CoreProperties.get();

    public MainJFrame() {
        super(CoreProperties.get().windowTitle());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setSize(props.windowWidth(), props.windowHeight());
        setMinimumSize(new Dimension(props.windowMinWidth(), props.windowMinHeight()));
        setLocationRelativeTo(null);

        TitleMenuBar titleMenuBar = new TitleMenuBar();
        setJMenuBar(titleMenuBar);
    }
}
