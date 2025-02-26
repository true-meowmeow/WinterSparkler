package swing.pages.home.play.objects;


import core.contentManager.FolderData;
import swing.objects.JPanelCustom;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class FolderPanel extends JPanelCustom {     //Можно folders & media отнаследоваться от общего класса если будет больше общих частей
    private String folderName;
    private ImageIcon icon;
    // Ссылка на основную панель (с CardLayout) для переключения между экранами
    private JPanelCustom parentPanel;
    private FolderData folderData;

    public FolderPanel(FolderData folderData, String iconPath, JPanelCustom parentPanel) {
        super(PanelType.BORDER);
        this.folderData = folderData;
        this.folderName = folderData.getName();
        this.parentPanel = parentPanel;
        this.icon = new ImageIcon(iconPath);
        initialize();
    }

    private void initialize() {
        setPreferredSize(new Dimension(100, 100));
        setBorder(BorderFactory.createLineBorder(Color.GRAY));

        // Метка, отображающая иконку и имя
        JLabel label = new JLabel(folderName, icon, JLabel.CENTER);
        label.setVerticalTextPosition(JLabel.BOTTOM);
        label.setHorizontalTextPosition(JLabel.CENTER);
        add(label, BorderLayout.CENTER);

        label.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                showCard(folderData.getLinkNextPathFull());
            }
        });

    }

    public void showCard(String path) {
        ((CardLayout) parentPanel.getLayout()).show(parentPanel, path);
    }
}