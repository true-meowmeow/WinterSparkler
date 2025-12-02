package core.panels.cards.manageCard;

import core.panels.panels.ExplorerPanel;
import core.panels.panels.PlayPanel;
import core.panels.panels.QueuePanel;
import core.panels.panels.SeriesPanel;

import java.awt.*;

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

    class ManageCollectionPanel extends core.panels.panels.CollectionPanel {
        public ManageCollectionPanel() {
        }

         @Override
         public void createLabel() {
             super.createLabel();
             //setBackground(Color.BLUE);
         }
     }
    class ManageSeriesPanel extends SeriesPanel {

    }
     class ManageExplorerPanel extends ExplorerPanel {

    }
     class ManagePlayPanel extends PlayPanel {

    }
     class ManageQueuePanel extends QueuePanel {

    }
}


