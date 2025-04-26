package core;

import swing.ui.FlatLafConfigurator;
import swing.ui.MainJFrame;
import javax.swing.*;

public class Main implements Variables {

    public static final String LINK_GITHUB = "https://github.com/true-meowmeow/WinterSparkler";

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            FlatLafConfigurator.applyLookAndFeel(args, PREFS_ROOT_PATH);
            MainJFrame frame = new MainJFrame();
            SwingUtilities.updateComponentTreeUI(frame);
            frame.setVisible(true);
        });
    }
}
