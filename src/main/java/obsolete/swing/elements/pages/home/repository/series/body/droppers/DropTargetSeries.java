package obsolete.swing.elements.pages.home.repository.series.body.droppers;

import obsolete.swing.core.dropper.DropPanelAbstract;
import obsolete.core.contentManager.TransferableManageData;
import obsolete.swing.elements.pages.home.playground.obsolete.play.view.selection.SelectablePanel;

import javax.swing.*;

public class DropTargetSeries extends DropPanelAbstract {
    private JLabel infoLabel;


    public void dropItems(java.util.List<SelectablePanel> items) {
        StringBuilder namesList = new StringBuilder();
        for (SelectablePanel sp : items) {
            namesList.append(sp.getDisplayText()).append(", ");
        }
        if (namesList.length() >= 2) {
            namesList.setLength(namesList.length() - 2);
        }
        String info = "Dropped: [" + namesList + "] (Count: " + items.size() + ")";
        infoLabel.setText(info);
        System.out.println(info);
    }

    @Override
    public void dropAction(TransferableManageData transferableManageData) {
        //dropItems(selectedItems);
        System.out.println("dropped from series");
    }
}