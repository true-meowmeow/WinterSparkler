package core.swing.cards.manageCard;

import core.main.check.Axis;
import core.objects.GButtonListPanel;
import core.objects.GPanel;
import core.swing.data.transferTrain.ExplorerModel;
import core.swing.data.transferTrain.Item;
import core.swing.panels.*;

import java.awt.BorderLayout;
import java.awt.Label;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ManagePanelsManager {
    private static final int MANAGE_ACTION_HEIGHT = 80;
    private static final int MANAGE_ITEM_HEIGHT = 70;
    private static final int MANAGE_ITEM_GAP = 10;

    private final ExplorerModel model;
    ManageCollectionPanel collectionPanel;
    ManageSeriesPanel seriesPanel;
    ManageExplorerPanel explorerPanel;
    ManagePlayPanel playPanel;
    ManageQueuePanel queuePanel;


    public ManagePanelsManager(ExplorerModel model) {
        this.model = model;
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
        private final GButtonListPanel listPanel;
        private int collectionCounter = 1;

        public ManageCollectionPanel(ExplorerModel model) {
            super(model);
            removeAll();
            listPanel = new GButtonListPanel(MANAGE_ITEM_HEIGHT, MANAGE_ITEM_GAP);
            add(listPanel, BorderLayout.CENTER);
            add(buildManageActionsPanel(
                    "Создать коллекцию",
                    this::addCollectionItem,
                    "Создать группу",
                    null
            ), BorderLayout.SOUTH);
        }

        private void addCollectionItem() {
            String name = "Коллекция " + collectionCounter++;
            listPanel.addItemButton(name);
            model.addItem(model.itemsPanelByName(CollectionPanel.name_id), new Item(name));
        }
    }

    class ManageSeriesPanel extends SeriesPanel {
        private final GButtonListPanel listPanel;
        private int seriesCounter = 1;

        public ManageSeriesPanel(ExplorerModel model) {
            super(model);
            removeAll();
            listPanel = new GButtonListPanel(MANAGE_ITEM_HEIGHT, MANAGE_ITEM_GAP);
            add(listPanel, BorderLayout.CENTER);
            add(buildManageActionsPanel(
                    "Создать серию",
                    this::addSeriesItem,
                    "Создать группу",
                    null
            ), BorderLayout.SOUTH);
        }

        private void addSeriesItem() {
            String name = "Серия " + seriesCounter++;
            listPanel.addItemButton(name);
            model.addItem(model.itemsPanelByName(SeriesPanel.name_id), new Item(name));
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

    private GPanel buildManageActionsPanel(String firstTitle, Runnable firstAction,
                                           String secondTitle, Runnable secondAction) {
        GPanel container = new GPanel(Axis.Y_AX);
        container.add(buildManageActionPanel(firstTitle, firstAction));
        container.add(buildManageActionPanel(secondTitle, secondAction));
        return container;
    }

    private GPanel buildManageActionPanel(String title, Runnable action) {
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
                if (action != null) {
                    action.run();
                }
            }
        };
        panel.addMouseListener(listener);
        label.addMouseListener(listener);
        return panel;
    }
}
