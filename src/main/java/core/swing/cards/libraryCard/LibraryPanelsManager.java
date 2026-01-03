package core.swing.cards.libraryCard;

import core.swing.data.transferTrain.ExplorerModel;
import core.swing.panels.*;

public class LibraryPanelsManager {
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
        public LibraryCollectionPanel(ExplorerModel model) {
            super(model);
        }
    }

    class LibrarySeriesPanel extends SeriesPanel {

        public LibrarySeriesPanel(ExplorerModel model) {
            super(model);
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