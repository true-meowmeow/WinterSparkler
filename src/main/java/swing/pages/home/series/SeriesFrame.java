package swing.pages.home.series;

import swing.objects.JPanelCustom;

import javax.swing.*;
import java.awt.*;

import static swing.pages.home.play.PlaylistPanel.*;


public class SeriesFrame extends JPanelCustom {
    public SeriesFrame() {
        super(PanelType.BORDER, true);
        add(new ControlsPanel(), BorderLayout.NORTH);
        add(new SeriesPanel(), BorderLayout.CENTER);
    }
}

class ControlsPanel extends JPanelCustom {
    public ControlsPanel() {
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
                // Переключаемся на режим управления:
                cardLayout.show(cardPanel, MANAGE_VIEW);
                // Можно дополнительно деактивировать компоненты обычного экрана,
                // если они должны быть неактивны (но в нашем случае они просто скрыты)
            } else {
                // Возвращаемся к обычному экрану плейлиста:
                cardLayout.show(cardPanel, PLAYLIST_VIEW);
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
