package swing.pages.home.play.objects;

import core.contentManager.FolderData;
import swing.objects.JPanelCustom;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class FolderPanel extends JPanelCustom {
    private String folderName;
    private ImageIcon icon;
    private JPanelCustom parentPanel;
    private FolderData folderData;
    private boolean isSelected = false; // Флаг выделения

    private static final Color DEFAULT_COLOR = Color.WHITE;
    private static final Color SELECTED_COLOR = new Color(173, 216, 230); // Светло-голубой

    public FolderPanel(FolderData folderData, String iconPath, JPanelCustom parentPanel) {
        super(PanelType.BORDER);
        this.folderData = folderData;
        this.folderName = folderData.getName();
        this.parentPanel = parentPanel;
        this.icon = new ImageIcon(iconPath);
        initialize();
        setupDragAndDrop();
    }

    private void initialize() {
        setPreferredSize(new Dimension(100, 100));
        setBorder(BorderFactory.createLineBorder(Color.GRAY));
        setBackground(DEFAULT_COLOR);
        setLayout(new BorderLayout());

        // Метка, отображающая иконку и имя
        JLabel label = new JLabel(folderName, icon, JLabel.CENTER);
        label.setVerticalTextPosition(JLabel.BOTTOM);
        label.setHorizontalTextPosition(JLabel.CENTER);
        add(label, BorderLayout.CENTER);

        label.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.isControlDown()) {
                    SelectionManager.toggleFolderSelection(FolderPanel.this, true);
                } else {
                    // При обычном клике открываем папку
                    showCard(folderData.getLinkNextPathFull());
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
    protected Object getDragData() {
        return this;
    }

    @Override
    public String toString() {
        return folderName;
    }

    public void showCard(String path) {
        ((CardLayout) parentPanel.getLayout()).show(parentPanel, path);
    }
}
