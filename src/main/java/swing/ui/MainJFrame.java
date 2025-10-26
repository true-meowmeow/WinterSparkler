package swing.ui;

import core.config.CoreSettings;
import swing.ui.components.TitleMenuBar;
import swing.ui.controllers.TabHotkeyController;
import swing.ui.utils.IconLoader;

import javax.swing.*;
import java.io.IOException;

public class MainJFrame extends JFrame {

    public MainJFrame() {
        setSize(CoreSettings.get().windowWidth(), CoreSettings.get().windowHeight());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setTitle(CoreSettings.get().windowTitle());

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
