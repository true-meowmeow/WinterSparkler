package swing.ui.pages.home.collections;

import swing.objects.dropper.DropPanelAbstract;

import java.util.function.Consumer;

public class DropTargetCollection extends DropPanelAbstract {


    public CollectionItemPanel collectionItem;

    @Override
    public void dropAction() {
        //if (collectionItem != null) {
            System.out.println("dropped from collection + " + collectionItem.getTitle());
        //}
    }

    @Override
    public void setCollectionItem(CollectionItemPanel collectionItem) {
        this.collectionItem = collectionItem;
    }


    /*
    @Override
    public void dropAction(String title) {
        System.out.println("dropped from collection + " + title);
    }*/
}