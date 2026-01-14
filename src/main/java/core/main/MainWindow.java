package core.main;

import core.main.config.CoreProperties;
import core.ui.navigation.TitleMenuBar;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {

    private final CoreProperties props = CoreProperties.get();
    private final RootPanel jRoot;


    public MainWindow() {
        super(CoreProperties.get().windowTitle());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setSize(props.windowWidth(), props.windowHeight());
        setMinimumSize(new Dimension(props.windowMinWidth(), props.windowMinHeight()));
        setLocationRelativeTo(null);

        jRoot = new RootPanel();
        setContentPane(jRoot);
        TitleMenuBar titleMenuBar = new TitleMenuBar(jRoot::showCard);
        setJMenuBar(titleMenuBar);
    }
}
