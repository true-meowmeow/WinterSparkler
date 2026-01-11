package core.swing.cards.manageCard;

import core.main.check.Axis;
import core.objects.GButtonListPanel;
import core.objects.GPanel;
import core.swing.data.transferTrain.ExplorerModel;
import core.swing.data.transferTrain.GroupItem;
import core.swing.data.transferTrain.Item;
import core.swing.data.transferTrain.ItemsPanel;
import core.swing.panels.*;

import java.awt.Color;
import java.awt.BorderLayout;
import java.awt.Label;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;

public class ManagePanelsManager {
    private static final int MANAGE_ACTION_HEIGHT = 80;
    private static final int MANAGE_ITEM_HEIGHT = 70;
    private static final int MANAGE_ITEM_GAP = 10;
    private static final int GROUP_INDENT = 20;

    private final ExplorerModel model;
    ManageCollectionPanel collectionPanel;
    ManageSeriesPanel seriesPanel;
    ManageExplorerPanel explorerPanel;
    ManagePlayPanel playPanel;
    ManageQueuePanel queuePanel;

    private Item dragItem;
    private ItemsPanel dragItemsPanel;
    private GroupItem dragGroup;
    private GButtonListPanel dragListPanel;
    private JButton dragButton;
    private Color dragButtonBackground;
    private boolean dragButtonOpaque;
    private boolean dragButtonContentFilled;
    private boolean dragButtonBorderPainted;

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
        private final ItemsPanel itemsPanel;
        private int collectionCounter = 1;
        private int groupCounter = 1;

        public ManageCollectionPanel(ExplorerModel model) {
            super(model);
            removeAll();
            listPanel = new GButtonListPanel(MANAGE_ITEM_HEIGHT, MANAGE_ITEM_GAP);
            itemsPanel = model.itemsPanelByName(CollectionPanel.name_id);
            add(listPanel, BorderLayout.CENTER);
            add(buildManageActionsPanel(
                    "Создать коллекцию",
                    this::addCollectionItem,
                    "Создать группу",
                    this::addGroupItem
            ), BorderLayout.SOUTH);
            initRootReorderList(itemsPanel, listPanel);
            refreshItems(itemsPanel, listPanel);
            model.addListener(itemsPanel, evt -> refreshItems(itemsPanel, listPanel));
        }

        private void addCollectionItem() {
            String name = "Коллекция " + collectionCounter++;
            model.addItem(itemsPanel, new Item(name));
        }

