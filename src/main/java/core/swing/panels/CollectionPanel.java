package core.swing.panels;

import core.swing.data.transferTrain.ExplorerModel;
import core.swing.data.transferTrain.FolderView2;

import java.awt.*;

public class CollectionPanel extends FolderView2 {
    public static final String name_id = "CollectionPanel";

    public CollectionPanel(ExplorerModel model) {
        super(model, model.itemsPanelByName(name_id));

        setBackground(Color.RED);
    }
}