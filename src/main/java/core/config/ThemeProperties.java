package core.config;

import java.awt.*;
import java.util.Locale;

public final class ThemeProperties {
    private static final ThemeProperties INSTANCE = load();

    private final Color columnOneColor;
    private final Color columnTwoColor;
    private final Color columnThreeBackgroundColor;
    private final Color gridPanelOneColor;
    private final Color gridPanelTwoColor;
    private final Color gridPanelThreeColor;
    private final Color gridPanelFourColor;
    private final Color textColor;
    private final Font cellFont;
    private final float demoPanelTitleSize;

    private ThemeProperties(Color columnOneColor,
                            Color columnTwoColor,
                            Color columnThreeBackgroundColor,
                            Color gridPanelOneColor,
                            Color gridPanelTwoColor,
                            Color gridPanelThreeColor,
                            Color gridPanelFourColor,
                            Color textColor,
                            Font cellFont,
                            float demoPanelTitleSize) {
        this.columnOneColor = columnOneColor;
        this.columnTwoColor = columnTwoColor;
        this.columnThreeBackgroundColor = columnThreeBackgroundColor;
        this.gridPanelOneColor = gridPanelOneColor;
        this.gridPanelTwoColor = gridPanelTwoColor;
        this.gridPanelThreeColor = gridPanelThreeColor;
        this.gridPanelFourColor = gridPanelFourColor;
        this.textColor = textColor;
        this.cellFont = cellFont;
        this.demoPanelTitleSize = demoPanelTitleSize;
    }

    public static ThemeProperties get() {
        return INSTANCE;
    }

    private static ThemeProperties load() {
        PropertyFile props = PropertyFile.load(
                ThemeProperties.class,
                ConfigFiles.DEFAULTS,
                ConfigFiles.USER_OVERRIDES
        );

        Color col1 = requireColor(props, "color.col1");
        Color col2 = requireColor(props, "color.col2");
        Color col3Bg = requireColor(props, "color.col3Background");
        Color grid1 = requireColor(props, "color.gridP1");
        Color grid2 = requireColor(props, "color.gridP2");
        Color grid3 = requireColor(props, "color.gridP3");
        Color grid4 = requireColor(props, "color.gridP4");
        Color text = requireColor(props, "color.text");
        Font cellFont = loadCellFont(props);
        float demoPanelTitleSize = loadDemoPanelTitleSize(props);

        return new ThemeProperties(
                col1,
                col2,
                col3Bg,
                grid1,
                grid2,
                grid3,
                grid4,
                text,
                cellFont,
                demoPanelTitleSize
        );
    }

    private static Color requireColor(PropertyFile props, String key) {
        String value = props.string(key);
        if (value == null) {
            throw missingProperty(key);
        }
        try {
            return ColorParser.parse(value);
        } catch (IllegalArgumentException ex) {
            throw new IllegalStateException("Invalid color for '" + key + "': " + value, ex);
        }
    }

    private static Font loadCellFont(PropertyFile props) {
        String family = props.string("font.cell.family");
        if (family == null || family.isBlank()) {
            throw missingProperty("font.cell.family");
        }
        String styleValue = props.string("font.cell.style");
        if (styleValue == null || styleValue.isBlank()) {
            throw missingProperty("font.cell.style");
        }
        int style = parseFontStyle(styleValue);
        int size = requireInt(props, "font.cell.size");
        return new Font(family, style, size);
    }

    private static float loadDemoPanelTitleSize(PropertyFile props) {
        Double value = props.doubleValue("font.demoPanelTitleSize");
        if (value == null) {
            throw missingProperty("font.demoPanelTitleSize");
        }
        if (value <= 0) {
            throw new IllegalStateException("font.demoPanelTitleSize must be positive");
        }
        return value.floatValue();
    }

    private static int parseFontStyle(String styleValue) {
        String normalized = styleValue.trim().toLowerCase(Locale.ROOT);
        return switch (normalized) {
            case "plain" -> Font.PLAIN;
            case "bold" -> Font.BOLD;
            case "italic" -> Font.ITALIC;
            case "bolditalic", "italicbold", "bold_italic", "italic_bold", "bold-italic", "italic-bold" ->
                    Font.BOLD | Font.ITALIC;
            default -> throw new IllegalStateException("Unsupported font style: " + styleValue);
        };
    }

    private static int requireInt(PropertyFile props, String key) {
        Integer value = props.integer(key);
        if (value == null) {
            throw missingProperty(key);
        }
        return value;
    }

    private static IllegalStateException missingProperty(String key) {
        return new IllegalStateException("Missing configuration property: " + key);
    }

    public Color columnOneColor() {
        return columnOneColor;
    }

    public Color columnTwoColor() {
        return columnTwoColor;
    }

    public Color columnThreeBackgroundColor() {
        return columnThreeBackgroundColor;
    }

    public Color gridPanelOneColor() {
        return gridPanelOneColor;
    }

    public Color gridPanelTwoColor() {
        return gridPanelTwoColor;
    }

    public Color gridPanelThreeColor() {
        return gridPanelThreeColor;
    }

    public Color gridPanelFourColor() {
        return gridPanelFourColor;
    }

    public Color textColor() {
        return textColor;
    }

    public Font cellFont() {
        return cellFont;
    }

    public float demoPanelTitleSize() {
        return demoPanelTitleSize;
    }
}
