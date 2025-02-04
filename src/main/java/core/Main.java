package core;

import com.formdev.flatlaf.FlatLaf;
import swing.DemoPrefs;
import swing.MainJFrame;

import javax.swing.*;

public class Main implements Variables {

    public static final String KEY_TAB = "tab";

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            DemoPrefs.init(PREFS_ROOT_PATH);
            DemoPrefs.setupLaf(args);


// Перед изменением темы
            LookAndFeel originalTheme = UIManager.getLookAndFeel();

            MainJFrame frame = new MainJFrame();

// После диалога восстановите явно
            try {
                UIManager.setLookAndFeel(originalTheme);
            } catch (UnsupportedLookAndFeelException e) {
                throw new RuntimeException(e);
            }
            SwingUtilities.updateComponentTreeUI(frame); // для главного окна
            FlatLaf.updateUI(); // вместо updateComponentTreeUI()
            frame.setVisible(true);

            //Swing.DemoPrefs.registerSystemScaleFactors(frame);
            //Swing.DemoPrefs.initSystemScale();
            //DemoPrefs.start(frame, "alt shift F3", "2" );
        });
    }



}
