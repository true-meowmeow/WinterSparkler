package swing.ui.pages.home.play.view;

import swing.objects.core.JPanelCustom;

import javax.swing.*;
import java.awt.*;

public class PlaylistPanel extends JPanelCustom {

    public PlaylistPanel() {
        add(playlistViewPanel());
    }

    public JPanel playlistViewPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JLabel("Обычный экран плейлиста", SwingConstants.CENTER), BorderLayout.CENTER);
        panel.setBackground(Color.ORANGE);
        return panel;
    }
}
