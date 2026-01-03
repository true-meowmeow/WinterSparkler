package core.swing.panels;

import core.swing.data.transferTrain.ExplorerModel;
import core.swing.data.transferTrain.FolderView2;

import java.awt.*;

public class QueuePanel extends FolderView2 {
    public static final String name_id = "QueuePanel";

    public QueuePanel(ExplorerModel model) {
        super(model, model.itemsPanelByName(name_id));

        setBackground(Color.CYAN);
    }
}
