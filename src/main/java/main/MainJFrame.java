package main;

import config.CoreProperties;
import ui.navigation.TitleMenuBar;

import javax.swing.*;
import java.awt.*;

public class MainJFrame extends JFrame {

    private final CoreProperties props = CoreProperties.get();
    private final Root jRoot;


    public MainJFrame() {
        super(CoreProperties.get().windowTitle());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setSize(props.windowWidth(), props.windowHeight());
        setMinimumSize(new Dimension(props.windowMinWidth(), props.windowMinHeight()));
        setLocationRelativeTo(null);

        jRoot = new Root();
        setContentPane(jRoot);
        TitleMenuBar titleMenuBar = new TitleMenuBar(jRoot::showCard);
        setJMenuBar(titleMenuBar);
    }
}
