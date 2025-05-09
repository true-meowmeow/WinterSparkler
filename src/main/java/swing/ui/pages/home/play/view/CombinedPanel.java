package swing.ui.pages.home.play.view;

import core.contentManager.FilesDataMap;
import swing.objects.general.JPanelCustom;
import swing.objects.ViewCardLayout;

import java.awt.*;

public class CombinedPanel extends JPanelCustom {

    private static CombinedPanel INSTANCE = new CombinedPanel();

    public static CombinedPanel getInstance() {
        return INSTANCE;
    }

    public static final String PLAYLIST_VIEW = "PLAYLIST_VIEW";
    public static final String MANAGE_VIEW = "MANAGE_VIEW";

    private final ViewCardLayout cardLayout = new ViewCardLayout();
    private final JPanelCustom viewCardPanel = new JPanelCustom(cardLayout);

    public CombinedPanel() {
        {           // Карточка для режима плейлиста
            JPanelCustom playlistCard = new JPanelCustom();
            playlistCard.add(new InfoPanel(), BorderLayout.NORTH);
            playlistCard.add(new PlaylistPanel(), BorderLayout.CENTER);

            viewCardPanel.add(playlistCard, PLAYLIST_VIEW);
        }

        {           // Карточка для режима управления
            JPanelCustom manageCard = new JPanelCustom();
            manageCard.add(new SelectionMenuPanel(), BorderLayout.NORTH);
            manageCard.add(ManagePanel.getInstance(), BorderLayout.CENTER);

            viewCardPanel.add(manageCard, MANAGE_VIEW);
        }

        add(viewCardPanel);
    }

    public void showCard(String view) {
        cardLayout.show(viewCardPanel, view);
    }
}
