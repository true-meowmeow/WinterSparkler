package core.swing.data.transferTrain;

import java.util.ArrayList;
import java.util.List;

public class ItemsPanel {
    private final String name;
    private final List<Item> items = new ArrayList<>();

    public ItemsPanel(String name) { this.name = name; }

    public String name() { return name; }

    public List<Item> items() { return items; }

    @Override public String toString() { return name; }
}
