package core.swing.data.transferTrain;

import javax.swing.DefaultListModel;

public class ItemsPanel {
    private final String name;
    private final DefaultListModel<Item> items = new DefaultListModel<>();

    public ItemsPanel(String name) { this.name = name; }

    public String name() { return name; }

    public DefaultListModel<Item> items() { return items; }

    @Override public String toString() { return name; }
}
