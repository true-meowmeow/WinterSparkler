package swing.ui.pages.home.series;

import swing.objects.general.JPanelCustom;
import swing.objects.dropper.DropPanel;
import swing.ui.pages.home.play.CombinedPanel;

import javax.swing.*;
import java.awt.*;

public class SeriesFrame extends JPanelCustom {

    public SeriesFrame(CombinedPanel combinedPanel) {
        super(true);
        add(new ControlsPanel(combinedPanel), BorderLayout.NORTH);
        add(new SeriesPanel(), BorderLayout.CENTER);
    }
}

 class ControlsPanel extends JPanelCustom {
    public ControlsPanel(CombinedPanel combinedPanel) {
        super(PanelType.GRID);
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 0.5;

        JToggleButton favoritesToggle = new JToggleButton("Favorites");
        favoritesToggle.setFocusable(false);
        favoritesToggle.setFocusPainted(false);
        gbc.gridx = 0;
        add(favoritesToggle, gbc);

        JToggleButton manageToggle = new JToggleButton("Manage");
        manageToggle.setFocusable(false);
        manageToggle.setFocusPainted(false);
        gbc.gridx = 1;
        add(manageToggle, gbc);

        // При переключении кнопки меняем карточку в CombinedPanel
        manageToggle.addActionListener(e -> {
            if (manageToggle.isSelected()) {
                combinedPanel.showCard(CombinedPanel.MANAGE_VIEW);
            } else {
                combinedPanel.showCard(CombinedPanel.PLAYLIST_VIEW);
            }
        });
    }
}
class SeriesPanel extends DropPanel {
    public SeriesPanel() {
        super("series", new DropTargetSeries());
        setBackground(Color.LIGHT_GRAY);
    }

}
