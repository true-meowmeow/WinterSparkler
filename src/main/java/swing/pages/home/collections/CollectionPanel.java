package swing.pages.home.collections;

import swing.objects.JPanelCustom;
import swing.pages.home.play.objects.FolderPanel;
import swing.pages.home.play.objects.MediaPanel;

import javax.swing.*;
import java.awt.*;


class CollectionPanel extends JPanelCustom {            //todo Дропать не объект, а лист из его объектов, создать класс содержащий list<media> list<folders> для выбора папок и медиа одновременно

    public CollectionPanel() {
        super(PanelType.BORDER);
        setBackground(Color.LIGHT_GRAY);
        setBorder(BorderFactory.createLineBorder(Color.BLUE, 2));
        setPreferredSize(new Dimension(300, 200));
        add(new JLabel("Перетащите объекты сюда", SwingConstants.CENTER), BorderLayout.CENTER);
    }

}
