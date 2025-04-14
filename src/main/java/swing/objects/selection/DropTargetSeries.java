package swing.objects.selection;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class DropTargetSeries extends DropPanelChild {
    private JLabel infoLabel;

    public DropTargetSeries() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        infoLabel = new JLabel("Drop items here", SwingConstants.CENTER);
        add(infoLabel, BorderLayout.CENTER);
    }

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
    public void action(List<SelectablePanel> selectedItems) {
        super.action(selectedItems);
        dropItems(selectedItems);
        System.out.println("2222222222");
    }
}