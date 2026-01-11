package core.swing.cards.libraryCard;

import core.main.check.Axis;
import core.objects.GButtonListPanel;
import core.objects.GPanel;
import core.swing.data.transferTrain.ExplorerModel;
import core.swing.data.transferTrain.GroupItem;
import core.swing.data.transferTrain.Item;
import core.swing.data.transferTrain.ItemsPanel;
import core.swing.panels.*;

import java.awt.BorderLayout;
import javax.swing.JButton;

public class LibraryPanelsManager {
    private static final int LIBRARY_ITEM_HEIGHT = 70;
    private static final int LIBRARY_ITEM_GAP = 10;
    private static final int GROUP_INDENT = 20;

    private final ExplorerModel model;
    LibraryCollectionPanel collectionPanel;
    LibrarySeriesPanel seriesPanel;
    LibraryPlaylistPanel playlistPanel;
    LibraryQueuePanel queuePanel;
    LibraryPlayPanel playPanel;
    LibraryCoverPanel coverPane;

    public LibraryPanelsManager(ExplorerModel model) {
        this.model = model;
        collectionPanel = new LibraryCollectionPanel(model);
        seriesPanel = new LibrarySeriesPanel(model);
        playlistPanel = new LibraryPlaylistPanel(model);
        queuePanel = new LibraryQueuePanel(model);
        playPanel = new LibraryPlayPanel(model);
        coverPane = new LibraryCoverPanel(model);
    }


    public LibraryCollectionPanel getCollectionPanel() {
        return collectionPanel;
    }

    public LibrarySeriesPanel getSeriesPanel() {
        return seriesPanel;
    }

    public LibraryPlaylistPanel getPlaylistPanel() {
        return playlistPanel;
    }

    public LibraryQueuePanel getQueuePanel() {
        return queuePanel;
    }

    public LibraryPlayPanel getPlayPanel() {
        return playPanel;
    }

    public LibraryCoverPanel getCoverPanel() {
        return coverPane;
    }

    class LibraryCollectionPanel extends CollectionPanel {
        private final GButtonListPanel listPanel;

        public LibraryCollectionPanel(ExplorerModel model) {
            super(model);
            removeAll();
            listPanel = new GButtonListPanel(LIBRARY_ITEM_HEIGHT, LIBRARY_ITEM_GAP);
            add(listPanel, BorderLayout.CENTER);
            ItemsPanel itemsPanel = model.itemsPanelByName(CollectionPanel.name_id);
            refreshItems(itemsPanel, listPanel);
            model.addListener(itemsPanel, evt -> refreshItems(itemsPanel, listPanel));
        }
    }

    class LibrarySeriesPanel extends SeriesPanel {
        private final GButtonListPanel listPanel;

        public LibrarySeriesPanel(ExplorerModel model) {
            super(model);
            removeAll();
            listPanel = new GButtonListPanel(LIBRARY_ITEM_HEIGHT, LIBRARY_ITEM_GAP);
            add(listPanel, BorderLayout.CENTER);
            ItemsPanel itemsPanel = model.itemsPanelByName(SeriesPanel.name_id);
            refreshItems(itemsPanel, listPanel);
            model.addListener(itemsPanel, evt -> refreshItems(itemsPanel, listPanel));
        }
    }

    class LibraryPlaylistPanel extends PlaylistPanel {

        public LibraryPlaylistPanel(ExplorerModel model) {
            super(model);
        }
    }

    class LibraryQueuePanel extends QueuePanel {

        public LibraryQueuePanel(ExplorerModel model) {
            super(model);
        }
    }

    class LibraryPlayPanel extends PlayPanel {

        public LibraryPlayPanel(ExplorerModel model) {
            super(model);
        }
    }

    class LibraryCoverPanel extends CoverPanel {

        public LibraryCoverPanel(ExplorerModel model) {
            super(model);
        }
    }

    private void refreshItems(ItemsPanel itemsPanel, GButtonListPanel listPanel) {
        listPanel.clearItems();
        for (int i = 0; i < itemsPanel.items().size(); i++) {
            Item item = itemsPanel.items().getElementAt(i);
            if (item instanceof GroupItem group) {
                listPanel.addItemComponent(buildGroupPanel(group, itemsPanel));
            } else {
                listPanel.addItemButton(item.name());
            }
        }
    }

    private GPanel buildGroupPanel(GroupItem group, ItemsPanel itemsPanel) {
        GPanel container = new GPanel(Axis.Y_AX);
        JButton header = new JButton(groupTitle(group));
        header.addActionListener(e -> model.toggleGroup(itemsPanel, group));
        container.add(header);

        if (group.isExpanded()) {
            GButtonListPanel groupListPanel = new GButtonListPanel(LIBRARY_ITEM_HEIGHT, LIBRARY_ITEM_GAP, false);
            groupListPanel.setBorder(0, GROUP_INDENT, 0, 0);
            refreshGroupItems(group, groupListPanel);
            container.add(groupListPanel);
        }
        return container;
    }

    private void refreshGroupItems(GroupItem group, GButtonListPanel listPanel) {
        listPanel.clearItems();
        for (int i = 0; i < group.items().size(); i++) {
            listPanel.addItemButton(group.items().getElementAt(i).name());
        }
    }

    private String groupTitle(GroupItem group) {
        return (group.isExpanded() ? "[-] " : "[+] ") + group.name();
    }
}
