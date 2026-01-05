package core.swing.data.transferTrain;

import core.swing.panels.*;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.HashMap;
import java.util.Map;

public class ExplorerModel {


    private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);

    private final Map<String, ItemsPanel> foldersByName = new HashMap<>();
    private final Map<Item, ItemsPanel> parentByItem = new HashMap<>();

    public ExplorerModel() {
        createPanels();
        testData();
    }

    public ItemsPanel addItemsPanel(String name) {
        ItemsPanel i = new ItemsPanel(name);
        foldersByName.put(name, i);
        return i;
    }

    public ItemsPanel itemsPanelByName(String name) {
        ItemsPanel i = foldersByName.get(name);
        if (i == null) throw new IllegalArgumentException("No ItemsPanel: " + name);
        return i;
    }

    public void addItem(ItemsPanel itemsPanel, Item item) {
        itemsPanel.items().addElement(item);
        parentByItem.put(item, itemsPanel);
        pcs.firePropertyChange(changedProperty(itemsPanel), null, item);
    }

    public ItemsPanel parentOf(Item item) {
        return parentByItem.get(item);
    }

    public void moveItem(Item item, ItemsPanel targetItems) {
        ItemsPanel from = parentByItem.get(item);
        if (from == null) return;
        if (from == targetItems) return;

        from.items().removeElement(item);
        targetItems.items().addElement(item);
        parentByItem.put(item, targetItems);

        MoveEvent event = new MoveEvent(item, from, targetItems);
        pcs.firePropertyChange(moveProperty(from), null, event);
        pcs.firePropertyChange(moveProperty(targetItems), null, event);
    }

    public void addListener(PropertyChangeListener l) {
        pcs.addPropertyChangeListener(l);
    }

    public void addListener(ItemsPanel itemsPanel, PropertyChangeListener l) {
        pcs.addPropertyChangeListener(changedProperty(itemsPanel), l);
        pcs.addPropertyChangeListener(moveProperty(itemsPanel), l);
    }

    private static String changedProperty(ItemsPanel itemsPanel) {
        System.out.println("changed:" + itemsPanel.name());
        return "changed:" + itemsPanel.name();
    }

    private static String moveProperty(ItemsPanel itemsPanel) {
        System.out.println("move:" + itemsPanel.name());
        return "move:" + itemsPanel.name();
    }

    private void createPanels() {
        addItemsPanel(CollectionPanel.name_id);
        addItemsPanel(CoverPanel.name_id);
        addItemsPanel(ExplorerPanel.name_id);
        addItemsPanel(PlaylistPanel.name_id);
        addItemsPanel(PlayPanel.name_id);
        addItemsPanel(QueuePanel.name_id);
        addItemsPanel(SeriesPanel.name_id);
    }

    private void testData() {

        //addItem(itemsPanelByName(PlaylistPanel.name_id), new Item("name 1"));
        //addItem(itemsPanelByName(PlaylistPanel.name_id), new Item("name 2"));
        //addItem(itemsPanelByName(PlaylistPanel.name_id), new Item("name 3"));
    }
}
