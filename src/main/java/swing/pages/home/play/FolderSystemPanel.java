package swing.pages.home.play;

import core.contentManager.*;
import swing.objects.JPanelCustom;
import swing.objects.selection.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.nio.file.Path;
import java.util.*;

public class FolderSystemPanel extends JPanelCustom {
    private static FolderSystemPanel instance;
    private Component glassPane; // Поле для glassPane

    public static FolderSystemPanel FolderSystemPanelInstance() {
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
    public DropTargetPanel dropTargetPanel;
    // Glass pane для ghost‑эффекта при перетаскивании
    public DragGlassPane dragGlassPane;

    public Component getGlassPane() {
        return glassPane;
    }

    @Override
    public void addNotify() {
        super.addNotify();

        Window window = SwingUtilities.getWindowAncestor(this);
        if (window instanceof JFrame) {
            JFrame frame = (JFrame) window;
            // Устанавливаем glassPane для ghost‑эффекта
            frame.setGlassPane(dragGlassPane);
        }


        // Теперь корневой элемент уже должен быть доступен
        JRootPane rootPane = SwingUtilities.getRootPane(this);
        if (rootPane != null) {
            glassPane = rootPane.getGlassPane();
            // ESC – снимаем выделение
            rootPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                    .put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "clearSelection");
            // Ctrl+D – тоже снимаем выделение
            rootPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                    .put(KeyStroke.getKeyStroke(KeyEvent.VK_D, InputEvent.CTRL_DOWN_MASK), "clearSelection");

            rootPane.getActionMap().put("clearSelection", new AbstractAction() {
                public void actionPerformed(ActionEvent e) {
                    clearSelection();
                    anchorIndex = -1;
                }
            });

            // Ctrl+A – выделять только объекты Person, у которых isFolder == false
            rootPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                    .put(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_DOWN_MASK), "selectNotFolder");
            rootPane.getActionMap().put("selectNotFolder", new AbstractAction() {
                public void actionPerformed(ActionEvent e) {
                    int firstIndex = -1;
                    for (SelectablePanel sp : panels) {
                        if (!sp.getIsFolder()) {
                            sp.setSelected(true);
                            if (firstIndex == -1) {
                                firstIndex = sp.getIndex();
                            }
                        } else {
                            sp.setSelected(false);
                        }
                    }
                    anchorIndex = firstIndex;
                }
            });

            // Ctrl+Shift+A – выделить все панели
            rootPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                    .put(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_DOWN_MASK | InputEvent.SHIFT_DOWN_MASK), "selectAll");
            rootPane.getActionMap().put("selectAll", new AbstractAction() {
                public void actionPerformed(ActionEvent e) {
                    for (SelectablePanel sp : panels) {
                        sp.setSelected(true);
                    }
                    anchorIndex = 0;
                }
            });
        }
    }

    public FolderSystemPanel() {
        super(PanelType.BORDER, "Y");
        instance = this;

        dragGlassPane = new DragGlassPane();
        add(selectionPanel);

        PathManager.getInstance().addPropertyChangeListener(evt -> {
            System.out.println("1");
            if (filesDataMap != null) {
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
