package swing.ui.pages.home.play.view.selection;

import core.model.FilesDataMap;
import core.model.MediaData;
import swing.util.PathManager;
import swing.objects.core.JPanelCustom;
import swing.ui.pages.home.play.view.controllers.MovementHandler;

import javax.swing.*;
import java.awt.*;
import java.nio.file.Path;

import static swing.ui.pages.home.play.view.ManagePanel.getInstance;

public class SelectablePanel extends JPanelCustom {
    private boolean selected = false;
    private long selectionOrder = 0; // Порядок выделения

    private int index;
    private String name;
    private Path folderPath;


    public SelectablePanel(int index, FilesDataMap.CatalogData.FilesData.SubFolder folder) {    /// Folder
        this.index = index;
        this.name = folder.getName();
        this.folderPath = folder.getPath();

        isFolder = true;


        init(new Dimension(70, 70));
    }

    private MediaData mediaData;

    public SelectablePanel(int index, MediaData mediaData) {    /// Media
        this.index = index;
        this.mediaData = mediaData;
        this.name = mediaData.getName();


        init(new Dimension(110, 25));
    }

    public void action() {
        if (isFolder) {
            PathManager.getInstance().setPath(folderPath);  /// Opens a folder
        } else {    //todo action for media

        }
        if (SelectablePanel.this.isFolder()) {
            getInstance().manageController.clearSelection();
        }
        getInstance().manageController.clearAnchorIndex();
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

    @Override
    public String getName() {
        return name;
    }

    public Path getFolderPath() {
        return folderPath;
    }

    public boolean isFolder() {
        return isFolder;
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

    public MediaData getMediaData() {
        return mediaData;
    }

    private boolean isFolder;

    public long getSelectionOrder() {
        return selectionOrder;
    }

    public void setSelected(boolean selected) {
        if (!selected && this.selected && getInstance().manageController.getAnchorIndex() == this.index) {
            getInstance().manageController.updateAnchorAfterDeselection(this.index);
        }
        if (selected && !this.selected) {
            this.selectionOrder = getInstance().globalSelectionCounter++;
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