package ui.transfer;

import ui.components.BasePanel;

import javax.swing.*;
import java.awt.*;

public class ItemFolderView extends BasePanel {

    private final ItemTransferModel model;
    private final ItemFolder folder;

    private final JList<Item> list;

    public ItemFolderView(ItemTransferModel model, ItemFolder folder) {
        this.model = model;
        this.folder = folder;

        this.list = new JList<>(folder.items());

        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setDragEnabled(true);
        list.setDropMode(DropMode.ON);
        list.setTransferHandler(new ItemTransferHandler(model, folder, list));

        // Показываем, что объект НЕ копируется (id один и тот же после перемещения)
        list.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                          boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Item it) {
                    String id = Integer.toHexString(System.identityHashCode(it));
                    setText(it.name() + " (id=" + id + ")");
                }
                return this;
            }
        });

        add(new JScrollPane(list), BorderLayout.CENTER);
    }
}
