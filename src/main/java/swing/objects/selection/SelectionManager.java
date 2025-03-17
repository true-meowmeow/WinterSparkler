package swing.objects.selection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class SelectionManager extends JFrame {
    private static SelectionManager instance;


    public static SelectionManager SelectionInstance() {
        return instance;
    }

    private static final String[] NAMES = {
            "Ace", "Ben", "Cam", "Dan", "Eve", "Fay", "Gus", "Hal", "Ivy", "Jay",
            "Kay", "Lee", "Max", "Ned", "Oli", "Pam", "Quin", "Ray", "Sam", "Tia",
            "Uma", "Vic", "Wes", "Xen", "Yen", "Zed", "Amy", "Bob", "Cox", "Dex",
            "Eli", "Fox", "Gem", "Hex", "Ian", "Jax", "Kit", "Lux", "Moe", "Neo"
    };

    // Глобальный счётчик для порядка выделения
    public static long globalSelectionCounter = 1;

    // Список объектов Person (40 штук)
    public List<Person> persons = new ArrayList<>();
    // Список панелей в левой области
    public List<SelectablePanel> panels = new ArrayList<>();
    // Якорный индекс для диапазонного выделения (Shift)
    public int anchorIndex = -1;
    // Прямоугольник выделения при drag‑selection по фону
    public Rectangle selectionRect = null;

    // Переменные для группового перетаскивания (ghost‑эффект)
    public Point groupDragStart = null;
    public boolean draggingGroup = false;

    // Правая область – информационное окно (drop target)
    public DropTargetPanel dropTargetPanel;
    // Glass pane для ghost‑эффекта при перетаскивании
    public DragGlassPane dragGlassPane;

    public SelectionManager() {
        instance = this;
        setTitle("Windows-like Selection Demo");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 600);

        // Создаем 40 объектов Person с именем и папкой ли это
        for (int i = 0; i < 40; i++) {
            if (i < 30) {
                persons.add(new Person(NAMES[i], i, true));
            } else {
                persons.add(new Person(NAMES[i], i, false));
            }
        }

        // Инициализируем glass pane для ghost‑эффекта
        dragGlassPane = new DragGlassPane();
        setGlassPane(dragGlassPane);

        // Левая область – панель с объектами для выделения
        SelectionPanel selectionPanel = new SelectionPanel();
        JScrollPane selectionScroll = new JScrollPane(selectionPanel);

        // Правая область – информационное окно для drop'а
        dropTargetPanel = new DropTargetPanel();
        JScrollPane dropScroll = new JScrollPane(dropTargetPanel);

        // Размещаем области через разделитель
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, selectionScroll, dropScroll);
        splitPane.setDividerLocation(700);
        add(splitPane);

        // ESC – снимаем выделение
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                .put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "clearSelection");
        // Ctrl+D – тоже снимаем выделение
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                .put(KeyStroke.getKeyStroke(KeyEvent.VK_D, InputEvent.CTRL_DOWN_MASK), "clearSelection");

        getRootPane().getActionMap().put("clearSelection", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                clearSelection();
                anchorIndex = -1;
            }
        });

// Ctrl+A – выделять только объекты Person, у которых isFolder == false
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                .put(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_DOWN_MASK), "selectNotFolder");
        getRootPane().getActionMap().put("selectNotFolder", new AbstractAction() {
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
                anchorIndex = firstIndex; // Устанавливаем якорь в индекс первого подходящего объекта
            }
        });

// Ctrl+Shift+A – выделить все панели (как было раньше для Ctrl+A)
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                .put(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_DOWN_MASK | InputEvent.SHIFT_DOWN_MASK), "selectAll");
        getRootPane().getActionMap().put("selectAll", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                for (SelectablePanel sp : panels) {
                    sp.setSelected(true);
                }
                anchorIndex = 0;  // Устанавливаем якорь в позицию 0
            }
        });


        setLocationRelativeTo(null);
        setVisible(true);
    }

    // Новый метод для поиска ближайшей выбранной панели к targetIndex
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

    // Остальной код (updateAnchorAfterDeselection, clearSelection, getSelectedCount) остается без изменений

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

    /**
     * Обработка клика по панели.
     * Если зажат Shift, выбирается диапазон от ближайшего выбранного объекта до текущего.
     * При зажатых Alt+Shift – снимается выделение диапазона,
     * а при Ctrl+Shift – выделение в диапазоне инвертируется.
     * Ctrl – переключает выделение кликнутого объекта.
     * При клике без модификаторов происходит сброс выделения и выбор только текущего объекта.
     */
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new SelectionManager());
    }
}
