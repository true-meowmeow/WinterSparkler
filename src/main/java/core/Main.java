package core;

import com.formdev.flatlaf.FlatLaf;
import core.flatLaf.FlatLafConfigurator;

import javax.swing.*;
import java.awt.*;

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
        //FlatDarkLaf.setup();


        JFrame frame = new MainJFrame();
        JRoot jRoot = new JRoot();

        //SwingUtilities.updateComponentTreeUI(frame);    //Нужно ли?

        frame.setContentPane(jRoot);
        frame.setVisible(true);


    }
}
