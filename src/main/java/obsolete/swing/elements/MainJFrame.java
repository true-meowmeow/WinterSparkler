package obsolete.swing.elements;

import obsolete.core.main.config.CoreProperties;
import obsolete.swing.elements.controllers.TabHotkeyController;
import obsolete.swing.elements.utils.IconLoader;

import javax.swing.*;
import java.io.IOException;

public class MainJFrame extends JFrame {

    public MainJFrame() {
        setSize(CoreProperties.get().windowWidth(), CoreProperties.get().windowHeight());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setTitle(CoreProperties.get().appName());

        try {
            setIconImages(IconLoader.loadAndScaleIcons());
        } catch (IOException e) {
            e.printStackTrace();
        }

        JPanelTabs jPanelTabs = new JPanelTabs();
        add(jPanelTabs);

        TitleMenuBar titleMenuBar = new TitleMenuBar(jPanelTabs);
        setJMenuBar(titleMenuBar);
        new TabHotkeyController(titleMenuBar);
    }
}
