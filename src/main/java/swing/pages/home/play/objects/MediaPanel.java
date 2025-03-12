package swing.pages.home.play.objects;

import core.contentManager.FolderData;
import core.contentManager.MediaData;
import swing.objects.JPanelCustom;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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
    }

    private void initialize() {
        setPreferredSize(new Dimension(100, 30));
        setBorder(BorderFactory.createLineBorder(Color.GRAY));

        // Метка, отображающая иконку и имя
        JLabel label = new JLabel(mediaName, icon, JLabel.CENTER);
        label.setVerticalTextPosition(JLabel.BOTTOM);
        label.setHorizontalTextPosition(JLabel.CENTER);
        add(label, BorderLayout.CENTER);

        label.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("hihi");
            }
        });

    }
}
