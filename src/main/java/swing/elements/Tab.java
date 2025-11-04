package swing.elements;

import java.util.List;

public enum Tab {
    HOME("Home", "PageHome"),
    MANAGE("Manage", "PageManage"),
    GITHUB_LINK("Github link", "GITHUB_LINK"),
    SETTINGS("Settings", "Settings");

    static final Tab DEFAULT_TAB = HOME;

    private final String label;
    private final String card;

    Tab(String label, String card) { this.label = label; this.card = card; }

    static List<Tab> navTabs() { return List.of(HOME, MANAGE, GITHUB_LINK); }

    public String getLabel() {
        return label;
    }

    public String getCard() {
        return card;
    }
}
