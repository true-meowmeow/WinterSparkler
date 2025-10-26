package core.config;

public final class CoreSettings {
    private static final String RESOURCE_PATH = "/properties/core.settings.properties";
    private static final CoreSettings INSTANCE = load();

    private final String appName;
    private final String appVersion;
    private final String windowTitle;
    private final int windowWidth;
    private final int windowHeight;
    private final String iconPath;
    private final String preferencesRootPath;
    private final double defaultScale;

    private CoreSettings(String appName,
                         String appVersion,
                         String windowTitle,
                         int windowWidth,
                         int windowHeight,
                         String iconPath,
                         String preferencesRootPath,
                         double defaultScale) {
        this.appName = appName;
        this.appVersion = appVersion;
        this.windowTitle = windowTitle;
        this.windowWidth = windowWidth;
        this.windowHeight = windowHeight;
        this.iconPath = iconPath;
        this.preferencesRootPath = preferencesRootPath;
        this.defaultScale = defaultScale;
    }

    public static CoreSettings get() {
        return INSTANCE;
    }

    private static CoreSettings load() {    //defaults
        PropertyFile props = PropertyFile.load(CoreSettings.class, RESOURCE_PATH);

        String appName = props.string("app.name", "WinterSparkler");
        String appVersion = props.string("app.version", "version");
        int windowWidth = props.integer("window.width", 1600);
        int windowHeight = props.integer("window.height", 900);
        String iconPath = /*sanitizeIconPath(*/props.string("icon.path", "/icon/Winter Sparkler and Musical Glow2.png");
        String preferencesRootPath = props.string("prefs.root", "/core");
        double defaultScale = props.doubleValue("ui.scale.default", 1.0);

        String configuredTitle = props.string("window.title");
        String windowTitle = configuredTitle != null ? configuredTitle : appName/* + "  " + appVersion*/;

        return new CoreSettings(
                appName,
                appVersion,
                windowTitle,
                windowWidth,
                windowHeight,
                iconPath,
                preferencesRootPath,
                defaultScale
        );
    }

/*    private static String sanitizeIconPath(String path) {
        if (path == null || path.isBlank()) {
            return "/icon/Winter Sparkler and Musical Glow2.png";
        }
        return path.startsWith("/") ? path : "/" + path;
    }*/

    public String appName() {
        return appName;
    }

    public String appVersion() {
        return appVersion;
    }

    public String windowTitle() {
        return windowTitle;
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
