package swing.ui.pages.home.collections.droppers;

import core.model.MediaData;
import swing.objects.dropper.DropPanelAbstract;
import core.service.TransferableManageData;
import swing.ui.pages.home.collections.objects.CollectionObjectPanel;

import java.util.List;

public class DropTargetNewCollection extends DropPanelAbstract {
    @Override
    public void dropAction() {


    }

    private static final boolean mergeGroups = false;

    /// Сохраняет группы

    @Override
    public void dropAction(TransferableManageData transferableManageData) {
        super.dropAction(transferableManageData);
        if (transferableManageData.isEmpty()) {   /// If the folder/s is empty
            return;
        }


        if (mergeGroups) {  /// Просто показаться, для создания групп или группы todo here переместить в серию дроп
            transferableManageData.mergeMediaGroups();
        }

        for (List<MediaData> mediaGroupList : transferableManageData.getMediaGroupList()) {
            CollectionObjectPanel.getInstance().getCollectionManager().addCollection(mediaGroupList);
        }

    }
}