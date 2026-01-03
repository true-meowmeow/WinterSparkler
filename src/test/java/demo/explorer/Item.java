package demo.explorer;

public final class Item {
    private final String name;

    public Item(String name) { this.name = name; }

    public String name() { return name; }

    @Override public String toString() { return name; }
}
