package swing.objects.objects;

import swing.ui.pages.home.play.view.selection.SelectablePanel;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.util.ArrayList;
import java.util.List;

public class TransferableData {

    List<SelectablePanel> selectablePanelList;

    public TransferableData(ArrayList<SelectablePanel> selectablePanelList) {
        this.selectablePanelList = selectablePanelList;


        for (SelectablePanel selectablePanel : selectablePanelList) {
            System.out.println(selectablePanel.getName());
            System.out.println(selectablePanel.getFolderPath());
        }
    }
}