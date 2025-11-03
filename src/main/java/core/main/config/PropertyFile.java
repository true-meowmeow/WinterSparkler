package core.main.config;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

final class PropertyFile {
    private final Properties properties;

    private PropertyFile(Properties properties) {
        this.properties = properties;
    }

    static PropertyFile load(Class<?> resourceAnchor, String... resourcePaths) {
        Properties props = new Properties();
        if (resourcePaths != null) {
            for (String resourcePath : resourcePaths) {
                loadResource(resourceAnchor, resourcePath, props);
            }
        }
        return new PropertyFile(props);
    }

    private static void loadResource(Class<?> resourceAnchor, String resourcePath, Properties props) {
        if (resourcePath == null || resourcePath.isBlank()) {
            return;
        }
        try (InputStream in = resourceAnchor.getResourceAsStream(resourcePath)) {
            if (in == null) {
                return;
            }
            try (InputStreamReader reader = new InputStreamReader(in, StandardCharsets.UTF_8)) {
                props.load(reader);
            }
        } catch (IOException ignored) {
        }
    }

    String string(String key) {
        String value = properties.getProperty(key);
        if (value == null) {
            return null;
        }
        value = value.trim();
        return value.isEmpty() ? null : value;
    }

    String string(String key, String defaultValue) {
        String value = string(key);
        return value != null ? value : defaultValue;
    }

    Integer integer(String key) {
        String value = string(key);
        if (value == null) {
            return null;
        }
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException ignored) {
            return null;
        }
    }

    int integer(String key, int defaultValue) {
        Integer value = integer(key);
        return value != null ? value : defaultValue;
    }

    Double doubleValue(String key) {
        String value = string(key);
        if (value == null) {
            return null;
        }
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException ignored) {
            return null;
        }
    }

    double doubleValue(String key, double defaultValue) {
        Double value = doubleValue(key);
        return value != null ? value : defaultValue;
    }
}
