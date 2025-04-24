package swing.ui.pages.home.collections;

import swing.objects.dropper.DropPanel;
import swing.ui.pages.home.SwingHomeVariables;

import javax.swing.*;
import java.awt.*;


public class BottomAddCollectionPanel extends DropPanel implements SwingHomeVariables {

    public BottomAddCollectionPanel() {
        super("_EMPTY_SLOT_2", new DropTargetNewCollection());
        int height = HEIGHT_ADD_COLLECTION_PANEL;
        setPreferredSize(MAX_INT, height);
        add(new JLabel("+ Add collection"));
        //setVisible(false);
    }
}