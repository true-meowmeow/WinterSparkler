package core.config;

import java.awt.*;

public final class LayoutProperties {
    private static final LayoutProperties INSTANCE = new LayoutProperties();

    private LayoutProperties() {
    }

    public static LayoutProperties get() {
        return INSTANCE;
    }

    public Dimension getTitleControlButton() {
        PropertyFile props = loadProperties();
        int width = requireInt(props, "titleControlButtonWidth");
        int height = requireInt(props, "titleControlButtonHeight");
        return new Dimension(width, height);
    }

    public Dimension getTitleNavigationButton() {
        PropertyFile props = loadProperties();
        int width = requireInt(props, "titleNavigationButtonWidth");
        int height = requireInt(props, "titleNavigationButtonHeight");
        return new Dimension(width, height);
    }

    public Dimension getTitleNavigationSettingsButton() {
        PropertyFile props = loadProperties();
        int width = requireInt(props, "titleNavigationSettingsButtonWidth");
        int height = requireInt(props, "titleNavigationSettingsButtonHeight");
        return new Dimension(width, height);
    }

    public int getBottomFrameHeight() {
        return requireInt(loadProperties(), "bottomFrameHeight");
    }

    public double getWeightLeftSidePageHome() {
        return normalize(requireDouble(loadProperties(), "weightLeftSidePageHome"));
    }

    public double getWeightRightSidePageHome() {
        PropertyFile props = loadProperties();
        Double raw = props.doubleValue("weightRightSidePageHome");
        if (raw == null) {
            double left = normalize(requireDouble(props, "weightLeftSidePageHome"));
            return Math.max(0.0, 100.0 - left);
        }
        return normalize(raw);
    }

    public double getWeightCollectionsFrame() {
        return normalize(requireDouble(loadProperties(), "weightCollectionsFrame"));
    }

    public double getWeightSeriesFrame() {
        return normalize(requireDouble(loadProperties(), "weightSeriesFrame"));
    }

    public double getWeightPlayFrame() {
        return normalize(requireDouble(loadProperties(), "weightPlayFrame"));
    }

    public double getWeightQueueFrame() {
        return normalize(requireDouble(loadProperties(), "weightQueueFrame"));
    }

    public double getWeightLeftPanelSettings() {
        return normalize(requireDouble(loadProperties(), "weightLeftPanelSettings"));
    }

    public double getWeightCenterPanelSettings() {
        return normalize(requireDouble(loadProperties(), "weightCenterPanelSettings"));
    }

    public double getWeightRightPanelSettings() {
        return normalize(requireDouble(loadProperties(), "weightRightPanelSettings"));
    }

    public int getHeightCollectionPanel() {
        return requireInt(loadProperties(), "heightCollectionPanel");
    }

    public int getHeightAddCollectionPanel() {
        return requireInt(loadProperties(), "heightAddCollectionPanel");
    }

    public Color getColorCollectionBasic() {
        return requireColor(loadProperties(), "colorCollectionBasic");
    }

    public Color getColorCollectionOpened() {
        return requireColor(loadProperties(), "colorCollectionOpened");
    }

    public Color getColorCollectionDrag() {
        return requireColor(loadProperties(), "colorCollectionDrag");
    }

    public Color getColorCollectionFocus() {
        return requireColor(loadProperties(), "colorCollectionFocus");
    }

    private static PropertyFile loadProperties() {
        return PropertyFile.load(
                LayoutProperties.class,
                ConfigFiles.DEFAULTS,
                ConfigFiles.USER_OVERRIDES
        );
    }

    private static int requireInt(PropertyFile props, String key) {
        Integer value = props.integer(key);
        if (value == null) {
            throw missingProperty(key);
        }
        return value;
    }

    private static double requireDouble(PropertyFile props, String key) {
        Double value = props.doubleValue(key);
        if (value == null) {
            throw missingProperty(key);
        }
        return value;
    }

    private static IllegalStateException missingProperty(String key) {
        return new IllegalStateException("Missing configuration property: " + key);
    }

    private static double normalize(double value) {
        if (value > 0.0 && value <= 1.0) {
            return value * 100.0;
        }
        return value;
    }

    private static Color requireColor(PropertyFile props, String key) {
        String value = props.string(key);
        if (value == null) {
            throw missingProperty(key);
        }
        try {
            return parseColor(value);
        } catch (IllegalArgumentException ex) {
            throw invalidColor(key, value, ex);
        }
    }

    private static Color parseColor(String value) {
        String normalized = value.trim();
        if (normalized.isEmpty()) {
            throw new IllegalArgumentException("Empty color value");
        }
        if (normalized.startsWith("#")) {
            normalized = normalized.substring(1);
        } else if (normalized.startsWith("0x") || normalized.startsWith("0X")) {
            normalized = normalized.substring(2);
        }
        normalized = normalized.replace("_", "");
        int length = normalized.length();
        long parsed = Long.parseLong(normalized, 16);
        if (length == 6) {
            return new Color((int) parsed);
        }
        if (length == 8) {
            return new Color((int) parsed, true);
        }
        throw new IllegalArgumentException("Unsupported color format");
    }

    private static IllegalStateException invalidColor(String key, String value, Exception cause) {
        return new IllegalStateException("Invalid configuration color for '" + key + "': " + value, cause);
    }
}
