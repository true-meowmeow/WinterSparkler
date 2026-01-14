package demo.explorer;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.*;

public final class ExplorerModel {
    private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);

    private final Map<String, Folder> foldersByName = new LinkedHashMap<>();
    private final Map<Item, Folder> parentByItem = new HashMap<>();

    public Folder addFolder(String name) {
        Folder f = new Folder(name);
        foldersByName.put(name, f);
        return f;
    }

    public Folder folderByName(String name) {
        Folder f = foldersByName.get(name);
        if (f == null) throw new IllegalArgumentException("No folder: " + name);
        return f;
    }

    public void addItem(Folder folder, Item item) {
        folder.items().add(item);
        parentByItem.put(item, folder);
        pcs.firePropertyChange("changed", null, null);
    }

    public Folder parentOf(Item item) {
        return parentByItem.get(item);
    }

    public void moveItem(Item item, Folder targetFolder) {
        Folder from = parentByItem.get(item);
        if (from == null) return;
        if (from == targetFolder) return;

        from.items().remove(item);
        targetFolder.items().add(item);
        parentByItem.put(item, targetFolder);

        pcs.firePropertyChange("move", null, new MoveEvent(item, from, targetFolder));
    }

    public void addListener(PropertyChangeListener l) {
        pcs.addPropertyChangeListener(l);
    }
}
