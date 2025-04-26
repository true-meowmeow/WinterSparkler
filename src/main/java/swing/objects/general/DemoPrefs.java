package swing.objects.general;

import com.formdev.flatlaf.*;
import com.formdev.flatlaf.IntelliJTheme.ThemeLaf;
import com.formdev.flatlaf.util.LoggingFacade;
import com.formdev.flatlaf.util.StringUtils;

import javax.swing.*;
import java.io.*;
import java.nio.file.*;
import java.util.Objects;
import java.util.prefs.Preferences;

/**
 * Управление Look & Feel и сохранение его в prefs.
 */
public final class DemoPrefs {

    /** prefs-ключи */
    private static final String KEY_LAF       = "laf";
    private static final String KEY_THEME     = "lafTheme";

    /** префиксы хранения темы */
    private static final String PFX_RES       = "res:";
    private static final String PFX_FILE      = "file:";
    private static final String THEME_UI_KEY  = "__FlatLaf.demo.theme";

    private static Preferences prefs;

    private DemoPrefs() {}                         // util-класс

    /* ────────────────── API ────────────────── */

    public static void init(String rootPath) {
        prefs = Preferences.userRoot().node(Objects.requireNonNull(rootPath));
    }

    public static void setupLaf(String... args) {
        try {
            if (args != null && args.length > 0)                     // из аргумента
                setLaf(args[0]);
            else                                                     // из prefs
                restoreLafFromPrefs();
        } catch (Throwable ex) {                                     // fallback
            LoggingFacade.INSTANCE.logSevere(null, ex);
            FlatLightLaf.setup();
        }

        // автосохранение выбранного LAF
        UIManager.addPropertyChangeListener(e -> {
            if ("lookAndFeel".equals(e.getPropertyName()))
                prefs.put(KEY_LAF, UIManager.getLookAndFeel().getClass().getName());
        });
    }

    private static void restoreLafFromPrefs() throws Exception {
        String lafClass = prefs.get(KEY_LAF, FlatLightLaf.class.getName());

        if (lafClass.equals(ThemeLaf.class.getName()))
            setupIntellijTheme(prefs.get(KEY_THEME, ""));
        else if (lafClass.equals(FlatPropertiesLaf.class.getName()))
            setupPropsTheme(prefs.get(KEY_THEME, ""));
        else
            setLaf(lafClass);
    }

    private static void setupIntellijTheme(String themeSpec) throws IOException {
        if (themeSpec.startsWith(PFX_RES)) {
            // пример: IntelliJTheme.setup(SomePanel.class.getResourceAsStream("/mytheme.json"));
            // оставлено пользователю
            FlatLightLaf.setup();
        } else if (themeSpec.startsWith(PFX_FILE)) {
            try (InputStream in = Files.newInputStream(Path.of(themeSpec.substring(PFX_FILE.length())))) {
                FlatLaf.setup(IntelliJTheme.createLaf(in));
            }
        } else
            FlatLightLaf.setup();

        storeTheme(themeSpec);
    }

    private static void setupPropsTheme(String themeSpec) throws IOException {
        if (themeSpec.startsWith(PFX_FILE)) {
            Path path = Path.of(themeSpec.substring(PFX_FILE.length()));
            String name = StringUtils.removeTrailing(path.getFileName().toString(), ".properties");
            FlatLaf.setup(new FlatPropertiesLaf(name, path.toFile()));
        } else
            FlatLightLaf.setup();

        storeTheme(themeSpec);
    }

    private static void setLaf(String className) throws Exception {
        UIManager.setLookAndFeel(className);
    }

    private static void storeTheme(String spec) {
        if (!spec.isEmpty())
            UIManager.getLookAndFeelDefaults().put(THEME_UI_KEY, spec);
        prefs.put(KEY_THEME, spec);
    }
}
