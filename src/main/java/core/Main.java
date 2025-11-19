package core;

import com.formdev.flatlaf.FlatDarkLaf;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            //FlatLafConfigurator.applyLookAndFeel(args, CoreProperties.get().preferencesRootPath());
            Main.start();
        });
    }

    private static void start() {
        FlatDarkLaf.setup();


        JFrame frame = new MainJFrame();
        JRoot jRoot = new JRoot();

        //SwingUtilities.updateComponentTreeUI(frame);    //Нужно ли?

        frame.setContentPane(jRoot);
        frame.setVisible(true);


    }
}
