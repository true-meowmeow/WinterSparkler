package core.contentManager;

import swing.objects.objects.DataManager;
import swing.ui.pages.home.play.view.ManagePanel;
import swing.ui.pages.home.play.view.selection.SelectablePanel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class TransferableManageData {
    private List<List<MediaData>> mediaGroupList;
    private boolean empty = true;

    public TransferableManageData() {
        List<SelectablePanel> initialList = getSelectedPanels();
        setMediaGroupList(getGroup(initialList));
        print();
    }

    /**
     * Объединяет все группы в одну, сохраняя порядок
     */
    public void mergeMediaGroups() {
        if (mediaGroupList == null || mediaGroupList.isEmpty()) return;
        List<MediaData> merged = new ArrayList<>();
        for (List<MediaData> group : mediaGroupList) {
            merged.addAll(group);
        }
        mediaGroupList.clear();
        mediaGroupList.add(merged);
    }

    /**
     * @return true, если ни один mediaData не был найден
     */
    public boolean isEmpty() {
        return empty;
    }

    private void print() {
        int groupIndex = 0;
        for (List<MediaData> mediaGroup : mediaGroupList) {
            System.out.println("Группа #" + (++groupIndex) + ": size is " + mediaGroup.size());
            for (MediaData media : mediaGroup) {
                System.out.println("    " + media);
            }
        }
    }

    private List<List<MediaData>> getGroup(List<SelectablePanel> initialList) {
        List<List<MediaData>> groups = new ArrayList<>();
        List<MediaData> nonFolderGroup = new ArrayList<>();
        List<SelectablePanel> folderPanels = new ArrayList<>();

        // Собираем сначала все файлы вне папок
        for (SelectablePanel sb : initialList) {
            if (sb.isFolder()) {
                folderPanels.add(sb);
            } else {
                nonFolderGroup.add(sb.getMediaData());
                empty = false;
            }
        }
        if (!nonFolderGroup.isEmpty()) {
            groups.add(nonFolderGroup);
        }

        // Теперь папки
        for (SelectablePanel sb : folderPanels) {
            List<MediaData> group = DataManager
                    .getInstance()
                    .getFilesDataMap()
                    .getAllMediaData(sb.getFolderPath());
            if (!group.isEmpty()) {
                groups.add(group);
                empty = false;
            }
        }

        return groups;
    }

    private List<SelectablePanel> getSelectedPanels() {
        List<SelectablePanel> selectedItems = new ArrayList<>();
        for (SelectablePanel p : ManagePanel.getInstance().manageController.panels) {
            if (p.isSelected()) {
                selectedItems.add(p);
            }
        }
        Collections.sort(selectedItems, Comparator.comparingLong(SelectablePanel::getSelectionOrder));
        return selectedItems;
    }

    public void setMediaGroupList(List<List<MediaData>> mediaGroupList) {
        this.mediaGroupList = mediaGroupList;
    }

    public List<List<MediaData>> getMediaGroupList() {
        return mediaGroupList;
    }
}
