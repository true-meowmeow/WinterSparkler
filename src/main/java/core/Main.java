package core;

import com.formdev.flatlaf.FlatLaf;
import swing.DemoPrefs;
import swing.MainJFrame;

import javax.swing.*;

public class Main implements Variables {

    public static final String KEY_TAB = "tab";
    public static final String link_github = "https://github.com/true-meowmeow/WinterSparkler";
    //Сначала я набрасываю код, потом рефакторю, я всегда так работаю (meow meow)

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            DemoPrefs.init(PREFS_ROOT_PATH);
            DemoPrefs.setupLaf(args);




            MainJFrame frame = new MainJFrame();


            try {
                UIManager.setLookAndFeel("com.formdev.flatlaf.FlatDarkLaf");
                UIManager.setLookAndFeel("com.formdev.flatlaf.FlatLightLaf");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }


            SwingUtilities.updateComponentTreeUI(frame); // для главного окна
            frame.setVisible(true);

            //Swing.DemoPrefs.registerSystemScaleFactors(frame);
            //Swing.DemoPrefs.initSystemScale();
            //DemoPrefs.start(frame, "alt shift F3", "2" );
        });
    }



}
