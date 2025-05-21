package swing.objects.dropper;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class DropPanelRegistry {
    private static final Map<String, DropPanel> panels = new HashMap<>();

    public static void register(String name, DropPanel panel) {
        panels.put(name, panel);
    }

    public static DropPanel get(String name) {
        return panels.get(name);
    }

    public static Collection<DropPanel> getAll() {
        return panels.values();
    }

    public static boolean contains(String name) {
        return panels.containsKey(name);
    }

    public static void remove(String name) {
        panels.remove(name);
    }

    public static void clear() {
        panels.clear();
    }
}
