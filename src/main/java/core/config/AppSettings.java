package core.config;

import java.awt.*;

public class AppSettings {
    // Dimensions (nullable to allow defaults)
    public Integer titleControlButtonWidth;
    public Integer titleControlButtonHeight;
    public Integer titleNavigationButtonWidth;
    public Integer titleNavigationButtonHeight;
    public Integer titleNavigationSettingsButtonWidth;
    public Integer titleNavigationSettingsButtonHeight;

    // Weights (nullable; accept 0..1 fractions or 0..100 percents)
    public Double weightLeftSidePageHome;
    public Double weightRightSidePageHome;
    public Double weightCollectionsFrame;
    public Double weightSeriesFrame;
    public Double weightPlayFrame;
    public Double weightQueueFrame;
    public Double weightLeftPanelSettings;
    public Double weightCenterPanelSettings;
    public Double weightRightPanelSettings;

    // Defaults from previous code
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

    private static double normalize(Double v, double defPercent) {
        if (v == null) return defPercent;
        double x = v;
        if (x > 0.0 && x <= 1.0) return x * 100.0;
        return x;
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
        Double v = weightRightSidePageHome;
        if (v == null && weightLeftSidePageHome != null) {
            double left = getWeightLeftSidePageHome();
            return 100.0 - left;
        }
        return normalize(v, DEF_RIGHT_HOME);
    }

    public double getWeightCollectionsFrame() { return normalize(weightCollectionsFrame, DEF_COLL); }
    public double getWeightSeriesFrame() { return normalize(weightSeriesFrame, DEF_SER); }
    public double getWeightPlayFrame() { return normalize(weightPlayFrame, DEF_PLAY); }
    public double getWeightQueueFrame() { return normalize(weightQueueFrame, DEF_QUEUE); }
    public double getWeightLeftPanelSettings() { return normalize(weightLeftPanelSettings, DEF_LEFT_SET); }
    public double getWeightCenterPanelSettings() { return normalize(weightCenterPanelSettings, DEF_CENTER_SET); }
    public double getWeightRightPanelSettings() { return normalize(weightRightPanelSettings, DEF_RIGHT_SET); }
}
