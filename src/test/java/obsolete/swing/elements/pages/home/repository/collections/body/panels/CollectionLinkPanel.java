package obsolete.swing.elements.pages.home.repository.collections.body.panels;

import obsolete.swing.core.basics.Axis;
import obsolete.swing.core.basics.JPanelCustom;
import obsolete.swing.core.basics.PanelType;
import obsolete.swing.elements.pages.home.repository.collections.body.objects.CollectionManager;
import obsolete.swing.elements.pages.home.repository.collections.body.objects.CollectionObject;

import java.util.*;

public class CollectionLinkPanel extends JPanelCustom {

    private static final CollectionLinkPanel INSTANCE = new CollectionLinkPanel();

    private CollectionManager collectionManager;

    private CollectionLinkPanel() {
        super(PanelType.BOX, Axis.Y_AX);

        collectionManager = new CollectionManager(this);
    }

    public static CollectionLinkPanel getInstance() {
        return INSTANCE;
    }


    public void resequence() {
        for (int i = 0; i < collectionManager.getObjects().size(); i++)
            collectionManager.getObjects().get(i).setPositionList(i);
    }

    public void refreshUI() {
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
