package core.config;

import java.util.ArrayList;
import java.util.List;

public final class CurveProperties {
    public record CurveValue(int windowWidth, int valueWidth, double alpha) {
    }

    private static final CurveProperties INSTANCE = load();

    private final CurveValue[] col12;
    private final CurveValue[] right;

    private CurveProperties(CurveValue[] col12, CurveValue[] right) {
        this.col12 = col12;
        this.right = right;
    }

    public static CurveProperties get() {
        return INSTANCE;
    }

    public CurveValue[] col12() {
        return col12.clone();
    }

    public CurveValue[] right() {
        return right.clone();
    }

    private static CurveProperties load() {
        PropertyFile props = PropertyFile.load(
                CurveProperties.class,
                ConfigFiles.DEFAULTS,
                ConfigFiles.USER_OVERRIDES
        );

        CurveValue[] col12 = parseCurve(props, "curves.col12");
        CurveValue[] right = parseCurve(props, "curves.right");
        return new CurveProperties(col12, right);
    }

    private static CurveValue[] parseCurve(PropertyFile props, String key) {
        String raw = props.string(key);
        if (raw == null) {
            throw new IllegalStateException("Missing curve definition: " + key);
        }

        String[] entries = raw.split(",");
        List<CurveValue> points = new ArrayList<>(entries.length);
        for (String entry : entries) {
            String token = entry.trim();
            if (token.isEmpty()) {
                continue;
            }
            String[] parts = token.split(":");
            if (parts.length != 3) {
                throw invalidEntry(key, entry, "Expected format windowWidth:valueWidth:alpha");
            }
            try {
                int windowWidth = Integer.parseInt(parts[0].trim());
                int valueWidth = Integer.parseInt(parts[1].trim());
                double alpha = Double.parseDouble(parts[2].trim());
                points.add(new CurveValue(windowWidth, valueWidth, alpha));
            } catch (NumberFormatException ex) {
                throw invalidEntry(key, entry, "Failed to parse numeric values", ex);
            }
        }

        if (points.isEmpty()) {
            throw new IllegalStateException("Curve definition contains no points: " + key);
        }

        return points.toArray(new CurveValue[0]);
    }

    private static IllegalStateException invalidEntry(String key, String value, String message) {
        return new IllegalStateException("Invalid curve entry for '" + key + "': " + value + ". " + message);
    }

    private static IllegalStateException invalidEntry(String key, String value, String message, Exception cause) {
        return new IllegalStateException("Invalid curve entry for '" + key + "': " + value + ". " + message, cause);
    }
}
