package swing.pages.home.play;

import core.contentManager.FilesDataMap;
import core.contentManager.FolderEntities;
import swing.objects.JPanelCustom;
import swing.pages.home.series.ObservableCardLayout;

import javax.swing.*;
import java.awt.*;

public class PlaylistPanel extends JPanelCustom {

    public PlaylistPanel() {

/*        // Создаем единственный экземпляр панели папочной системы
        FolderSystemPanel folderPanel = new FolderSystemPanel();

        // Добавляем слушатель изменений, который обновляет ту же панель
        cardLayout.addPropertyChangeListener(evt -> {
            if ("filesDataList".equals(evt.getPropertyName())) {
                folderPanel.updateManagingPanel(((FilesDataMap)evt.getNewValue()));
            }
        });

        setBackground(Color.LIGHT_GRAY);

        // Создаем панель плейлиста и добавляем в cardPanel
        JPanel playlistViewPanel = playlistViewPanel();
        cardPanel.add(playlistViewPanel, PLAYLIST_VIEW);

        // Используем созданную ранее folderPanel для управления
        cardPanel.add(folderPanel, MANAGE_VIEW);

        cardLayout.show(cardPanel, PLAYLIST_VIEW);
        add(cardPanel, BorderLayout.CENTER);*/
    }

    public JPanel playlistViewPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JLabel("Обычный экран плейлиста", SwingConstants.CENTER), BorderLayout.CENTER);
        panel.setBackground(Color.ORANGE);
        return panel;
    }
}
