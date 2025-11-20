package obsolete.swing.elements.pages.home.repository.collections.body.droppers;

import obsolete.swing.core.dropper.DropPanelAbstract;
import obsolete.swing.elements.pages.home.repository.collections.body.objects.CollectionObject;

public class DropTargetCollection extends DropPanelAbstract {


    public CollectionObject collectionItem;

    @Override
    public void dropAction() {  //todo Не реализовано
        //if (collectionItem != null) {
            System.out.println("dropped from collection + " + collectionItem.getTitle());
        //}
    }

    @Override
    public void setCollectionItem(CollectionObject collectionItem) {
        this.collectionItem = collectionItem;
    }
}