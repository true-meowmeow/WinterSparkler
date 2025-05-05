package swing.ui.pages.home.collections.droppers;

import swing.objects.dropper.DropPanelAbstract;
import swing.ui.pages.home.collections.objects.CollectionObject;

public class DropTargetCollection extends DropPanelAbstract {


    public CollectionObject collectionItem;

    @Override
    public void dropAction() {
        //if (collectionItem != null) {
            System.out.println("dropped from collection + " + collectionItem.getTitle());
        //}
    }

    @Override
    public void setCollectionItem(CollectionObject collectionItem) {
        this.collectionItem = collectionItem;
    }
}