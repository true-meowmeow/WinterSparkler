package core;

import core.config.CoreSettings;
import swing.ui.MainJFrame;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            FlatLafConfigurator.applyLookAndFeel(args, CoreSettings.get().preferencesRootPath());
            MainJFrame frame = new MainJFrame();
            SwingUtilities.updateComponentTreeUI(frame);
            frame.setVisible(true);
        });
    }
}
