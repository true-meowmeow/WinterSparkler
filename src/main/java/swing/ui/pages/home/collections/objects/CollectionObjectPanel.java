package swing.ui.pages.home.collections.objects;

import swing.objects.core.*;

import java.util.*;

public class CollectionObjectPanel extends JPanelCustom {

    private static final CollectionObjectPanel INSTANCE = new CollectionObjectPanel();

    private CollectionManager collectionManager;

    private CollectionObjectPanel() {
        super(PanelType.BOX, Axis.Y_AX);

        collectionManager = new CollectionManager(this);
    }

    public static CollectionObjectPanel getInstance() {
        return INSTANCE;
    }


    void resequence() {
        for (int i = 0; i < collectionManager.getObjects().size(); i++)
            collectionManager.getObjects().get(i).setPositionList(i);
    }

    void refreshUI() {
        removeAll();
        collectionManager.getObjects().sort(Comparator.comparingInt(CollectionObject::getPositionList));
        collectionManager.getObjects().forEach(this::add);
        revalidate();
        repaint();
    }

    public CollectionManager getCollectionManager() {
        return collectionManager;
    }
}
