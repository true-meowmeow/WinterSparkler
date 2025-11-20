package core.config;

public final class MergeProperties {
    private static final MergeProperties INSTANCE = load();

    private final int minP3Width;
    private final int minP1Height;

    private MergeProperties(int minP3Width, int minP1Height) {
        this.minP3Width = minP3Width;
        this.minP1Height = minP1Height;
    }

    public static MergeProperties get() {
        return INSTANCE;
    }

    private static MergeProperties load() {
        PropertyFile props = PropertyFile.load(
                MergeProperties.class,
                ConfigFiles.DEFAULTS,
                ConfigFiles.USER_OVERRIDES
        );

        return new MergeProperties(
                requireInt(props, "merge.minP3Width"),
                requireInt(props, "merge.minP1Height")
        );
    }

    private static int requireInt(PropertyFile props, String key) {
        Integer value = props.integer(key);
        if (value == null) {
            throw new IllegalStateException("Missing configuration property: " + key);
        }
        return value;
    }

    public int minP3Width() {
        return minP3Width;
    }

    public int minP1Height() {
        return minP1Height;
    }
}
