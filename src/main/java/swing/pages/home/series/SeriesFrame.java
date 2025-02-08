package swing.pages.home.series;

import swing.objects.JPanelCustom;
import swing.pages.home.play.PlaylistPanel;

import javax.swing.*;
import java.awt.*;

import static swing.pages.home.play.PlaylistPanel.*;


public class SeriesFrame extends JPanelCustom {

    public SeriesFrame(PlaylistPanel playlistPanel) {
        super(PanelType.BORDER, true);
        add(new ControlsPanel(playlistPanel), BorderLayout.NORTH);
        add(new SeriesPanel(), BorderLayout.CENTER);
    }
}

class ControlsPanel extends JPanelCustom {
    public ControlsPanel(PlaylistPanel playlistPanel) {
        super(PanelType.GRID);
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 0.5;

        JToggleButton favoritesToggle = new JToggleButton("Favorites");
        gbc.gridx = 0;
        add(favoritesToggle, gbc);

        JToggleButton manageToggle = new JToggleButton("Manage");

        manageToggle.setFocusPainted(false);
        gbc.gridx = 1;
        add(manageToggle, gbc);
        manageToggle.addActionListener(e -> {
            if (manageToggle.isSelected()) {
                playlistPanel.setCardLayout("MANAGE");
            } else {
                playlistPanel.setCardLayout("PLAYLIST");
            }
        });
    }
}
class SeriesPanel extends JPanelCustom {
    public SeriesPanel() {
        super(PanelType.GRID);
        setBackground(Color.LIGHT_GRAY);
    }

}
