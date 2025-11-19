package obsolete.core.main;

import obsolete.core.main.config.LayoutProperties;

import javax.swing.*;

public final class FlatLafConfigurator {

    public static void applyLookAndFeel(String[] args, String prefsRootPath) {
        JFrame.setDefaultLookAndFeelDecorated(true);
        DemoPrefs.init(prefsRootPath);
        DemoPrefs.setupLaf(args);
        tuneUI();
    }

    private static void tuneUI() {
        int z = 0;

        UIManager.put("ScrollPane.smoothScrolling", true);

        UIManager.put("TitlePane.buttonSize", LayoutProperties.get().getTitleControlButton());
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
