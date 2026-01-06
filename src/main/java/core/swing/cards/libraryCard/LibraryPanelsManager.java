package core.swing.cards.libraryCard;

import core.objects.GButtonListPanel;
import core.swing.data.transferTrain.ExplorerModel;
import core.swing.data.transferTrain.ItemsPanel;
import core.swing.panels.*;

import java.awt.BorderLayout;

public class LibraryPanelsManager {
    private static final int LIBRARY_ITEM_HEIGHT = 70;
    private static final int LIBRARY_ITEM_GAP = 10;

    LibraryCollectionPanel collectionPanel;
    LibrarySeriesPanel seriesPanel;
    LibraryPlaylistPanel playlistPanel;
    LibraryQueuePanel queuePanel;
    LibraryPlayPanel playPanel;
    LibraryCoverPanel coverPane;

    public LibraryPanelsManager(ExplorerModel model) {
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
            refreshItems(itemsPanel);
            model.addListener(itemsPanel, evt -> refreshItems(itemsPanel));
        }

        private void refreshItems(ItemsPanel itemsPanel) {
            listPanel.clearItems();
            for (int i = 0; i < itemsPanel.items().size(); i++) {
                listPanel.addItemButton(itemsPanel.items().getElementAt(i).name());
            }
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
            refreshItems(itemsPanel);
            model.addListener(itemsPanel, evt -> refreshItems(itemsPanel));
        }

        private void refreshItems(ItemsPanel itemsPanel) {
            listPanel.clearItems();
            for (int i = 0; i < itemsPanel.items().size(); i++) {
                listPanel.addItemButton(itemsPanel.items().getElementAt(i).name());
            }
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
}
