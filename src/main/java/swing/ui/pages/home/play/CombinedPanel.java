package swing.ui.pages.home.play;

import core.contentManager.FilesDataMap;
import core.contentManager.FolderEntities;
import swing.objects.general.JPanelCustom;
import swing.objects.ObservableCardLayout;

import javax.swing.*;
import java.awt.*;

public class CombinedPanel extends JPanelCustom {

    public static final String PLAYLIST_VIEW = "PLAYLIST_VIEW";
    public static final String MANAGE_VIEW = "MANAGE_VIEW";

    private ObservableCardLayout cardLayout;
    private JPanel cardPanel;


    public CombinedPanel(FolderEntities folderEntities) {
        super(true);


        cardLayout = new ObservableCardLayout(MANAGE_VIEW, folderEntities);
        cardPanel = new JPanel(cardLayout);

        ManagePanel folderPanel = new ManagePanel();

        // Добавляем слушатель изменений, который обновляет ту же панель
        cardLayout.addPropertyChangeListener(evt -> {
            if ("filesDataList".equals(evt.getPropertyName())) {
                folderPanel.updateManagingPanel(((FilesDataMap)evt.getNewValue()));
            }
        });

        {           // Карточка для режима плейлиста: объединяем InfoPanel и PlaylistPanel
            JPanelCustom playlistCard = new JPanelCustom();
            playlistCard.add(new InfoPanel(), BorderLayout.NORTH);
            playlistCard.add(new PlaylistPanel(), BorderLayout.CENTER);

            cardPanel.add(playlistCard, PLAYLIST_VIEW);
        }

        {           // Карточка для режима управления: объединяем InfoPanel и дополнительную панель управления
            JPanelCustom manageCard = new JPanelCustom();
            manageCard.add(new SelectionMenuPanel(), BorderLayout.NORTH);
            manageCard.add(folderPanel, BorderLayout.CENTER);

            cardPanel.add(manageCard, MANAGE_VIEW);
        }

        setLayout(new BorderLayout());
        add(cardPanel, BorderLayout.CENTER);
    }

    /**
     * Метод для переключения отображаемой карточки.
     *
     * @param view одно из значений: PLAYLIST_VIEW или MANAGE_VIEW
     */
    public void showCard(String view) {
        cardLayout.show(cardPanel, view);
    }
}
