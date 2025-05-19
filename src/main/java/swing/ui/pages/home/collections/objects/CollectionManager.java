package swing.ui.pages.home.collections.objects;

import core.contentManager.CollectionNameGenerator;
import core.contentManager.MediaData;

import java.util.ArrayList;
import java.util.List;

public class CollectionManager {
    private final List<CollectionObject> objects = new ArrayList<>();

    private final CollectionObjectPanel collectionObjectPanel;

    public CollectionManager(CollectionObjectPanel collectionObjectPanel) {
        this.collectionObjectPanel = collectionObjectPanel;
    }

    OrderCounter counter;
    CollectionNameGenerator collectionNameGenerator;
    public void addCollection(List<MediaData> mediaGroupList) {
        counter = new OrderCounter();
        //collectionNameGenerator = new CollectionNameGenerator();

        int collectionOrderNumber = counter.getNextNumber();

        CollectionObject collectionObject = new CollectionObject(getPropertyName(collectionOrderNumber), collectionOrderNumber, objects.size(), "dsadfsadfdsafdsfd");
        objects.add(collectionObject);

        collectionObjectPanel.refreshUI();
    }


    private String getPropertyName(int collectionOrder) {
        return "_COLLECTION_OBJECT_" + collectionOrder + "_";
    }

    public void removeObject(int positionList) {
        if (positionList < 0 || positionList >= objects.size()) return;
        objects.remove(positionList);
        collectionObjectPanel.resequence();
        collectionObjectPanel.refreshUI();
    }

    public void renameObject(int positionList, String newTitle) {
        if (positionList < 0 || positionList >= objects.size()) return;
        objects.get(positionList).setTitle(newTitle);
        collectionObjectPanel.refreshUI();
    }

    public List<CollectionObject> getObjects() {
        return objects;
    }

    private class OrderCounter {
        private int current;

        public OrderCounter() {
            this(1);
        }

        public OrderCounter(int start) {
            current = start - 1;
        }

        public int getNextNumber() {
            return ++current;
        }
    }
}