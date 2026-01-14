package ui.cards.manage;

import ui.layout.Axis;
import ui.components.BasePanel;
import ui.panels.*;
import ui.transfer.ItemTransferModel;

import java.awt.BorderLayout;
import java.awt.Label;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ManagePanels {
    private static final int MANAGE_ACTION_HEIGHT = 80;

    ManageCollectionPanel collectionPanel;
    ManageSeriesPanel seriesPanel;
    ManageExplorerPanel explorerPanel;
    ManagePlayPanel playPanel;
    ManageQueuePanel queuePanel;


    public ManagePanels(ItemTransferModel model) {
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
        public ManageCollectionPanel(ItemTransferModel model) {
            super(model);
            add(buildManageActionsPanel("Создать коллекцию", "Создать группу"), BorderLayout.SOUTH);
        }
    }

    class ManageSeriesPanel extends SeriesPanel {
        public ManageSeriesPanel(ItemTransferModel model) {
            super(model);
            add(buildManageActionsPanel("Создать серию", "Создать группу"), BorderLayout.SOUTH);
        }

    }

    class ManageExplorerPanel extends ExplorerPanel {
        public ManageExplorerPanel(ItemTransferModel model) {
            super(model);
            init();
        }
    }

    class ManagePlayPanel extends PlayPanel {
        public ManagePlayPanel(ItemTransferModel model) {
            super(model);
        }
    }

    class ManageQueuePanel extends QueuePanel {
        public ManageQueuePanel(ItemTransferModel model) {
            super(model);
        }
    }

    private BasePanel buildManageActionsPanel(String firstTitle, String secondTitle) {
        BasePanel container = new BasePanel(Axis.Y_AX);
        container.add(buildManageActionPanel(firstTitle));
        container.add(buildManageActionPanel(secondTitle));
        return container;
    }

    private BasePanel buildManageActionPanel(String title) {
        BasePanel panel = new BasePanel();
        panel.setPreferredSize(BasePanel.MAX_INT, MANAGE_ACTION_HEIGHT);
        panel.setMaximumSize(BasePanel.MAX_INT, MANAGE_ACTION_HEIGHT);
        panel.setMinimumSize(BasePanel.ZERO_INT, MANAGE_ACTION_HEIGHT);

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


