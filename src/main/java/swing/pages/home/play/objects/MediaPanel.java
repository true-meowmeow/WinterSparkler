package swing.pages.home.play.objects;

import core.contentManager.MediaData;
import swing.objects.JPanelCustom;

import javax.swing.*;
import java.awt.*;

public class MediaPanel extends JPanelCustom {
    private String mediaName;
    private ImageIcon icon;
    private JPanelCustom parentPanel;
    private MediaData mediaData;

    public MediaPanel(MediaData mediaData, String iconPath, JPanelCustom parentPanel) {
        super(PanelType.BORDER);
        this.mediaData = mediaData;
        this.mediaName = mediaData.getNameFull();
        this.parentPanel = parentPanel;
        this.icon = new ImageIcon(iconPath);
        initialize();
        setupDragAndDrop();
    }

    private void initialize() {
        setPreferredSize(new Dimension(100, 30));
        setBorder(BorderFactory.createLineBorder(Color.GRAY));
        // Создаём метку с иконкой и именем
        JLabel label = new JLabel(mediaName, icon, JLabel.CENTER);
        label.setVerticalTextPosition(JLabel.BOTTOM);
        label.setHorizontalTextPosition(JLabel.CENTER);
        add(label, BorderLayout.CENTER);
    }

    // Переопределяем метод для передачи полного объекта для DnD
    @Override
    protected Object getDragData() {
        return this;
    }

    @Override
    public String toString() {
        // Для отображения в окне перетаскивания возвращаем имя медиа
        return mediaName;
    }
}
