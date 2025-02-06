package swing.pages.home.play;

import swing.objects.JPanelCustom;

import javax.swing.*;
import java.awt.*;

public class PlaylistPanel extends JPanelCustom {

    public static final String PLAYLIST_VIEW = "PLAYLIST_VIEW";
    public static final String MANAGE_VIEW = "MANAGE_VIEW";

    // Панель с карточками и CardLayout для переключения режимов
    public static final CardLayout cardLayout = new CardLayout();
    public static final JPanel cardPanel = new JPanel(cardLayout);

    public PlaylistPanel() {
        super(PanelType.BORDER);
        setBackground(Color.LIGHT_GRAY);
        //add(new JLabel(), BorderLayout.CENTER); // Пустой компонент





        // Создаём панель с карточками и инициализируем CardLayout


        JPanel playlistViewPanel = new JPanel(new BorderLayout());
        // Например, для демонстрации добавляем метку:
        playlistViewPanel.add(new JLabel("Обычный экран плейлиста", SwingConstants.CENTER), BorderLayout.CENTER);
        playlistViewPanel.setBackground(Color.ORANGE);


        // Здесь можно добавить ваши компоненты воспроизведения, список треков и т.д.

        // Создаём экран управления – панель, в которой будут новые компоненты управления плейлистом
        JPanel manageViewPanel = new JPanel(new BorderLayout());
        manageViewPanel.add(new JLabel("Экран управления плейлистом", SwingConstants.CENTER), BorderLayout.CENTER);
        manageViewPanel.setBackground(Color.BLUE);



        // Добавляем обе панели в панель с карточками
        cardPanel.add(playlistViewPanel, PLAYLIST_VIEW);
        cardPanel.add(manageViewPanel, MANAGE_VIEW);

        // Изначально показываем обычный экран плейлиста
        cardLayout.show(cardPanel, PLAYLIST_VIEW);
        add(cardPanel, BorderLayout.CENTER);








    }
}
