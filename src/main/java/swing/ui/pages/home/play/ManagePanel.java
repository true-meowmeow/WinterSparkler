package swing.ui.pages.home.play;

import core.contentManager.*;
import swing.objects.PathManager;
import swing.objects.general.Axis;
import swing.objects.general.JPanelCustom;
import swing.objects.movement.DragGlassPane;
import swing.objects.selection.*;
import swing.ui.pages.home.collections.DropTargetCollection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.nio.file.Path;
import java.util.*;

public class ManagePanel extends JPanelCustom {
    private static ManagePanel instance;
    private Component glassPane; // Поле для glassPane

    public static ManagePanel FolderSystemPanelInstance() {
        return instance;
    }

    // Глобальный счётчик для порядка выделения
    public static long globalSelectionCounter = 1;

    // Список панелей
    public ArrayList<SelectablePanel> panels;
    // Якорный индекс для диапазонного выделения (Shift)
    public int anchorIndex = -1;

    // Переменные для группового перетаскивания (ghost‑эффект)
    public Point groupDragStart = null;
    public boolean draggingGroup = false;

    // Правая область – информационное окно (drop target)
    public DropTargetCollection dropTargetCollection;
    // Glass pane для ghost‑эффекта при перетаскивании
    public DragGlassPane dragGlassPane;

    public Component getGlassPane() {
        return glassPane;
    }

