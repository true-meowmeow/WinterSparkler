package Core;

import Swing.DemoPrefs;
import Swing.MainJFrame;

import javax.swing.*;

public class Main implements Variables {

    public static final String KEY_TAB = "tab";

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            DemoPrefs.init(PREFS_ROOT_PATH);
            DemoPrefs.setupLaf(args);

            MainJFrame frame = new MainJFrame();
            frame.setVisible(true);

            //Swing.DemoPrefs.registerSystemScaleFactors(frame);
            //Swing.DemoPrefs.initSystemScale();
            //DemoPrefs.start(frame, "alt shift F3", "2" );
        });
    }



}
