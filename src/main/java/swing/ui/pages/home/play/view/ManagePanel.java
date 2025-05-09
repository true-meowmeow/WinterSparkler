package swing.ui.pages.home.play.view;

import core.contentManager.*;
import swing.objects.PathManager;
import swing.objects.general.Axis;
import swing.objects.general.JPanelCustom;
import swing.ui.pages.home.play.view.controllers.DragGlassPane;
import swing.objects.selection.*;
import swing.ui.pages.home.play.view.controllers.ManageController;

import java.awt.*;
import java.nio.file.Path;
import java.util.*;

public class ManagePanel extends JPanelCustom {
    private static ManagePanel INSTANCE = new ManagePanel();

    public ManageController manageController = new ManageController(this);

    // Glass pane для ghost‑эффекта при перетаскивании
    public DragGlassPane dragGlassPane;

    public static ManagePanel getInstance() {
        return INSTANCE;
    }


    // Глобальный счётчик для порядка выделения
    public static long globalSelectionCounter = 1;

    // Переменные для группового перетаскивания (ghost‑эффект)
    public Point groupDragStart = null;
    public boolean draggingGroup = false;


    public Component glassPane; // Поле для glassPane

    public void setGlassPane(Component glassPane) {
        this.glassPane = glassPane;
    }

    @Override
    public void addNotify() {
        super.addNotify();
        new KeyBindingConfigurator(this);
    }

    public void updateManagePanel() {
        if (filesDataMap != null) {
            dragGlassPane.clearGhost();
            if (PathManager.getInstance().getPath().equals(Path.of("Home"))) {
                selectionPanel.updateSetHome(filesDataMap);
            } else {
                selectionPanel.updateSet(filesDataMap.getFilesDataByFullPath(PathManager.getInstance().getPath()));
            }
        }
    }

    public ManagePanel() {
        super(Axis.Y_AX);

        dragGlassPane = new DragGlassPane();
        add(selectionPanel);

    }

    public FilesDataMap filesDataMap;
    public SelectionPanel selectionPanel = new SelectionPanel();

    public void updateManagingPanel(FilesDataMap filesDataMapObj) {
        this.filesDataMap = filesDataMapObj;
        manageController.panels = new ArrayList<>();

        PathManager.getInstance().setCorePaths(new HashSet<>(filesDataMapObj.getCatalogDataHashMap().keySet()));
        PathManager.getInstance().setPathHome();
        if (PathManager.getInstance().isPathHome()) {
            selectionPanel.updateSetHome(filesDataMap);
        }
    }
}
