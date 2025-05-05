package swing.ui.pages.home.collections;

import swing.objects.general.Axis;
import swing.objects.general.JPanelCustom;
import swing.objects.general.PanelType;
import swing.objects.general.SmoothScrollPane;
import swing.ui.pages.home.collections.objects.*;

import java.awt.*;
import java.util.Comparator;
import java.util.TreeSet;

class CollectionsPanel extends JPanelCustom {

    public CollectionsPanel() {
        SmoothScrollPane scroller = new SmoothScrollPane(new CollectionsFieldPanel());

        add(scroller);

        add(new BottomAddCollectionPanel(), BorderLayout.SOUTH);
    }

    private static class CollectionsFieldPanel extends JPanelCustom {
        CollectionsFieldPanel() {
            add(new CollectionsListPanel(), BorderLayout.NORTH);

            EmptyDropPanel emptyPanel = new EmptyDropPanel();
            add(emptyPanel, BorderLayout.CENTER);
        }

        private static class CollectionsListPanel extends JPanelCustom {

            TreeSet<CollectionObject> collectionObjectHashSet = new TreeSet<>(Comparator.comparingInt(CollectionObject::getPositionList));

            public CollectionsListPanel() {
                super(PanelType.BOX, Axis.Y_AX);

                //Блок для теста работы коллекций
                for (int i = 1; i <= 9; i++) {
                    collectionObjectHashSet.add(new CollectionObject(i, "Коллекция " + i));
                }

                for (CollectionObject collectionObject : collectionObjectHashSet) {
                    add(collectionObject);
                }
            }
        }
    }
}
