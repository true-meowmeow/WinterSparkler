package swing.pages.home.play;

import core.contentManager.ContentSeeker;
import core.contentManager.FolderEntities;
import swing.objects.JPanelCustom;
import swing.pages.home.series.ObservableCardLayout;

import javax.swing.*;
import java.awt.*;

public class PlaylistPanel extends JPanelCustom {

    private final String PLAYLIST_VIEW = "PLAYLIST_VIEW";
    private final String MANAGE_VIEW = "MANAGE_VIEW";

    FolderEntities folderEntities;
    ContentSeeker contentSeeker;

    CardLayout cardLayout;
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
        ContentSeeker contentSeeker = new ContentSeeker(folderEntities);
        cardLayout = new ObservableCardLayout(MANAGE_VIEW, contentSeeker);
        cardPanel = new JPanel(cardLayout);

        setBackground(Color.LIGHT_GRAY);

        //Playlist
        JPanel playlistViewPanel = new JPanel(new BorderLayout());
        playlistViewPanel.add(new JLabel("Обычный экран плейлиста", SwingConstants.CENTER), BorderLayout.CENTER);
        playlistViewPanel.setBackground(Color.ORANGE);
        cardPanel.add(playlistViewPanel, PLAYLIST_VIEW);

        //Manage
        JPanel manageViewPanel = new JPanel(new BorderLayout());
        manageViewPanel.add(new JLabel("Экран управления плейлистом", SwingConstants.CENTER), BorderLayout.CENTER);
        manageViewPanel.setBackground(Color.WHITE);
        cardPanel.add(manageViewPanel, MANAGE_VIEW);

        JLabel displayCountLabel = new JLabel();
        manageViewPanel.add(displayCountLabel, BorderLayout.SOUTH); // например, снизу

        cardLayout.show(cardPanel, PLAYLIST_VIEW);
        add(cardPanel, BorderLayout.CENTER);


    }
}
