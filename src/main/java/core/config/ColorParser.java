package core.config;

import java.awt.*;

final class ColorParser {
    private ColorParser() {
    }

    static Color parse(String value) {
        if (value == null) {
            throw new IllegalArgumentException("Color value is null");
        }
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
        throw new IllegalArgumentException("Unsupported color format: " + value);
    }
}
