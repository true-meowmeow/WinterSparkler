package swing.ui.pages.home.collections;

import swing.objects.dropper.DropPanel;
import swing.objects.general.JPanelCustom;
import swing.objects.general.SmoothScrollPane;

import java.util.ArrayList;


class CollectionPanel extends DropPanel {

    public CollectionPanel() {
        super("collection", new DropTargetCollection());
        setAXIS(PanelType.BORDER, "Y");

        //CollectionsListPanel list = new CollectionsListPanel();
        //for (int i = 1; i <= 6; i++) list.addCollection("Коллекция " + i);

        CollectionsListPanel collectionsListPanel = new CollectionsListPanel();

        for (int i = 1; i <= 4; i++) {
            collectionsListPanel.addCollection("Коллекция " + i);
        }
        SmoothScrollPane scroller = new SmoothScrollPane(collectionsListPanel);
        add(scroller);



    }

    class CollectionsListPanel extends JPanelCustom {

        ArrayList<CollectionItemPanel> collectionPanelObjArrayList;
        int selected;

        CollectionsListPanel() {
            super("Y");
            collectionPanelObjArrayList = new ArrayList<>();
        }
        public void addCollection(String title) {
            add(new CollectionItemPanel(title));
        }
    }


}

