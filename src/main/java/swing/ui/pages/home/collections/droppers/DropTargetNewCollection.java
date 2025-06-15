package swing.ui.pages.home.collections.droppers;

import core.contentManager.MediaData;
import core.contentManager.TransferableManageData;
import swing.objects.dropper.DropPanelAbstract;
import swing.ui.pages.home.collections.objects.CollectionObjectPanel;

import java.util.List;

public class DropTargetNewCollection extends DropPanelAbstract {

    public DropTargetNewCollection(boolean mergeGroups) {
        this.mergeGroups = mergeGroups;
    }

    @Override
    public void dropAction() {
    }

    private boolean mergeGroups;

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