    @Override
    public void addNotify() {
        super.addNotify();

        // установим glassPane для ghost-эффекта
        Window window = SwingUtilities.getWindowAncestor(this);
        if (window instanceof JFrame) {
            ((JFrame) window).setGlassPane(dragGlassPane);
        }

        JRootPane root = SwingUtilities.getRootPane(this);
        if (root == null) return;
        glassPane = root.getGlassPane();

        InputMap im = root.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap am = root.getActionMap();

        // Home / Back навигация
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE,      0), "goHome");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_HOME,        0), "goHome");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_BACK_SPACE,  0), "goBack");
        am.put("goHome", new AbstractAction() {
            @Override public void actionPerformed(ActionEvent e) {
                PathManager.getInstance().goToHome();
            }
        });
        am.put("goBack", new AbstractAction() {
            @Override public void actionPerformed(ActionEvent e) {
                PathManager.getInstance().goToParentDirectory();
            }
        });

        // очистка выделения
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_D, InputEvent.CTRL_DOWN_MASK), "clearSel");
        am.put("clearSel", new AbstractAction() {
            @Override public void actionPerformed(ActionEvent e) {
                clearSelection();
                anchorIndex = -1;
            }
        });

        // Ctrl+A — выбрать только файлы
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_DOWN_MASK), "selectNotFolder");
        am.put("selectNotFolder", new AbstractAction() {
            @Override public void actionPerformed(ActionEvent e) {
                int first = -1;
                for (SelectablePanel sp : panels) {
                    if (!sp.getIsFolder()) {
                        sp.setSelected(true);
                        if (first == -1) first = sp.getIndex();
                    } else {
                        sp.setSelected(false);
                    }
                }
                anchorIndex = first;
            }
        });

        // Ctrl+Shift+A — выбрать всё
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_A,
                InputEvent.CTRL_DOWN_MASK | InputEvent.SHIFT_DOWN_MASK), "selectAll");
        am.put("selectAll", new AbstractAction() {
            @Override public void actionPerformed(ActionEvent e) {
                for (SelectablePanel sp : panels) sp.setSelected(true);
                anchorIndex = 0;
            }
        });
    }


    public ManagePanel() {
        super(Axis.Y_AX);
        instance = this;

        dragGlassPane = new DragGlassPane();
        add(selectionPanel);

        PathManager.getInstance().addPropertyChangeListener(evt -> {
            if (filesDataMap != null) {
                dragGlassPane.clearGhost();
                if (PathManager.getInstance().getPath().equals(Path.of("Home"))) {
                    selectionPanel.updateSetHome(filesDataMap);
                } else {
                    selectionPanel.updateSet(filesDataMap.getFilesDataByFullPath(PathManager.getInstance().getPath()));
                }
            }
        });
    }

    private FilesDataMap filesDataMap;
    SelectionPanel selectionPanel = new SelectionPanel();

    public void updateManagingPanel(FilesDataMap filesDataMapObj) {
        this.filesDataMap = filesDataMapObj;
        panels = new ArrayList<>();

        PathManager.getInstance().setCorePaths(new HashSet<>(filesDataMapObj.getCatalogDataHashMap().keySet()));
        PathManager.getInstance().setPathHome();
        if (PathManager.getInstance().isPathHome()) {
            selectionPanel.updateSetHome(filesDataMap);
        }
    }

    public void clearSelection() {
        for (SelectablePanel p : panels) {
            p.setSelected(false);
        }
    }

    private int getSelectedCount() {
        int count = 0;
        for (SelectablePanel sp : panels) {
            if (sp.isSelected()) {
                count++;
            }
        }
        return count;
    }

    public void handlePanelClick(SelectablePanel panel, MouseEvent e) {
        boolean ctrl = (e.getModifiersEx() & MouseEvent.CTRL_DOWN_MASK) != 0;
        boolean shift = (e.getModifiersEx() & MouseEvent.SHIFT_DOWN_MASK) != 0;
        boolean alt = (e.getModifiersEx() & MouseEvent.ALT_DOWN_MASK) != 0;
        int index = panel.getIndex();

        if (shift) {
            if (anchorIndex == -1) {
                anchorIndex = index;
                if (alt) {
                    panel.setSelected(false);
                    if (getSelectedCount() == 0) {
                        anchorIndex = -1;
                    }
                } else if (ctrl) {
                    panel.setSelected(!panel.isSelected());
                } else {
                    panel.setSelected(true);
                }
            } else {
                int bestAnchor;
                // Если панель уже выбрана – используем якорь, иначе ищем ближайшую выбранную панель
                if (panel.isSelected()) {
                    bestAnchor = anchorIndex;
                } else {
                    bestAnchor = findNearestSelected(index);
                }
                // Для Ctrl+Shift не переопределяем bestAnchor, а используем найденное значение

                int start = Math.min(bestAnchor, index);
                int end = Math.max(bestAnchor, index);
                for (int i = start; i <= end; i++) {
                    if (alt) {
                        panels.get(i).setSelected(false);
                    } else if (ctrl) {
                        panels.get(i).setSelected(!panels.get(i).isSelected());
                    } else {
                        panels.get(i).setSelected(true);
                    }
                }
                if (alt && getSelectedCount() == 0) {
                    anchorIndex = -1;
                } else {
                    anchorIndex = index;
                }
            }
        } else if (ctrl) {
            boolean newSelection = !panel.isSelected();
            panel.setSelected(newSelection);
            if (newSelection) {
                anchorIndex = index;
            }
        } else {
            clearSelection();
            panel.setSelected(true);
            anchorIndex = index;
        }
    }

    private int findNearestSelected(int targetIndex) {
        int nearest = -1;
        int minDiff = Integer.MAX_VALUE;
        for (SelectablePanel sp : panels) {
            if (sp.isSelected()) {
                int diff = Math.abs(sp.getIndex() - targetIndex);
                if (diff < minDiff) {
                    minDiff = diff;
                    nearest = sp.getIndex();
                }
            }
        }
        return (nearest == -1 ? targetIndex : nearest);
    }

    public void updateAnchorAfterDeselection(int deselectedIndex) {
        int candidateAbove = -1;
        int candidateBelow = -1;
        int minAboveDiff = Integer.MAX_VALUE;
        int minBelowDiff = Integer.MAX_VALUE;
        for (SelectablePanel sp : panels) {
            if (sp.isSelected()) {
                int idx = sp.getIndex();
                if (idx > deselectedIndex) {
                    int diff = idx - deselectedIndex;
                    if (diff < minAboveDiff) {
                        minAboveDiff = diff;
                        candidateAbove = idx;
                    }
                } else if (idx < deselectedIndex) {
                    int diff = deselectedIndex - idx;
                    if (diff < minBelowDiff) {
                        minBelowDiff = diff;
                        candidateBelow = idx;
                    }
                }
            }
        }
        if (candidateAbove == -1 && candidateBelow == -1) {
            anchorIndex = -1;
        } else if (candidateAbove == -1) {
            anchorIndex = candidateBelow;
        } else if (candidateBelow == -1) {
            anchorIndex = candidateAbove;
        } else {
            if (minAboveDiff < minBelowDiff) {
                anchorIndex = candidateAbove;
            } else if (minBelowDiff < minAboveDiff) {
                anchorIndex = candidateBelow;
            } else {
                anchorIndex = candidateAbove; // при равенстве выбираем панель с большим индексом (выше)
            }
        }
    }
}
