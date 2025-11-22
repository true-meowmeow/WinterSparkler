package core.main;

import com.formdev.flatlaf.FlatClientProperties;
import core.config.CoreProperties;
import core.main.titleMenuBar.TitleMenuBar;

import javax.swing.*;
import java.awt.*;

public class MainJFrame extends JFrame {

    private final CoreProperties props = CoreProperties.get();
    private final JRoot jRoot;


    public MainJFrame() {
        super(CoreProperties.get().windowTitle());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setSize(props.windowWidth(), props.windowHeight());
        setMinimumSize(new Dimension(props.windowMinWidth(), props.windowMinHeight()));
        setLocationRelativeTo(null);

        jRoot = new JRoot();
        setContentPane(jRoot);
        TitleMenuBar titleMenuBar = new TitleMenuBar(jRoot::showCard);
        setJMenuBar(titleMenuBar);

    }
}
