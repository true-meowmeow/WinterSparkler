package core.swing.panels;

import core.swing.data.transferTrain.ExplorerModel;
import core.swing.data.transferTrain.FolderView2;

import java.awt.*;

public class SeriesPanel extends FolderView2 {
    public static final String name_id = "SeriesPanel";

    public SeriesPanel(ExplorerModel model) {
        super(model, model.itemsPanelByName(name_id));

        setBackground(Color.LIGHT_GRAY);
    }
}