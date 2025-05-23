package swing.ui.pages.home.series;

import swing.objects.core.JPanelCustom;
import swing.objects.dropper.DropPanel;
import swing.objects.core.PanelType;
import swing.ui.pages.home.play.view.CombinedPanel;

import javax.swing.*;
import java.awt.*;

public class SeriesFrame extends JPanelCustom {

    public SeriesFrame() {
        super(true);
        add(new ControlsPanel(), BorderLayout.NORTH);
        add(new SeriesPanel(), BorderLayout.CENTER);
    }
}

 class ControlsPanel extends JPanelCustom {
    public ControlsPanel() {
        super(PanelType.GRID);
        setBorder(10, 10, 10, 10);
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
                CombinedPanel.getInstance().showCard(CombinedPanel.getInstance().MANAGE_VIEW);
            } else {
                CombinedPanel.getInstance().showCard(CombinedPanel.getInstance().PLAYLIST_VIEW);
            }
        });
    }
}
class SeriesPanel extends DropPanel {
    public static final String PROPERTY_NAME = "_SERIES_PANEL_";
    public SeriesPanel() {
        super(PROPERTY_NAME, new DropTargetSeries());
        setBackground(Color.LIGHT_GRAY);
    }

}
