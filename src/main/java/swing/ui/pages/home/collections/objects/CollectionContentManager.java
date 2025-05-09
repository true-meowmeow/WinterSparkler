package swing.ui.pages.home.collections.objects;

import swing.objects.core.Axis;
import swing.objects.core.JPanelCustom;
import swing.objects.core.PanelType;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Collections;
import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

public class CollectionContentManager extends JPanelCustom {
    public static final String PROPERTY_NAME = "_COLLECTION_CONTENT_";

    private static TreeSet<CollectionObject> collectionObjectHashSet = new TreeSet<>(Comparator.comparingInt(CollectionObject::getPositionList));
    PropertyChangeSupport propertyChangeSupport =
            new PropertyChangeSupport(CollectionContentManager.class);

    public CollectionContentManager() {
        super(PanelType.BOX, Axis.Y_AX);



/*
        test();
        for (CollectionObject collectionObject : collectionObjectHashSet) {
            add(collectionObject);
        }*/

    }

    public void add(CollectionObject object) {
        if (collectionObjectHashSet.add(object)) {
            propertyChangeSupport.firePropertyChange(PROPERTY_NAME, null, object);
        }
    }

    public void remove(CollectionObject object) {
        if (collectionObjectHashSet.remove(object)) {
            propertyChangeSupport.firePropertyChange(PROPERTY_NAME, object, null);
        }
    }

    public Set<CollectionObject> getAll() {
        return Collections.unmodifiableSet(collectionObjectHashSet);
    }

    public void addPropertyChangeListener(PropertyChangeListener propertyChangeListener) {
        //propertyChangeSupport.addPropertyChangeListener(PROPERTY_NAME, propertyChangeListener);
    }

    private void test() {
        //Блок для теста работы коллекций
        for (int i = 1; i <= 9; i++) {
            collectionObjectHashSet.add(new CollectionObject(i, "Коллекция " + i));
        }

    }
}
