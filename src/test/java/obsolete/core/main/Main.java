package obsolete.core.main;

import config.laf.FlatLafConfigurator;
import obsolete.swing.elements.MainJFrame;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            FlatLafConfigurator.applyLookAndFeel(args);
            MainJFrame frame = new MainJFrame();
            SwingUtilities.updateComponentTreeUI(frame);
            frame.setVisible(true);
        });
    }
}