        private void addGroupItem() {
            String name = "Группа " + groupCounter++;
            model.addItem(itemsPanel, new GroupItem(name));
        }
    }

    class ManageSeriesPanel extends SeriesPanel {
        private final GButtonListPanel listPanel;
        private final ItemsPanel itemsPanel;
        private int seriesCounter = 1;

        public ManageSeriesPanel(ExplorerModel model) {
            super(model);
            removeAll();
            listPanel = new GButtonListPanel(MANAGE_ITEM_HEIGHT, MANAGE_ITEM_GAP);
            itemsPanel = model.itemsPanelByName(SeriesPanel.name_id);
            add(listPanel, BorderLayout.CENTER);
            add(buildManageActionsPanel(
                    "Создать серию",
                    this::addSeriesItem,
                    "Создать группу",
                    null
            ), BorderLayout.SOUTH);
            initRootReorderList(itemsPanel, listPanel);
            refreshItems(itemsPanel, listPanel);
            model.addListener(itemsPanel, evt -> refreshItems(itemsPanel, listPanel));
        }

        private void addSeriesItem() {
            String name = "Серия " + seriesCounter++;
            model.addItem(itemsPanel, new Item(name));
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

    private void initRootReorderList(ItemsPanel itemsPanel, GButtonListPanel listPanel) {
        listPanel.addContentMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                finishRootDrop(e, itemsPanel, listPanel);
            }
        });
    }

    private void initGroupReorderList(ItemsPanel itemsPanel, GroupItem group, GButtonListPanel listPanel) {
        listPanel.addContentMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                finishGroupDrop(e, itemsPanel, group, listPanel);
            }
        });
    }

    private void refreshItems(ItemsPanel itemsPanel, GButtonListPanel listPanel) {
        listPanel.clearItems();
        for (int i = 0; i < itemsPanel.items().size(); i++) {
            Item item = itemsPanel.items().getElementAt(i);
            if (item instanceof GroupItem group) {
                listPanel.addItemComponent(buildGroupPanel(group, itemsPanel, listPanel));
            } else {
                JButton button = listPanel.addItemButton(item.name());
                attachRootReorder(button, item, itemsPanel, listPanel);
            }
        }
    }

    private void refreshGroupItems(GroupItem group, ItemsPanel itemsPanel, GButtonListPanel listPanel) {
        listPanel.clearItems();
        for (int i = 0; i < group.items().size(); i++) {
            Item item = group.items().getElementAt(i);
            JButton button = listPanel.addItemButton(item.name());
            attachGroupReorder(button, item, itemsPanel, group, listPanel);
        }
    }

    private GPanel buildGroupPanel(GroupItem group, ItemsPanel itemsPanel, GButtonListPanel rootListPanel) {
        GPanel container = new GPanel(Axis.Y_AX);
        JButton header = new JButton(groupTitle(group));
        attachGroupHeader(header, group, itemsPanel, rootListPanel);
        container.add(header);

        if (group.isExpanded()) {
            GButtonListPanel groupListPanel = new GButtonListPanel(MANAGE_ITEM_HEIGHT, MANAGE_ITEM_GAP, false);
            groupListPanel.setBorder(0, GROUP_INDENT, 0, 0);
            initGroupReorderList(itemsPanel, group, groupListPanel);
            refreshGroupItems(group, itemsPanel, groupListPanel);
            container.add(groupListPanel);
        }
        return container;
    }

    private String groupTitle(GroupItem group) {
        return (group.isExpanded() ? "[-] " : "[+] ") + group.name();
    }

    private void attachRootReorder(JButton button, Item item, ItemsPanel itemsPanel, GButtonListPanel listPanel) {
        MouseAdapter adapter = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                startReorder(item, itemsPanel, null, listPanel, button);
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                updateDropIndicator(e, listPanel);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                finishRootDrop(e, itemsPanel, listPanel);
            }
        };
        button.addMouseListener(adapter);
        button.addMouseMotionListener(adapter);
    }

    private void attachGroupReorder(JButton button, Item item, ItemsPanel itemsPanel, GroupItem group,
                                    GButtonListPanel listPanel) {
        MouseAdapter adapter = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                startReorder(item, itemsPanel, group, listPanel, button);
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                updateDropIndicator(e, listPanel);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                finishGroupDrop(e, itemsPanel, group, listPanel);
            }
        };
        button.addMouseListener(adapter);
        button.addMouseMotionListener(adapter);
    }

    private void attachGroupHeader(JButton button, GroupItem group, ItemsPanel itemsPanel, GButtonListPanel listPanel) {
        button.addActionListener(e -> model.toggleGroup(itemsPanel, group));
        MouseAdapter adapter = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                startReorder(group, itemsPanel, null, listPanel, button);
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                updateDropIndicator(e, listPanel);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (dragItem != null && dragItem != group && !(dragItem instanceof GroupItem)) {
                    dropOnGroupHeader(itemsPanel, group);
                } else {
                    finishRootDrop(e, itemsPanel, listPanel);
                }
            }
        };
        button.addMouseListener(adapter);
        button.addMouseMotionListener(adapter);
    }

    private void startReorder(Item item, ItemsPanel itemsPanel, GroupItem group, GButtonListPanel listPanel,
                              JButton button) {
        dragItem = item;
        dragItemsPanel = itemsPanel;
        dragGroup = group;
        dragListPanel = listPanel;
        dragButton = button;
        dragButtonBackground = button.getBackground();
        dragButtonOpaque = button.isOpaque();
        dragButtonContentFilled = button.isContentAreaFilled();
        dragButtonBorderPainted = button.isBorderPainted();
        button.setOpaque(true);
        button.setContentAreaFilled(true);
        button.setBorderPainted(true);
        button.setBackground(new Color(210, 230, 255));
    }

    private void finishRootDrop(MouseEvent e, ItemsPanel itemsPanel, GButtonListPanel listPanel) {
        if (dragItem == null || dragItemsPanel != itemsPanel) {
            clearReorder();
            return;
        }

        int targetIndex = listPanel.indexAtPoint(e.getPoint(), e.getComponent());
        if (dragGroup != null) {
            moveItemToRoot(itemsPanel, dragItem, targetIndex);
        } else {
            model.reorderItem(itemsPanel, dragItem, targetIndex);
        }
        clearReorder();
    }

    private void finishGroupDrop(MouseEvent e, ItemsPanel itemsPanel, GroupItem group, GButtonListPanel listPanel) {
        if (dragItem == null || dragItemsPanel != itemsPanel) {
            clearReorder();
            return;
        }
        if (dragItem instanceof GroupItem) {
            clearReorder();
            return;
        }

        int targetIndex = listPanel.indexAtPoint(e.getPoint(), e.getComponent());
        moveItemToGroup(itemsPanel, dragItem, group, targetIndex);
        clearReorder();
    }

    private void dropOnGroupHeader(ItemsPanel itemsPanel, GroupItem group) {
        if (dragItem == null || dragItemsPanel != itemsPanel) {
            clearReorder();
            return;
        }
        if (dragItem instanceof GroupItem) {
            clearReorder();
            return;
        }

        moveItemToGroup(itemsPanel, dragItem, group, group.items().getSize());
        clearReorder();
    }

    private void moveItemToGroup(ItemsPanel itemsPanel, Item item, GroupItem group, int targetIndex) {
        if (item == null || group == null) {
            return;
        }
        if (item instanceof GroupItem) {
            return;
        }
        if (dragGroup == group) {
            reorderGroupItems(group, item, targetIndex, itemsPanel);
            return;
        }

        removeItemFromContainers(itemsPanel, item);
        int index = clampIndex(targetIndex, group.items().getSize());
        group.items().add(index, item);
        model.notifyItemsChanged(itemsPanel);
    }

    private void moveItemToRoot(ItemsPanel itemsPanel, Item item, int targetIndex) {
        removeItemFromContainers(itemsPanel, item);
        int index = clampIndex(targetIndex, itemsPanel.items().getSize());
        itemsPanel.items().add(index, item);
        model.notifyItemsChanged(itemsPanel);
    }

    private void removeItemFromContainers(ItemsPanel itemsPanel, Item item) {
        if (itemsPanel.items().removeElement(item)) {
            return;
        }
        for (int i = 0; i < itemsPanel.items().size(); i++) {
            Item rootItem = itemsPanel.items().getElementAt(i);
            if (rootItem instanceof GroupItem group) {
                if (group.items().removeElement(item)) {
                    return;
                }
            }
        }
    }

    private void reorderGroupItems(GroupItem group, Item item, int targetIndex, ItemsPanel itemsPanel) {
        int fromIndex = group.items().indexOf(item);
        if (fromIndex == -1) {
            return;
        }

        int size = group.items().getSize();
        int newIndex = clampIndex(targetIndex, size);
        if (fromIndex < newIndex) {
            newIndex--;
        }
        if (newIndex == fromIndex) {
            return;
        }

        group.items().removeElementAt(fromIndex);
        if (newIndex > group.items().getSize()) {
            newIndex = group.items().getSize();
        }
        group.items().add(newIndex, item);
        model.notifyItemsChanged(itemsPanel);
    }

    private int clampIndex(int targetIndex, int size) {
        return Math.max(0, Math.min(targetIndex, size));
    }

    private void updateDropIndicator(MouseEvent e, GButtonListPanel listPanel) {
        if (dragItem == null || dragListPanel != listPanel) {
            return;
        }
        int targetIndex = listPanel.indexAtPoint(e.getPoint(), e.getComponent());
        listPanel.showDropIndicator(targetIndex);
    }

    private void clearReorder() {
        if (dragListPanel != null) {
            dragListPanel.clearDropIndicator();
        }
        if (dragButton != null) {
            dragButton.setBackground(dragButtonBackground);
            dragButton.setOpaque(dragButtonOpaque);
            dragButton.setContentAreaFilled(dragButtonContentFilled);
            dragButton.setBorderPainted(dragButtonBorderPainted);
        }
        dragItem = null;
        dragItemsPanel = null;
        dragGroup = null;
        dragListPanel = null;
        dragButton = null;
        dragButtonBackground = null;
    }
}
