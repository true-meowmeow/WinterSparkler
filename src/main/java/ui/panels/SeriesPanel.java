package ui.panels;

import ui.transfer.ItemFolderView;
import ui.transfer.ItemTransferModel;

import java.awt.*;

public class SeriesPanel extends ItemFolderView {
    public static final String name_id = "SeriesPanel";

    public SeriesPanel(ItemTransferModel model) {
        super(model, model.folderByName(name_id));

        setBackground(Color.LIGHT_GRAY);
    }
}
