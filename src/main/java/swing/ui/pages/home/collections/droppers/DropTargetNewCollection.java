package swing.ui.pages.home.collections.droppers;

import swing.objects.dropper.DropPanelAbstract;
import core.contentManager.TransferableManageData;
import swing.ui.pages.home.collections.objects.CollectionObjectManager;

public class DropTargetNewCollection extends DropPanelAbstract {
    @Override
    public void dropAction() {
        System.out.println();
        System.out.println("«Добавить новую коллекцию»");
        System.out.println();
        // TODO: вызвать отображение вашей временной панели

        CollectionObjectManager.getInstance().addCollection();

    }

    @Override
    public void dropAction(TransferableManageData transferableManageData) {
        super.dropAction(transferableManageData);

/*        for (SelectablePanel sb : selectedItems) {
            System.out.println(sb.getIsFolder());
        }*/
        //CollectionsPanel.Colle

    }
}