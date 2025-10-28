package core.config;

import java.awt.*;

public final class LayoutSettings {
    private static final LayoutSettings INSTANCE = load();

    private final Integer titleControlButtonWidth;
    private final Integer titleControlButtonHeight;
    private final Integer titleNavigationButtonWidth;
    private final Integer titleNavigationButtonHeight;
    private final Integer titleNavigationSettingsButtonWidth;
    private final Integer titleNavigationSettingsButtonHeight;

    private final Double weightLeftSidePageHome;
    private final Double weightRightSidePageHome;
    private final Double weightCollectionsFrame;
    private final Double weightSeriesFrame;
    private final Double weightPlayFrame;
    private final Double weightQueueFrame;
    private final Double weightLeftPanelSettings;
    private final Double weightCenterPanelSettings;
    private final Double weightRightPanelSettings;

    private static final int DEF_TITLE_CONTROL_W = 47;
    private static final int DEF_TITLE_CONTROL_H = 28;
    private static final int DEF_TITLE_NAV_W = 90;
    private static final int DEF_TITLE_NAV_H = 28;

    private static final double DEF_LEFT_HOME = 37.0;
    private static final double DEF_RIGHT_HOME = 63.0;
    private static final double DEF_COLL = 50.0;
    private static final double DEF_SER = 50.0;
    private static final double DEF_PLAY = 75.0;
    private static final double DEF_QUEUE = 25.0;
    private static final double DEF_LEFT_SET = 40.0;
    private static final double DEF_CENTER_SET = 30.0;
    private static final double DEF_RIGHT_SET = 30.0;

    private LayoutSettings(Integer titleControlButtonWidth,
                           Integer titleControlButtonHeight,
                           Integer titleNavigationButtonWidth,
                           Integer titleNavigationButtonHeight,
                           Integer titleNavigationSettingsButtonWidth,
                           Integer titleNavigationSettingsButtonHeight,
                           Double weightLeftSidePageHome,
                           Double weightRightSidePageHome,
                           Double weightCollectionsFrame,
                           Double weightSeriesFrame,
                           Double weightPlayFrame,
                           Double weightQueueFrame,
                           Double weightLeftPanelSettings,
                           Double weightCenterPanelSettings,
                           Double weightRightPanelSettings) {
        this.titleControlButtonWidth = titleControlButtonWidth;
        this.titleControlButtonHeight = titleControlButtonHeight;
        this.titleNavigationButtonWidth = titleNavigationButtonWidth;
        this.titleNavigationButtonHeight = titleNavigationButtonHeight;
        this.titleNavigationSettingsButtonWidth = titleNavigationSettingsButtonWidth;
        this.titleNavigationSettingsButtonHeight = titleNavigationSettingsButtonHeight;
        this.weightLeftSidePageHome = weightLeftSidePageHome;
        this.weightRightSidePageHome = weightRightSidePageHome;
        this.weightCollectionsFrame = weightCollectionsFrame;
        this.weightSeriesFrame = weightSeriesFrame;
        this.weightPlayFrame = weightPlayFrame;
        this.weightQueueFrame = weightQueueFrame;
        this.weightLeftPanelSettings = weightLeftPanelSettings;
        this.weightCenterPanelSettings = weightCenterPanelSettings;
        this.weightRightPanelSettings = weightRightPanelSettings;
    }

    public static LayoutSettings get() {
        return INSTANCE;
    }

    private static LayoutSettings load() {
        PropertyFile props = PropertyFile.load(
                LayoutSettings.class,
                ConfigFiles.DEFAULTS,
                ConfigFiles.USER_OVERRIDES
        );
        return new LayoutSettings(
                props.integer("titleControlButtonWidth"),
                props.integer("titleControlButtonHeight"),
                props.integer("titleNavigationButtonWidth"),
                props.integer("titleNavigationButtonHeight"),
                props.integer("titleNavigationSettingsButtonWidth"),
                props.integer("titleNavigationSettingsButtonHeight"),
                props.doubleValue("weightLeftSidePageHome"),
                props.doubleValue("weightRightSidePageHome"),
                props.doubleValue("weightCollectionsFrame"),
                props.doubleValue("weightSeriesFrame"),
                props.doubleValue("weightPlayFrame"),
                props.doubleValue("weightQueueFrame"),
                props.doubleValue("weightLeftPanelSettings"),
                props.doubleValue("weightCenterPanelSettings"),
                props.doubleValue("weightRightPanelSettings")
        );
    }

    public Dimension getTitleControlButton() {
        return new Dimension(
                titleControlButtonWidth != null ? titleControlButtonWidth : DEF_TITLE_CONTROL_W,
                titleControlButtonHeight != null ? titleControlButtonHeight : DEF_TITLE_CONTROL_H
        );
    }

    public Dimension getTitleNavigationButton() {
        return new Dimension(
                titleNavigationButtonWidth != null ? titleNavigationButtonWidth : DEF_TITLE_NAV_W,
                titleNavigationButtonHeight != null ? titleNavigationButtonHeight : DEF_TITLE_NAV_H
        );
    }

    public Dimension getTitleNavigationSettingsButton() {
        return new Dimension(
                titleNavigationSettingsButtonWidth != null ? titleNavigationSettingsButtonWidth : DEF_TITLE_NAV_W,
                titleNavigationSettingsButtonHeight != null ? titleNavigationSettingsButtonHeight : DEF_TITLE_NAV_H
        );
    }

    public double getWeightLeftSidePageHome() {
        return normalize(weightLeftSidePageHome, DEF_LEFT_HOME);
    }

    public double getWeightRightSidePageHome() {
        if (weightRightSidePageHome == null && weightLeftSidePageHome != null) {
            double left = getWeightLeftSidePageHome();
            return Math.max(0.0, 100.0 - left);
        }
        return normalize(weightRightSidePageHome, DEF_RIGHT_HOME);
    }

    public double getWeightCollectionsFrame() {
        return normalize(weightCollectionsFrame, DEF_COLL);
    }

    public double getWeightSeriesFrame() {
        return normalize(weightSeriesFrame, DEF_SER);
    }

    public double getWeightPlayFrame() {
        return normalize(weightPlayFrame, DEF_PLAY);
    }

    public double getWeightQueueFrame() {
        return normalize(weightQueueFrame, DEF_QUEUE);
    }

    public double getWeightLeftPanelSettings() {
        return normalize(weightLeftPanelSettings, DEF_LEFT_SET);
    }

    public double getWeightCenterPanelSettings() {
        return normalize(weightCenterPanelSettings, DEF_CENTER_SET);
    }

    public double getWeightRightPanelSettings() {
        return normalize(weightRightPanelSettings, DEF_RIGHT_SET);
    }

    private static double normalize(Double value, double defaultPercent) {
        if (value == null) {
            return defaultPercent;
        }
        double scaled = value;
        if (scaled > 0.0 && scaled <= 1.0) {
            return scaled * 100.0;
        }
        return scaled;
    }
}
