package swing.objects.selection;

import java.awt.*;

public class FolderPanel extends SelectablePanel {

    public FolderPanel(int index, String name) {
        super(index, name, new Dimension(70, 70));  //70 70
        setFolderActive();
    }
}
