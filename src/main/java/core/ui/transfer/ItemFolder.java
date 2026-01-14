package core.ui.transfer;

import javax.swing.DefaultListModel;

public class ItemFolder {
    private final String name;
    private final DefaultListModel<Item> items = new DefaultListModel<>();

    public ItemFolder(String name) { this.name = name; }

    public String name() { return name; }

    public DefaultListModel<Item> items() { return items; }

    @Override public String toString() { return name; }
}
