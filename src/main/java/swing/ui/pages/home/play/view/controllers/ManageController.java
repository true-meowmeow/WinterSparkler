package swing.ui.pages.home.play.view.controllers;

import swing.ui.pages.home.play.view.selection.SelectablePanel;

import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class ManageController {

    public ArrayList<SelectablePanel> panels;    // Список панелей

    public void clearSelection() {
        for (SelectablePanel p : panels) {
            p.setSelected(false);
        }
    }

    private int anchorIndex = -1;    // Якорный индекс для диапазонного выделения (Shift)

    public int getAnchorIndex() {
        return anchorIndex;
    }

    public void setAnchorIndex(int anchorIndex) {
        this.anchorIndex = anchorIndex;
    }

    public void clearAnchorIndex() {
        setAnchorIndex(-1);
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
                setAnchorIndex(index);
                if (alt) {
                    panel.setSelected(false);
                    if (getSelectedCount() == 0) {
                        clearAnchorIndex();
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
                    clearAnchorIndex();
                } else {
                    setAnchorIndex(index);
                }
            }
        } else if (ctrl) {
            boolean newSelection = !panel.isSelected();
            panel.setSelected(newSelection);
            if (newSelection) {
                setAnchorIndex(index);
            }
        } else {
            clearSelection();
            panel.setSelected(true);
            setAnchorIndex(index);
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
            clearAnchorIndex();
        } else if (candidateAbove == -1) {
            setAnchorIndex(candidateBelow);
        } else if (candidateBelow == -1) {
            setAnchorIndex(candidateAbove);
        } else {
            if (minAboveDiff < minBelowDiff) {
                setAnchorIndex(candidateAbove);
            } else if (minBelowDiff < minAboveDiff) {
                setAnchorIndex(candidateBelow);
            } else {
                setAnchorIndex(candidateAbove); // при равенстве выбираем панель с большим индексом (выше)
            }
        }
    }
}
