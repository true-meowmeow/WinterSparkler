package swing.ui.pages.home.collections.droppers;

import core.contentManager.MediaData;
import swing.objects.dropper.DropPanelAbstract;
import core.contentManager.TransferableManageData;
import swing.ui.pages.home.collections.objects.CollectionObjectPanel;

import java.util.List;

public class DropTargetNewCollection extends DropPanelAbstract {
    @Override
    public void dropAction() {


    }

    private static final boolean mergeGroups = false;      /// Сохраняет группы

    @Override
    public void dropAction(TransferableManageData transferableManageData) {
        super.dropAction(transferableManageData);
        if (transferableManageData.isEmpty()) return;

        if (mergeGroups) {
            transferableManageData.mergeMediaGroups();
        }

        for (List<MediaData> mediaGroupList : transferableManageData.getMediaGroupList()) {
            CollectionObjectPanel.getInstance().getCollectionManager().addCollection(mediaGroupList);
        }

    }
}