package swing.elements.pages.home.repository.collections.body.objects;

import core.contentManager.CollectionNameGenerator;
import core.contentManager.MediaData;
import swing.core.objects.OrderCounter;
import swing.elements.pages.home.repository.collections.body.panels.CollectionLinkPanel;

import java.util.ArrayList;
import java.util.List;

public class CollectionManager {
    private final List<CollectionObject> objects = new ArrayList<>();

    private final CollectionLinkPanel collectionLinkPanel;

    public CollectionManager(CollectionLinkPanel collectionLinkPanel) {
        this.collectionLinkPanel = collectionLinkPanel;
    }

    private final OrderCounter counter = new OrderCounter();

    public void addCollection(List<MediaData> mediaGroupList) {


        CollectionNameGenerator collectionNameGenerator = new CollectionNameGenerator(mediaGroupList);

        int collectionOrderNumber = counter.getNextNumber();

        CollectionObject collectionObject = new CollectionObject(getPropertyName(collectionOrderNumber), collectionOrderNumber, objects.size(), collectionNameGenerator.getCollectionName());
        objects.add(collectionObject);

        collectionLinkPanel.refreshUI();
    }


    private String getPropertyName(int collectionOrder) {
        return "_COLLECTION_OBJECT_" + collectionOrder + "_";
    }

    public void removeObject(int positionList) {
        if (positionList < 0 || positionList >= objects.size()) return;
        objects.remove(positionList);
        collectionLinkPanel.resequence();
        collectionLinkPanel.refreshUI();
    }

    public void renameObject(int positionList, String newTitle) {
        if (positionList < 0 || positionList >= objects.size()) return;
        objects.get(positionList).setTitle(newTitle);
        collectionLinkPanel.refreshUI();
    }

    public List<CollectionObject> getObjects() {
        return objects;
    }
}