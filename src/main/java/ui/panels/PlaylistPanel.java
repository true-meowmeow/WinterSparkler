package ui.panels;

import ui.transfer.ItemFolderView;
import ui.transfer.ItemTransferModel;

import java.awt.*;

public class PlaylistPanel extends ItemFolderView {
    public static final String name_id = "PlaylistPanel";

    public PlaylistPanel(ItemTransferModel model) {
        super(model, model.folderByName(name_id));

        setBackground(Color.ORANGE);

    }
}
