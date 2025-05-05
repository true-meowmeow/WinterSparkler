package swing.ui.pages.home.play;

import core.contentManager.FilesDataMap;
import core.contentManager.FolderEntities;
import swing.objects.general.JPanelCustom;
import swing.objects.ObservableCardLayout;

import java.awt.*;

public class CombinedPanel extends JPanelCustom {

    private static CombinedPanel INSTANCE = new CombinedPanel();

    public static CombinedPanel getInstance() {
        return INSTANCE;
    }

    public final String PLAYLIST_VIEW = "PLAYLIST_VIEW";
    public final String MANAGE_VIEW = "MANAGE_VIEW";

    private ObservableCardLayout cardLayout;
    private JPanelCustom  cardPanel;

    public CombinedPanel() {
        super(true);
        //new ManagePanel();

        cardLayout = new ObservableCardLayout(MANAGE_VIEW, FolderEntities.getInstance());
        cardPanel = new JPanelCustom(cardLayout);



        // Добавляем слушатель изменений, который обновляет ту же панель
        cardLayout.addPropertyChangeListener(evt -> {
            if ("filesDataList".equals(evt.getPropertyName())) {
                ManagePanel.getInstance().updateManagingPanel(((FilesDataMap) evt.getNewValue()));
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
            manageCard.add(ManagePanel.getInstance(), BorderLayout.CENTER);

            cardPanel.add(manageCard, MANAGE_VIEW);
        }

        setLayout(new BorderLayout());
        add(cardPanel, BorderLayout.CENTER);
    }

    public void showCard(String view) {
        cardLayout.show(cardPanel, view);
    }
}
