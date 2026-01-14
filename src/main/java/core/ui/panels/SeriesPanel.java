package core.ui.panels;

import core.ui.transfer.ItemFolderView;
import core.ui.transfer.ItemTransferModel;

import java.awt.*;

public class SeriesPanel extends ItemFolderView {
    public static final String name_id = "SeriesPanel";

    public SeriesPanel(ItemTransferModel model) {
        super(model, model.folderByName(name_id));

        setBackground(Color.LIGHT_GRAY);
    }
}
