package core.swing.panels;

import core.swing.data.transferTrain.ExplorerModel;
import core.swing.data.transferTrain.FolderView2;
import core.swing.data.transferTrain.Item;

import javax.swing.JButton;
import java.awt.*;

public class ExplorerPanel extends FolderView2 {
    public static final String name_id = "ExplorerPanel";
    private final ExplorerModel model;
    private int itemCounter = 1;
    private JButton addItemButton;

    public ExplorerPanel(ExplorerModel model) {
        super(model, model.itemsPanelByName(name_id));
        this.model = model;

    }

    public void init() {
        if (addItemButton == null) {
            addItemButton = new JButton("Add item");
            addItemButton.addActionListener(e -> {
                String name = "Item " + itemCounter++;
                model.addItem(model.itemsPanelByName(name_id), new Item(name));
            });
            add(addItemButton, BorderLayout.SOUTH);
        }

        setBackground(Color.GREEN);
    }
}
