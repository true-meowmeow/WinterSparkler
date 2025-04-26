package swing.ui.components;

import java.util.List;

public enum Tab {
    HOME("Home", "PageHome"),
    COMPONENT("Component", "PageComponent"),
    FAVORITES("Favorites", "Favorites"),
    SETTINGS("Settings", "Settings");

    static final Tab DEFAULT_TAB = HOME;

    final String label;
    final String card;

    Tab(String label, String card) { this.label = label; this.card = card; }

    static List<Tab> navTabs() { return List.of(HOME, COMPONENT, FAVORITES); }
}
