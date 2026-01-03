package core.swing.cards.manageCard;

import core.main.check.Axis;
import core.objects.GPanel;
import core.swing.data.transferTrain.ExplorerModel;
import core.swing.data.transferTrain.ItemsPanel;
import core.swing.panels.*;

import java.awt.BorderLayout;
import java.awt.Label;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ManagePanelsManager {
    private static final int MANAGE_ACTION_HEIGHT = 80;

    ManageCollectionPanel collectionPanel;
    ManageSeriesPanel seriesPanel;
    ManageExplorerPanel explorerPanel;
    ManagePlayPanel playPanel;
    ManageQueuePanel queuePanel;


    public ManagePanelsManager(ExplorerModel model) {
        collectionPanel = new ManageCollectionPanel(model);
        seriesPanel = new ManageSeriesPanel(model);
        explorerPanel = new ManageExplorerPanel(model);
        playPanel = new ManagePlayPanel(model);
        queuePanel = new ManageQueuePanel(model);
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

    class ManageCollectionPanel extends CollectionPanel {
        public ManageCollectionPanel(ExplorerModel model) {
            super(model);
            add(buildManageActionsPanel("Создать коллекцию", "Создать группу"), BorderLayout.SOUTH);
        }
    }

    class ManageSeriesPanel extends SeriesPanel {
        public ManageSeriesPanel(ExplorerModel model) {
            super(model);
            add(buildManageActionsPanel("Создать серию", "Создать группу"), BorderLayout.SOUTH);
        }

    }

    class ManageExplorerPanel extends ExplorerPanel {
        public ManageExplorerPanel(ExplorerModel model) {
            super(model);
            init();
        }
    }

    class ManagePlayPanel extends PlayPanel {
        public ManagePlayPanel(ExplorerModel model) {
            super(model);
        }
    }

    class ManageQueuePanel extends QueuePanel {
        public ManageQueuePanel(ExplorerModel model) {
            super(model);
        }
    }

    private GPanel buildManageActionsPanel(String firstTitle, String secondTitle) {
        GPanel container = new GPanel(Axis.Y_AX);
        container.add(buildManageActionPanel(firstTitle));
        container.add(buildManageActionPanel(secondTitle));
        return container;
    }

    private GPanel buildManageActionPanel(String title) {
        GPanel panel = new GPanel();
        panel.setPreferredSize(GPanel.MAX_INT, MANAGE_ACTION_HEIGHT);
        panel.setMaximumSize(GPanel.MAX_INT, MANAGE_ACTION_HEIGHT);
        panel.setMinimumSize(GPanel.ZERO_INT, MANAGE_ACTION_HEIGHT);

        Label label = new Label(title);
        panel.add(label);

        MouseAdapter listener = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                System.out.println("Нажатие: " + title);
            }
        };
        panel.addMouseListener(listener);
        label.addMouseListener(listener);
        return panel;
    }
}


