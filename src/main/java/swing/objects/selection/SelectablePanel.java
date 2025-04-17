package swing.objects.selection;

import core.contentManager.FilesDataMap;
import core.contentManager.MediaData;
import swing.objects.PathManager;
import swing.objects.general.JPanelCustom;
import swing.objects.movement.MovementHandler;

import javax.swing.*;
import java.awt.*;
import java.nio.file.Path;

import static swing.ui.pages.home.play.FolderSystemPanel.FolderSystemPanelInstance;

public class SelectablePanel extends JPanelCustom {
    private boolean selected = false;
    private long selectionOrder = 0; // Порядок выделения

    private int index;
    private String name;
    private Path folderPath;


    public SelectablePanel(int index, FilesDataMap.CatalogData.FilesData.SubFolder folder) {    //Folder
        this.index = index;
        this.name = folder.getName();
        this.folderPath = folder.getPath();

        isFolder = true;


        init(new Dimension(70, 70));
    }

    public SelectablePanel(int index, MediaData mediaData) {    ///Media
        this.index = index;
        this.name = mediaData.getName();


        init(new Dimension(110, 25));
    }

    public void action() {
        if (isFolder) {
            PathManager.getInstance().setPath(folderPath);
        } else {    //todo action for media

        }
        if (SelectablePanel.this.getIsFolder()) {
            FolderSystemPanelInstance().clearSelection();
        }
        FolderSystemPanelInstance().anchorIndex = -1;
    }


    public void init(Dimension sizes) { //todo Проверить обновляет ли он эти элементы потом
        setBackground(Color.LIGHT_GRAY);
        setBorder(BorderFactory.createLineBorder(Color.BLACK));

        setPreferredSize(sizes);
        setSize(sizes);

        JLabel nameLabel = new JLabel(name, SwingConstants.CENTER);
        add(nameLabel);

        // Используем новый класс обработчика для управления перемещениями
        MovementHandler movementHandler = new MovementHandler(this);
        addMouseListener(movementHandler);
        addMouseMotionListener(movementHandler);
    }


    public int getIndex() {
        return index;
    }

    public boolean isSelected() {
        return selected;
    }

    public String getDisplayText() {        //Только для отладки
        return name;
    }


    private boolean isFolder;

    public boolean getIsFolder() {
        return isFolder;
    }

    public long getSelectionOrder() {
        return selectionOrder;
    }

    public void setSelected(boolean selected) {
        if (!selected && this.selected && FolderSystemPanelInstance().anchorIndex == this.index) {
            FolderSystemPanelInstance().updateAnchorAfterDeselection(this.index);
        }
        if (selected && !this.selected) {
            this.selectionOrder = FolderSystemPanelInstance().globalSelectionCounter++;
        }
        if (!selected) {
            this.selectionOrder = 0;
        }
        this.selected = selected;
        if (selected) {
            setBorder(BorderFactory.createLineBorder(Color.BLUE, 3));
        } else {
            setBorder(BorderFactory.createLineBorder(Color.BLACK));
        }
        repaint();
    }
}