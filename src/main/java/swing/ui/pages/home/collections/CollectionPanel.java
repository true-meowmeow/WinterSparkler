package swing.ui.pages.home.collections;

import swing.objects.general.JPanelCustom;
import swing.objects.selection.DropPanel;

import java.awt.*;


class CollectionPanel extends JPanelCustom {

    public CollectionPanel() {
        setBackground(Color.LIGHT_GRAY);
        //setBorder(BorderFactory.createLineBorder(Color.BLUE, 2));
        setPreferredSize(new Dimension(300, 200));

        DropPanel collectionDropPanel = new DropPanel("collection", new DropTargetCollection());
        add(collectionDropPanel);
    }
}
