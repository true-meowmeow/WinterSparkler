package core.config;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

public final class SettingsLoader {
    private static final String RESOURCE_PATH = "/swing/settings.properties";
    private static final AppSettings SETTINGS = load();

    private SettingsLoader() {}

    public static AppSettings get() {
        return SETTINGS;
    }

    private static Integer getInt(Properties p, String key) {
        String v = p.getProperty(key);
        if (v == null || v.isBlank()) return null;
        try { return Integer.parseInt(v.trim()); } catch (NumberFormatException ignored) { return null; }
    }

    private static Double getDouble(Properties p, String key) {
        String v = p.getProperty(key);
        if (v == null || v.isBlank()) return null;
        try { return Double.parseDouble(v.trim()); } catch (NumberFormatException ignored) { return null; }
    }

    private static AppSettings load() {
        Properties props = new Properties();
        try (InputStream in = SettingsLoader.class.getResourceAsStream(RESOURCE_PATH)) {
            if (in != null) {
                // Use UTF-8 reader so comments/values are read robustly
                try (InputStreamReader r = new InputStreamReader(in, StandardCharsets.UTF_8)) {
                    props.load(r);
                }
            }
        } catch (IOException ignored) {
        }

        AppSettings s = new AppSettings();
        // Dimensions
        s.titleControlButtonWidth = getInt(props, "titleControlButtonWidth");
        s.titleControlButtonHeight = getInt(props, "titleControlButtonHeight");
        s.titleNavigationButtonWidth = getInt(props, "titleNavigationButtonWidth");
        s.titleNavigationButtonHeight = getInt(props, "titleNavigationButtonHeight");
        s.titleNavigationSettingsButtonWidth = getInt(props, "titleNavigationSettingsButtonWidth");
        s.titleNavigationSettingsButtonHeight = getInt(props, "titleNavigationSettingsButtonHeight");

        // Weights
        s.weightLeftSidePageHome = getDouble(props, "weightLeftSidePageHome");
        s.weightRightSidePageHome = getDouble(props, "weightRightSidePageHome");
        s.weightCollectionsFrame = getDouble(props, "weightCollectionsFrame");
        s.weightSeriesFrame = getDouble(props, "weightSeriesFrame");
        s.weightPlayFrame = getDouble(props, "weightPlayFrame");
        s.weightQueueFrame = getDouble(props, "weightQueueFrame");
        s.weightLeftPanelSettings = getDouble(props, "weightLeftPanelSettings");
        s.weightCenterPanelSettings = getDouble(props, "weightCenterPanelSettings");
        s.weightRightPanelSettings = getDouble(props, "weightRightPanelSettings");

        return s;
    }
}
