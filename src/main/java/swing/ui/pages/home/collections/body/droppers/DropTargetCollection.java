package swing.ui.pages.home.collections.body.droppers;

import swing.objects.dropper.DropPanelAbstract;
import swing.ui.pages.home.collections.body.objects.CollectionObject;

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