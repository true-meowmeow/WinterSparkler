package core.swing.panels;

import core.swing.data.transferTrain.ExplorerModel;
import core.swing.data.transferTrain.FolderView2;

import java.awt.*;

public class PlaylistPanel extends FolderView2 {
    public static final String name_id = "PlaylistPanel";

    public PlaylistPanel(ExplorerModel model) {
        super(model, model.itemsPanelByName(name_id));

        setBackground(Color.ORANGE);

    }
}
