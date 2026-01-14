package ui.navigation;

import java.util.List;

public enum AppTab {
    HOME("Home", "HOME"),
    LIBRARY("Library", "LIBRARY"),
    MANAGE("Manage", "MANAGE"),
    SEARCH("Search", "SEARCH"),
    SETTINGS("Settings", "SETTINGS");

    public static final AppTab DEFAULT_TAB = LIBRARY;

    private final String label;
    private final String card;

    AppTab(String label, String card) {
        this.label = label;
        this.card = card;
    }

    static List<AppTab> navTabs() {
        return List.of(HOME, LIBRARY, MANAGE);
    }

    static List<AppTab> sideTabs() {
        return List.of(SEARCH, SETTINGS);
    }

    public String getLabel() {
        return label;
    }

    public String getCard() {
        return card;
    }
}
