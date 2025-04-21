package core;

import swing.objects.general.DemoPrefs;
import swing.ui.MainJFrame;
import javax.swing.*;
import java.awt.*;

public class Main implements Variables {

    public static final String KEY_TAB = "tab";
    public static final String link_github = "https://github.com/true-meowmeow/WinterSparkler";


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame.setDefaultLookAndFeelDecorated(true);      // включаем FlatLaf‑decorations

            DemoPrefs.init(PREFS_ROOT_PATH);
            DemoPrefs.setupLaf(args);                         // ставит FlatLaf

            tuneFlatLaf();                                    // убираем все отступы + размеры кнопок

            MainJFrame frame = new MainJFrame();
            SwingUtilities.updateComponentTreeUI(frame);
            frame.setVisible(true);
        });
    }

    // размеры системных кнопок заголовка
    private static final int TITLE_BTN_W = 47;
    private static final int TITLE_BTN_H = 28;
    private static void tuneFlatLaf() {
        int z = 0;

        UIManager.put("ScrollPane.smoothScrolling", true);

        UIManager.put("TitlePane.buttonSize", new Dimension(TITLE_BTN_W, TITLE_BTN_H));
        UIManager.put("TitlePane.showIcon", false);
        UIManager.put("TitlePane.noIconLeftGap", z);
        UIManager.put("TitlePane.menuBarTitleGap", z);
        UIManager.put("TitlePane.menuBarTitleMinimumGap", z);

        UIManager.put("Button.iconTextGap", z);
        UIManager.put("ToggleButton.iconTextGap", z);
        UIManager.put("CheckBox.iconTextGap", z);
        UIManager.put("RadioButton.iconTextGap", z);
        UIManager.put("TextField.iconTextGap", z);
        UIManager.put("PasswordField.iconTextGap", z);
        UIManager.put("FormattedTextField.iconTextGap", z);

        UIManager.put("MenuItem.iconTextGap", z);
        UIManager.put("MenuItem.textAcceleratorGap", z);
        UIManager.put("MenuItem.textNoAcceleratorGap", z);
        UIManager.put("MenuItem.acceleratorArrowGap", z);

        UIManager.put("OptionPane.iconMessageGap", z);
        UIManager.put("SplitPaneDivider.gripGap", z);
    }
}
