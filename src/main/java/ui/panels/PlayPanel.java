package ui.panels;

import ui.transfer.ItemFolderView;
import ui.transfer.ItemTransferModel;

import java.awt.*;

public class PlayPanel extends ItemFolderView {
    public static final String name_id = "PlayPanel";

    public PlayPanel(ItemTransferModel model) {
        super(model, model.folderByName(name_id));

        setBackground(Color.PINK);
    }
}
