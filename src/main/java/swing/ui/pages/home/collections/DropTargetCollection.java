package swing.ui.pages.home.collections;

import swing.objects.dropper.DropPanelAbstract;
import swing.ui.pages.home.play.SelectablePanel;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class DropTargetCollection extends DropPanelAbstract {
    private JLabel infoLabel;

    public void dropItems(List<SelectablePanel> items) {
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
    public void dropAction() {
        System.out.println("dropped from collection");
    }
}