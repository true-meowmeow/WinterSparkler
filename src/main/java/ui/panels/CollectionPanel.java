package ui.panels;

import ui.transfer.ItemFolderView;
import ui.transfer.ItemTransferModel;

import java.awt.*;

public class CollectionPanel extends ItemFolderView {
    public static final String name_id = "CollectionPanel";

    public CollectionPanel(ItemTransferModel model) {
        super(model, model.folderByName(name_id));

        setBackground(Color.RED);
    }
}
