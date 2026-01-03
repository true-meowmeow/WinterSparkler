package core.swing.panels;

import core.swing.data.transferTrain.ExplorerModel;
import core.swing.data.transferTrain.FolderView2;

import java.awt.*;

public class CoverPanel extends FolderView2 {
    public static final String name_id = "CoverPanel";

    public CoverPanel(ExplorerModel model) {
        super(model, model.itemsPanelByName(name_id));

        setBackground(Color.YELLOW);
    }
}