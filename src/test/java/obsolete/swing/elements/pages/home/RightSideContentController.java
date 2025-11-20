package obsolete.swing.elements.pages.home;

import java.util.LinkedHashSet;
import java.util.Set;

public final class RightSideContentController {

    private static final RightSideContentController INSTANCE = new RightSideContentController();

    private final Set<RightSideSwitchable> sections = new LinkedHashSet<>();

    private RightSideContentController() {
    }

    public static RightSideContentController getInstance() {
        return INSTANCE;
    }

    public void reset() {
        sections.clear();
    }

    public void register(RightSideSwitchable section) {
        if (section != null) {
            sections.add(section);
        }
    }

    public void showMode(RightSideMode mode) {
        for (RightSideSwitchable section : sections) {
            section.showMode(mode);
        }
    }
}

