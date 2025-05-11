package swing.ui.pages.home.collections.objects;

import swing.objects.core.*;
import swing.objects.objects.OrderCounter;

import java.util.*;

public class CollectionObjectManager extends JPanelCustom {

    private static final CollectionObjectManager INSTANCE = new CollectionObjectManager();
    private final List<CollectionObject> objects = new ArrayList<>();
    private final Random rnd = new Random();
    private static OrderCounter collectionOrder;

    private CollectionObjectManager() {
        super(PanelType.BOX, Axis.Y_AX);

        collectionOrder = new OrderCounter();
    }

    public static CollectionObjectManager getInstance() { return INSTANCE; }


    String nameGenerator;
    public void addCollection() {
        int collectionOrderNumber = collectionOrder.getNextNumber();
        nameGenerator = "fdsfdsfsd";

        CollectionObject collectionObject = new CollectionObject(getPropertyName(collectionOrderNumber), collectionOrderNumber, objects.size(), nameGenerator);
        objects.add(collectionObject);

        refreshUI();
    }


    private String getPropertyName(int collectionOrder) {
        return "_COLLECTION_OBJECT_" + collectionOrder + "_";
    }

    public void removeObject(int positionList) {
        if (positionList < 0 || positionList >= objects.size()) return;
        objects.remove(positionList);
        resequence();
        refreshUI();
    }

    public void renameObject(int positionList, String newTitle) {
        if (positionList < 0 || positionList >= objects.size()) return;
        objects.get(positionList).setTitle(newTitle);
        refreshUI();
    }

    public void addFiveRandom() {
        for (int i = 0; i < 5; i++)
            addCollection();
    }

    private void resequence() {
        for (int i = 0; i < objects.size(); i++)
            objects.get(i).setPositionList(i);
    }

    private void refreshUI() {
        removeAll();
        objects.sort(Comparator.comparingInt(CollectionObject::getPositionList));
        objects.forEach(this::add);
        revalidate();
        repaint();
    }
}
