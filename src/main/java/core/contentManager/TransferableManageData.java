package core.contentManager;

import swing.objects.objects.DataManager;
import swing.ui.pages.home.play.view.CombinedPanel;
import swing.ui.pages.home.play.view.ManagePanel;
import swing.ui.pages.home.play.view.controllers.ManageController;
import swing.ui.pages.home.play.view.selection.SelectablePanel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class TransferableManageData {

    public TransferableManageData() {
        //List<SelectablePanel> initialList = ManagePanel.getInstance().manageController.panels;    ///
        List<SelectablePanel> initialList = getSelectedPanels();    /// List of selected Objects
        List<List<MediaData>> mediaGroupList = getGroup(initialList);;



        int groupIndex = 0;
        for (List<MediaData> mediaGroup : mediaGroupList) {
            System.out.println("Группа #" + (++groupIndex) + ": size is " + mediaGroup.size());
            // перебираем элементы внутри группы
            for (MediaData media : mediaGroup) {
                System.out.println("    " + media);
                // здесь можно вызвать любые методы media, например media.getName() или media.getPath()
            }
        }
    }

    private List<List<MediaData>> getGroup(List<SelectablePanel> initialList) {
        List<List<MediaData>> mediaGroupList = new ArrayList<>();  /// Временный список хранения Медиа
        List<SelectablePanel> tempFolderList = new ArrayList<>();  /// Временный список хранения папок

        mediaGroupList.add(new ArrayList<>());
        for (SelectablePanel sb : initialList) {
            if (sb.isFolder()) {
                tempFolderList.add(sb);
            } else {
                mediaGroupList.get(0).add(sb.getMediaData());
            }
        }
        if (mediaGroupList.get(0).isEmpty()) {
            mediaGroupList.remove(0);
        }

        for (SelectablePanel sb : tempFolderList) {
            mediaGroupList.add(DataManager.getInstance().getFilesDataMap().getAllMediaData(sb.getFolderPath()));
        }


        return mediaGroupList;
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


}