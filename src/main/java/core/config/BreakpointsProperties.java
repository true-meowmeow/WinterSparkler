package core.config;

public final class BreakpointsProperties {
    private static final BreakpointsProperties INSTANCE = load();

    private final int threeColumnWidth;
    private final int mergeHideColumnsWidth;
    private final int widthHideP4;
    private final int widthHideP1P4;
    private final int widthHideP2Mid;
    private final int heightP4ToP2;
    private final int heightP1P2ToP3;
    private final int heightP2ToP3;

    private BreakpointsProperties(int threeColumnWidth,
                                  int mergeHideColumnsWidth,
                                  int widthHideP4,
                                  int widthHideP1P4,
                                  int widthHideP2Mid,
                                  int heightP4ToP2,
                                  int heightP1P2ToP3,
                                  int heightP2ToP3) {
        this.threeColumnWidth = threeColumnWidth;
        this.mergeHideColumnsWidth = mergeHideColumnsWidth;
        this.widthHideP4 = widthHideP4;
        this.widthHideP1P4 = widthHideP1P4;
        this.widthHideP2Mid = widthHideP2Mid;
        this.heightP4ToP2 = heightP4ToP2;
        this.heightP1P2ToP3 = heightP1P2ToP3;
        this.heightP2ToP3 = heightP2ToP3;
    }

    public static BreakpointsProperties get() {
        return INSTANCE;
    }

    private static BreakpointsProperties load() {
        PropertyFile props = PropertyFile.load(
                BreakpointsProperties.class,
                ConfigFiles.DEFAULTS,
                ConfigFiles.USER_OVERRIDES
        );

        return new BreakpointsProperties(
                requireInt(props, "breakpoints.threeColWidth"),
                requireInt(props, "breakpoints.mergeHideColsWidth"),
                requireInt(props, "breakpoints.widthHideP4"),
                requireInt(props, "breakpoints.widthHideP1P4"),
                requireInt(props, "breakpoints.widthHideP2Mid"),
                requireInt(props, "breakpoints.heightP4ToP2"),
                requireInt(props, "breakpoints.heightP1P2ToP3"),
                requireInt(props, "breakpoints.heightP2ToP3")
        );
    }

    private static int requireInt(PropertyFile props, String key) {
        Integer value = props.integer(key);
        if (value == null) {
            throw new IllegalStateException("Missing configuration property: " + key);
        }
        return value;
    }

    public int threeColumnWidth() {
        return threeColumnWidth;
    }

    public int mergeHideColumnsWidth() {
        return mergeHideColumnsWidth;
    }

    public int widthHideP4() {
        return widthHideP4;
    }

    public int widthHideP1P4() {
        return widthHideP1P4;
    }

    public int widthHideP2Mid() {
        return widthHideP2Mid;
    }

    public int heightP4ToP2() {
        return heightP4ToP2;
    }

    public int heightP1P2ToP3() {
        return heightP1P2ToP3;
    }

    public int heightP2ToP3() {
        return heightP2ToP3;
    }
}
