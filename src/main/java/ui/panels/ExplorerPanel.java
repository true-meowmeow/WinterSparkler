package ui.panels;

import ui.transfer.Item;
import ui.transfer.ItemFolderView;
import ui.transfer.ItemTransferModel;

import javax.swing.JButton;
import java.awt.*;

public class ExplorerPanel extends ItemFolderView {
    public static final String name_id = "ExplorerPanel";
    private final ItemTransferModel model;
    private int itemCounter = 1;
    private JButton addItemButton;

    public ExplorerPanel(ItemTransferModel model) {
        super(model, model.folderByName(name_id));
        this.model = model;

    }

    public void init() {
        if (addItemButton == null) {
            addItemButton = new JButton("Add item");
            addItemButton.addActionListener(e -> {
                String name = "Item " + itemCounter++;
                model.addItem(model.folderByName(name_id), new Item(name));
            });
            add(addItemButton, BorderLayout.SOUTH);
        }

        setBackground(Color.GREEN);
    }
}
