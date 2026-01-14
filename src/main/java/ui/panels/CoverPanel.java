package ui.panels;

import ui.transfer.ItemFolderView;
import ui.transfer.ItemTransferModel;

import java.awt.*;

public class CoverPanel extends ItemFolderView {
    public static final String name_id = "CoverPanel";

    public CoverPanel(ItemTransferModel model) {
        super(model, model.folderByName(name_id));

        setBackground(Color.YELLOW);
    }
}
