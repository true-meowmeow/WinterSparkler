package core.main.titleMenuBar;

import java.util.List;

public enum Tab {
    HOME("Home", "HOME"),
    LIBRARY("Library", "LIBRARY"),
    MANAGE("Manage", "MANAGE"),
    SEARCH("Search", "SEARCH"),
    SETTINGS("Settings", "SETTINGS");

    public static final Tab DEFAULT_TAB = LIBRARY;

    private final String label;
    private final String card;

    Tab(String label, String card) {
        this.label = label;
        this.card = card;
    }

    static List<Tab> navTabs() {
        return List.of(HOME, LIBRARY, MANAGE);
    }

    static List<Tab> sideTabs() {
        return List.of(SEARCH, SETTINGS);
    }

    public String getLabel() {
        return label;
    }

    public String getCard() {
        return card;
    }
}