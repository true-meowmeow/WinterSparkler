package core.main;

import com.formdev.flatlaf.FlatLaf;
import core.flatLaf.FlatLafConfigurator;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            FlatLafConfigurator.applyLookAndFeel(args);
            //UIManager.setLookAndFeel(new FlatLightLaf());
            FlatLaf.updateUI();
            Main.start();
        });
    }

    private static void start() {

        JFrame frame = new MainJFrame();
        JRoot jRoot = new JRoot();

        frame.setContentPane(jRoot);
        frame.setVisible(true);


    }
}
