package core.config;

public final class GridProperties {
    private static final GridProperties INSTANCE = load();

    private final int preferredWidth;
    private final int preferredHeight;
    private final int minWidthCap;
    private final int extraMinHeight;
    private final int bottomRowHeight;

    private GridProperties(int preferredWidth,
                           int preferredHeight,
                           int minWidthCap,
                           int extraMinHeight,
                           int bottomRowHeight) {
        this.preferredWidth = preferredWidth;
        this.preferredHeight = preferredHeight;
        this.minWidthCap = minWidthCap;
        this.extraMinHeight = extraMinHeight;
        this.bottomRowHeight = bottomRowHeight;
    }

    public static GridProperties get() {
        return INSTANCE;
    }

    private static GridProperties load() {
        PropertyFile props = PropertyFile.load(
                GridProperties.class,
                ConfigFiles.DEFAULTS,
                ConfigFiles.USER_OVERRIDES
        );

        return new GridProperties(
                requireInt(props, "grid.prefWidth"),
                requireInt(props, "grid.prefHeight"),
                requireInt(props, "grid.minWidthCap"),
                requireInt(props, "grid.extraMinHeight"),
                requireInt(props, "grid.bottomRowHeight")
        );
    }

    private static int requireInt(PropertyFile props, String key) {
        Integer value = props.integer(key);
        if (value == null) {
            throw new IllegalStateException("Missing configuration property: " + key);
        }
        return value;
    }

    public int preferredWidth() {
        return preferredWidth;
    }

    public int preferredHeight() {
        return preferredHeight;
    }

    public int minWidthCap() {
        return minWidthCap;
    }

    public int extraMinHeight() {
        return extraMinHeight;
    }

    public int bottomRowHeight() {
        return bottomRowHeight;
    }
}
