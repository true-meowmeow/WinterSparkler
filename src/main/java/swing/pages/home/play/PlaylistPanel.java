package swing.pages.home.play;

import core.contentManager.ContentSeeker;
import core.contentManager.FilesDataList;
import core.contentManager.FolderEntities;
import swing.objects.JPanelCustom;
import swing.pages.home.series.ObservableCardLayout;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class PlaylistPanel extends JPanelCustom {

    private final String PLAYLIST_VIEW = "PLAYLIST_VIEW";
    private final String MANAGE_VIEW = "MANAGE_VIEW";

    FolderEntities folderEntities;
    ContentSeeker contentSeeker;

    ObservableCardLayout cardLayout;
    JPanel cardPanel;

    public void setCardLayout(String view) {
        switch (view) {
            case "PLAYLIST": {
                cardLayout.show(cardPanel, PLAYLIST_VIEW);
                break;
            }
            case "MANAGE":
                cardLayout.show(cardPanel, MANAGE_VIEW);
                break;
        }
    }

    public PlaylistPanel(FolderEntities folderEntitiesSuper) {
        super(PanelType.BORDER);
        this.folderEntities = folderEntitiesSuper;

        contentSeeker = new ContentSeeker(folderEntities);
        cardLayout = new ObservableCardLayout(MANAGE_VIEW, contentSeeker);
        cardPanel = new JPanel(cardLayout);

        // Создаем единственный экземпляр панели папочной системы
        FolderSystemPanel folderPanel = new FolderSystemPanel(cardLayout.filesDataList);

        // Добавляем слушатель изменений, который обновляет ту же панель
        cardLayout.addPropertyChangeListener(evt -> {
            if ("filesDataList".equals(evt.getPropertyName())) {
                //folderPanel.updateAudioFiles((FilesDataList) evt.getNewValue());
                folderPanel.updateManagingPanel(((FilesDataList)evt.getNewValue()));
            }
        });

        setBackground(Color.LIGHT_GRAY);

        // Создаем панель плейлиста и добавляем в cardPanel
        JPanel playlistViewPanel = playlistViewPanel();
        cardPanel.add(playlistViewPanel, PLAYLIST_VIEW);

        // Используем созданную ранее folderPanel для управления
        cardPanel.add(folderPanel, MANAGE_VIEW);

        cardLayout.show(cardPanel, PLAYLIST_VIEW);
        add(cardPanel, BorderLayout.CENTER);
    }

    public JPanel playlistViewPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JLabel("Обычный экран плейлиста", SwingConstants.CENTER), BorderLayout.CENTER);
        panel.setBackground(Color.ORANGE);
        return panel;
    }
}
