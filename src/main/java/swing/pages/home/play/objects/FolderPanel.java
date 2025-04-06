package swing.pages.home.play.objects;

import core.contentManager.FilesDataMap;
import core.contentManager.FolderData;
import swing.objects.JPanelCustom;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.nio.file.Path;
import java.util.HashSet;

public class FolderPanel extends JPanelCustom {
    private String folderName;
    private ImageIcon icon;
    private JPanelCustom parentPanel;
    private FilesDataMap.CatalogData.FilesData.SubFolder subFolder;
    private boolean isSelected = false; // Флаг выделения

    private static final Color DEFAULT_COLOR = Color.WHITE;
    private static final Color SELECTED_COLOR = new Color(173, 216, 230); // Светло-голубой

    public FolderPanel(FilesDataMap.CatalogData.FilesData.SubFolder subFolder, JPanelCustom parentPanel) {
        super(PanelType.BORDER);
        this.subFolder = subFolder;
        this.folderName = subFolder.getName();
        this.parentPanel = parentPanel;
        this.icon = new ImageIcon(defaultIconPath);
        initialize();
    }

    private void initialize() {
        setPreferredSize(new Dimension(100, 100));
        setBorder(BorderFactory.createLineBorder(Color.GRAY));
        setBackground(DEFAULT_COLOR);
        setLayout(new BorderLayout());

        // Метка, отображающая иконку и имя
        JLabel label = new JLabel(folderName.toString(), icon, JLabel.CENTER);
        label.setVerticalTextPosition(JLabel.BOTTOM);
        label.setHorizontalTextPosition(JLabel.CENTER);
        add(label, BorderLayout.CENTER);

        label.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.isControlDown()) {
                } else {
                    // При обычном клике открываем папку
                    showCard(subFolder.getPath().toString());
                }
            }
        });
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
        toggleSelection();
    }

    public boolean isSelected() {
        return isSelected;
    }

    private void toggleSelection() {
        setBackground(isSelected ? SELECTED_COLOR : DEFAULT_COLOR);
        repaint();
    }

    @Override
    public String toString() {
        return folderName.toString();
    }

    public void showCard(String path) {
        ((CardLayout) parentPanel.getLayout()).show(parentPanel, path);
    }
}
