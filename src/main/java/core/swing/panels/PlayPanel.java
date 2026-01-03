package core.swing.panels;

import core.swing.data.transferTrain.ExplorerModel;
import core.swing.data.transferTrain.FolderView2;

import java.awt.*;

public class PlayPanel extends FolderView2 {
    public static final String name_id = "PlayPanel";

    public PlayPanel(ExplorerModel model) {
        super(model, model.itemsPanelByName(name_id));

        setBackground(Color.PINK);
    }
}