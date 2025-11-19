package obsolete.core.main;

import obsolete.core.main.config.CoreProperties;
import obsolete.swing.elements.MainJFrame;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            FlatLafConfigurator.applyLookAndFeel(args, CoreProperties.get().preferencesRootPath());
            MainJFrame frame = new MainJFrame();
            SwingUtilities.updateComponentTreeUI(frame);
            frame.setVisible(true);
        });
    }
}
