package core.swing.panels;

import core.swing.data.transferTrain.ExplorerModel;
import core.swing.data.transferTrain.FolderView2;

import java.awt.*;

public class ExplorerPanel extends FolderView2 {
    public static final String name_id = "ExplorerPanel";

    public ExplorerPanel(ExplorerModel model) {
        super(model, model.itemsPanelByName(name_id));

    }

    public void init() {

        setBackground(Color.GREEN);
    }
}