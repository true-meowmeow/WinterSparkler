package ui.cards.library;

import ui.panels.*;
import ui.transfer.ItemTransferModel;

public class LibraryPanels {
    LibraryCollectionPanel collectionPanel;
    LibrarySeriesPanel seriesPanel;
    LibraryPlaylistPanel playlistPanel;
    LibraryQueuePanel queuePanel;
    LibraryPlayPanel playPanel;
    LibraryCoverPanel coverPane;

    public LibraryPanels(ItemTransferModel model) {
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
        public LibraryCollectionPanel(ItemTransferModel model) {
            super(model);
        }
    }

    class LibrarySeriesPanel extends SeriesPanel {

        public LibrarySeriesPanel(ItemTransferModel model) {
            super(model);
        }
    }

    class LibraryPlaylistPanel extends PlaylistPanel {

        public LibraryPlaylistPanel(ItemTransferModel model) {
            super(model);
        }
    }

    class LibraryQueuePanel extends QueuePanel {

        public LibraryQueuePanel(ItemTransferModel model) {
            super(model);
        }
    }

    class LibraryPlayPanel extends PlayPanel {

        public LibraryPlayPanel(ItemTransferModel model) {
            super(model);
        }
    }

    class LibraryCoverPanel extends CoverPanel {

        public LibraryCoverPanel(ItemTransferModel model) {
            super(model);
        }
    }
}
