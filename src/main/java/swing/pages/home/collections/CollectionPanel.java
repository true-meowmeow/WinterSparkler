package swing.pages.home.collections;

import swing.objects.JPanelCustom;
import swing.pages.home.play.objects.FolderPanel;
import swing.pages.home.play.objects.MediaPanel;

import javax.swing.*;
import java.awt.*;


class CollectionPanel extends JPanelCustom {

    public CollectionPanel() {
        super(PanelType.BORDER);
        setBackground(Color.LIGHT_GRAY);
        setBorder(BorderFactory.createLineBorder(Color.BLUE, 2));
        setPreferredSize(new Dimension(300, 200));
        add(new JLabel("Перетащите объекты сюда", SwingConstants.CENTER), BorderLayout.CENTER);
        setupDropTarget();
    }

    @Override
    protected void handleDrop(Object dropData) {            //todo Дропать не объект, а лист из его объектов, создать класс содержащий list<media> list<folders> для выбора папок и медиа одновременно
        System.out.println(dropData);

        if (dropData instanceof MediaPanel) {
            JLabel label = new JLabel("Dropped a MediaPanel!");
            label.setForeground(Color.GREEN);
            add(label, BorderLayout.SOUTH);
            System.out.println("MediaPanel");
        } else if (dropData instanceof FolderPanel) {
            JLabel label = new JLabel("Dropped a FolderPanel!");
            label.setForeground(Color.ORANGE);
            add(label, BorderLayout.SOUTH);
            System.out.println("FolderPanel");
        } else if (dropData instanceof String) {
            JLabel label = new JLabel("String dropped: " + dropData);
            label.setForeground(Color.RED);
            add(label, BorderLayout.SOUTH);
        } else {
            JLabel label = new JLabel("Dropped: " + dropData.toString());
            label.setForeground(Color.BLUE);
            add(label, BorderLayout.SOUTH);
        }

        revalidate();
        repaint();
    }

}
