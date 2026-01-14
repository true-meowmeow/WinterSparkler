package demo.explorer;

import java.util.ArrayList;
import java.util.List;

public final class Folder {
    private final String name;
    private final List<Item> items = new ArrayList<>();

    public Folder(String name) { this.name = name; }

    public String name() { return name; }

    public List<Item> items() { return items; }

    @Override public String toString() { return name; }
}
