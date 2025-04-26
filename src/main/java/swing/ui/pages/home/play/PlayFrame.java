package swing.ui.pages.home.play;

import swing.objects.general.JPanelCustom;

import javax.swing.*;
import java.awt.*;

public class PlayFrame extends JPanelCustom {

    public PlayFrame() {
        super(true);

        // Оборачиваем InfoPanel и PlaylistPanel в один комбинированный контейнер с переключением картами
        add(CombinedPanel.getInstance(), BorderLayout.CENTER);
        add(new PlayerPanel(), BorderLayout.SOUTH);
    }
}

class PlayerPanel extends JPanelCustom {
    public PlayerPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(Box.createVerticalStrut(300));
    }
}