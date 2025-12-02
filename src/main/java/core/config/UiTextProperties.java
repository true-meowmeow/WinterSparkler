package core.config;

public final class UiTextProperties {
    private static final UiTextProperties INSTANCE = load();

    private final String mergeButtonText;
    private final String splitButtonText;
    private final boolean showSplitText;

    private UiTextProperties(String mergeButtonText, String splitButtonText, boolean showSplitText) {
        this.mergeButtonText = mergeButtonText;
        this.splitButtonText = splitButtonText;
        this.showSplitText = showSplitText;
    }

    public static UiTextProperties get() {
        return INSTANCE;
    }

    private static UiTextProperties load() {
        PropertyFile props = PropertyFile.load(
                UiTextProperties.class,
                ConfigFiles.DEFAULTS,
                ConfigFiles.USER_OVERRIDES
        );

        return new UiTextProperties(
                requireString(props, "ui.button.merge"),
                requireString(props, "ui.button.split"),
                props.bool("ui.button.showSplitText", true)
        );
    }

    private static String requireString(PropertyFile props, String key) {
        String value = props.string(key);
        if (value == null) {
            throw new IllegalStateException("Missing configuration property: " + key);
        }
        return value;
    }

    public String mergeButtonText() {
        return mergeButtonText;
    }

    public String splitButtonText() {
        return splitButtonText;
    }

    public boolean showSplitText() {
        return showSplitText;
    }
}
