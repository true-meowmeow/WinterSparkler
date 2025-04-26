package swing.ui;

import javax.swing.*;

import core.Variables;
import swing.ui.components.TitleMenuBar;
import swing.ui.controllers.TabHotkeyController;
import swing.ui.utils.IconLoader;

import java.io.IOException;

public class MainJFrame extends JFrame implements Variables, VariablesUI {

    public MainJFrame() {
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setTitle(TITLE_NAME);


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
