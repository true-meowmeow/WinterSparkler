package swing.ui;

import core.config.SettingsLoader;

import java.awt.*;

public interface VariablesUI {

    // Title buttons (Dimensions from config)
    static Dimension TITLE_CONTROL_BUTTON() { return SettingsLoader.get().getTitleControlButton(); }
    static Dimension TITLE_NAVIGATION_BUTTON() { return SettingsLoader.get().getTitleNavigationButton(); }
    static Dimension TITLE_NAVIGATION_SETTINGS_BUTTON() { return SettingsLoader.get().getTitleNavigationSettingsButton(); }

    // Home page split (left/right)
    static double weightLeftSidePageHome() { return SettingsLoader.get().getWeightLeftSidePageHome(); }
    static double weightRightSidePageHome() { return SettingsLoader.get().getWeightRightSidePageHome(); }

    // Home frames
    static double weightCollectionsFrame() { return SettingsLoader.get().getWeightCollectionsFrame(); }
    static double weightSeriesFrame() { return SettingsLoader.get().getWeightSeriesFrame(); }
    static double weightPlayFrame() { return SettingsLoader.get().getWeightPlayFrame(); }
    static double weightQueueFrame() { return SettingsLoader.get().getWeightQueueFrame(); }

    // Settings page panels
    static double weightLeftPanelSettings() { return SettingsLoader.get().getWeightLeftPanelSettings(); }
    static double weightCenterPanelSettings() { return SettingsLoader.get().getWeightCenterPanelSettings(); }
    static double weightRightPanelSettings() { return SettingsLoader.get().getWeightRightPanelSettings(); }
}
