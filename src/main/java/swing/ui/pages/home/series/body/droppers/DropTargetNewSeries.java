package swing.ui.pages.home.series.body.droppers;

import core.contentManager.MediaData;
import core.contentManager.TransferableManageData;
import swing.objects.dropper.DropPanelAbstract;

import java.util.List;

public class DropTargetNewSeries extends DropPanelAbstract {

    public DropTargetNewSeries(boolean mergeGroups) {
        this.mergeGroups = mergeGroups;
    }

    private boolean mergeGroups;

    @Override
    public void dropAction(TransferableManageData transferableManageData) {
        super.dropAction(transferableManageData);
        if (transferableManageData.isEmpty()) {
            return;
        }

        if (mergeGroups) {
            transferableManageData.mergeMediaGroups();
        }

        // Placeholder for adding series logic
        for (List<MediaData> mediaGroupList : transferableManageData.getMediaGroupList()) {
            System.out.println("Add series from group size: " + mediaGroupList.size());
        }
    }
}