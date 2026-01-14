package core.ui.panels;

import core.ui.transfer.ItemFolderView;
import core.ui.transfer.ItemTransferModel;

import java.awt.*;

public class PlaylistPanel extends ItemFolderView {
    public static final String name_id = "PlaylistPanel";

    public PlaylistPanel(ItemTransferModel model) {
        super(model, model.folderByName(name_id));

        setBackground(Color.ORANGE);

    }
}
