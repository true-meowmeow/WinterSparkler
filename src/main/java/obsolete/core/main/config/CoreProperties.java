package obsolete.core.main.config;

public final class CoreProperties {
    private static final CoreProperties INSTANCE = load();

    private final String appName;
    private final String appVersion;
    private final String appLink;
    private final int windowWidth;
    private final int windowHeight;
    private final String iconPath;
    private final String preferencesRootPath;
    private final double defaultScale;

    private CoreProperties(String appName,
                           String appVersion,
                           String appLink,
                           int windowWidth,
                           int windowHeight,
                           String iconPath,
                           String preferencesRootPath,
                           double defaultScale) {
        this.appName = appName;
        this.appVersion = appVersion;
        this.appLink = appLink;
        this.windowWidth = windowWidth;
        this.windowHeight = windowHeight;
        this.iconPath = iconPath;
        this.preferencesRootPath = preferencesRootPath;
        this.defaultScale = defaultScale;
    }

    public static CoreProperties get() {
        return INSTANCE;
    }


    private static CoreProperties load() {
        PropertyFile props = PropertyFile.load(
                CoreProperties.class,
                ConfigFiles.DEFAULTS,
                ConfigFiles.USER_OVERRIDES
        );

        String appName = requiredString(props, "app.name");
        String appVersion = requiredString(props, "app.version");
        String appLink = requiredString(props, "app.link");
        int windowWidth = requiredInteger(props, "window.width");
        int windowHeight = requiredInteger(props, "window.height");
        String iconPath = requiredString(props, "icon.path");
        String preferencesRootPath = requiredString(props, "prefs.root");
        double defaultScale = requiredDouble(props, "ui.scale.default");

        return new CoreProperties(
                appName,
                appVersion,
                appLink,
                windowWidth,
                windowHeight,
                iconPath,
                preferencesRootPath,
                defaultScale
        );
    }

    private static String requiredString(PropertyFile props, String key) {
        String value = props.string(key);
        if (value == null) {
            throw missingProperty(key);
        }
        return value;
    }

    private static int requiredInteger(PropertyFile props, String key) {
        Integer value = props.integer(key);
        if (value == null) {
            throw missingProperty(key);
        }
        return value;
    }

    private static double requiredDouble(PropertyFile props, String key) {
        Double value = props.doubleValue(key);
        if (value == null) {
            throw missingProperty(key);
        }
        return value;
    }

    private static IllegalStateException missingProperty(String key) {
        return new IllegalStateException("Missing required property: " + key);
    }

    public String appName() {
        return appName;
    }

    public String appVersion() {
        return appVersion;
    }

    public String appLink() {
        return appLink;
    }

    public int windowWidth() {
        return windowWidth;
    }

    public int windowHeight() {
        return windowHeight;
    }

    public String iconPath() {
        return iconPath;
    }

    public String preferencesRootPath() {
        return preferencesRootPath;
    }

    public double defaultScale() {
        return defaultScale;
    }
}
