package core.swing.cards.manageCard;

import core.swing.panels.ExplorerPanel;
import core.swing.panels.PlayPanel;
import core.swing.panels.QueuePanel;
import core.swing.panels.SeriesPanel;

public class ManagePanelsManager {
    ManageCollectionPanel collectionPanel = new ManageCollectionPanel();
    ManageSeriesPanel seriesPanel = new ManageSeriesPanel();
    ManageExplorerPanel explorerPanel = new ManageExplorerPanel();
    ManagePlayPanel playPanel = new ManagePlayPanel();
    ManageQueuePanel queuePanel = new ManageQueuePanel();

    public ManagePanelsManager() {

    }

    public ManageCollectionPanel getCollectionPanel() {
        return collectionPanel;
    }

    public ManageSeriesPanel getSeriesPanel() {
        return seriesPanel;
    }

    public ManageExplorerPanel getExplorerPanel() {
        return explorerPanel;
    }

    public ManagePlayPanel getPlayPanel() {
        return playPanel;
    }

    public ManageQueuePanel getQueuePanel() {
        return queuePanel;
    }

    class ManageCollectionPanel extends core.swing.panels.CollectionPanel {
        public ManageCollectionPanel() {
        }
     }
    class ManageSeriesPanel extends SeriesPanel {

    }
     class ManageExplorerPanel extends ExplorerPanel {
         public ManageExplorerPanel() {
             super();
             init();
         }
     }
     class ManagePlayPanel extends PlayPanel {

    }
     class ManageQueuePanel extends QueuePanel {

    }
}


