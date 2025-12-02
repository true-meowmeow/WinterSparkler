package core.panels.cards.libraryCard;

import core.panels.panels.*;

public class LibraryPanelsManager {
    LibraryCollectionPanel collectionPanel = new LibraryCollectionPanel();
    LibrarySeriesPanel seriesPanel = new LibrarySeriesPanel();
    LibraryPlaylistPanel playlistPanel = new LibraryPlaylistPanel();
    LibraryQueuePanel queuePanel = new LibraryQueuePanel();
    LibraryPlayPanel playPanel = new LibraryPlayPanel();
    LibraryCoverPanel coverPane = new LibraryCoverPanel();

    public LibraryPanelsManager() {

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
    }

    class LibrarySeriesPanel extends SeriesPanel {

    }

    class LibraryPlaylistPanel extends PlaylistPanel {

    }

    class LibraryQueuePanel extends QueuePanel {

    }

    class LibraryPlayPanel extends PlayPanel {

    }

    class LibraryCoverPanel extends CoverPanel {

    }
}