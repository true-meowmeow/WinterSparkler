package swing.pages.home.play;

import core.contentManager.ContentSeeker;
import core.contentManager.FolderEntities;
import swing.objects.JPanelCustom;
import swing.pages.home.series.ObservableCardLayout;

import javax.swing.*;
import java.awt.*;

public class PlaylistPanel extends JPanelCustom {

    public static final String PLAYLIST_VIEW = "PLAYLIST_VIEW";
    public static final String MANAGE_VIEW = "MANAGE_VIEW";

    // Панель с карточками и CardLayout для переключения режимов
    public static final CardLayout cardLayout = new ObservableCardLayout();
    public static final JPanel cardPanel = new JPanel(cardLayout);



    public PlaylistPanel(FolderEntities folderEntities) {
        super(PanelType.BORDER);
        ContentSeeker contentSeeker = new ContentSeeker(folderEntities);

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

        folderEntities.listModel.addListDataListener(new javax.swing.event.ListDataListener() {
            @Override
            public void intervalAdded(javax.swing.event.ListDataEvent e) {
                System.out.println("\nadded ->");
                updateFolders();
            }

            @Override
            public void intervalRemoved(javax.swing.event.ListDataEvent e) {
                System.out.println("\nremoved ->");
                updateFolders();
            }

            @Override
            public void contentsChanged(javax.swing.event.ListDataEvent e) {
                System.out.println("\nchanged ->");
                updateFolders();
            }
            public void updateFolders() {
                displayCountLabel.setText(contentSeeker.seek());
            }
        });

        cardLayout.show(cardPanel, PLAYLIST_VIEW);
        add(cardPanel, BorderLayout.CENTER);


    }
}
