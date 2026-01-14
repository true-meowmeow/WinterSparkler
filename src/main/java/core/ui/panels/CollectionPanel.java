package core.ui.panels;

import core.ui.transfer.ItemFolderView;
import core.ui.transfer.ItemTransferModel;

import java.awt.*;

public class CollectionPanel extends ItemFolderView {
    public static final String name_id = "CollectionPanel";

    public CollectionPanel(ItemTransferModel model) {
        super(model, model.folderByName(name_id));

        setBackground(Color.RED);
    }
}
