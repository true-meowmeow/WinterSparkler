package core.ui.transfer;

import core.ui.panels.*;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.HashMap;
import java.util.Map;

public class ItemTransferModel {


    private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);

    private final Map<String, ItemFolder> foldersByName = new HashMap<>();
    private final Map<Item, ItemFolder> parentByItem = new HashMap<>();

    public ItemTransferModel() {
        createPanels();
        testData();
    }

    public ItemFolder addFolder(String name) {
        ItemFolder folder = new ItemFolder(name);
        foldersByName.put(name, folder);
        return folder;
    }

    public ItemFolder folderByName(String name) {
        ItemFolder folder = foldersByName.get(name);
        if (folder == null) throw new IllegalArgumentException("No folder: " + name);
        return folder;
    }

    public void addItem(ItemFolder folder, Item item) {
        folder.items().addElement(item);
        parentByItem.put(item, folder);
        pcs.firePropertyChange(changedProperty(folder), null, item);
    }

    public ItemFolder parentOf(Item item) {
        return parentByItem.get(item);
    }

    public void moveItem(Item item, ItemFolder targetFolder) {
        ItemFolder from = parentByItem.get(item);
        if (from == null) return;
        if (from == targetFolder) return;

        from.items().removeElement(item);
        targetFolder.items().addElement(item);
        parentByItem.put(item, targetFolder);

        ItemMoveEvent event = new ItemMoveEvent(item, from, targetFolder);
        pcs.firePropertyChange(moveProperty(from), null, event);
        pcs.firePropertyChange(moveProperty(targetFolder), null, event);
    }

    public void addListener(PropertyChangeListener l) {
        pcs.addPropertyChangeListener(l);
    }

    public void addListener(ItemFolder folder, PropertyChangeListener l) {
        pcs.addPropertyChangeListener(changedProperty(folder), l);
        pcs.addPropertyChangeListener(moveProperty(folder), l);
    }

    private static String changedProperty(ItemFolder folder) {
        System.out.println("changed:" + folder.name());
        return "changed:" + folder.name();
    }

    private static String moveProperty(ItemFolder folder) {
        System.out.println("move:" + folder.name());
        return "move:" + folder.name();
    }

    private void createPanels() {
        addFolder(CollectionPanel.name_id);
        addFolder(CoverPanel.name_id);
        addFolder(ExplorerPanel.name_id);
        addFolder(PlaylistPanel.name_id);
        addFolder(PlayPanel.name_id);
        addFolder(QueuePanel.name_id);
        addFolder(SeriesPanel.name_id);
    }

    private void testData() {

        //addItem(folderByName(PlaylistPanel.name_id), new Item("name 1"));
        //addItem(folderByName(PlaylistPanel.name_id), new Item("name 2"));
        //addItem(folderByName(PlaylistPanel.name_id), new Item("name 3"));
    }
}
