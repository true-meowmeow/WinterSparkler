package obsolete.swing.elements.pages.home.repository.series.body.droppers;

import obsolete.core.contentManager.MediaData;
import obsolete.core.contentManager.TransferableManageData;
import obsolete.swing.core.dropper.DropPanelAbstract;

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