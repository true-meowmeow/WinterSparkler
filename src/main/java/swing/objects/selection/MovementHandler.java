package swing.objects.selection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static swing.objects.selection.DropPanel.DropPanelInstance;
import static swing.pages.home.play.FolderSystemPanel.FolderSystemPanelInstance;

public class MovementHandler extends MouseAdapter {
    private final SelectablePanel panel;
    private Point pressPoint = null;
    private boolean moved = false;
    private boolean initialCtrl = false;
    private boolean initialShift = false;
    private boolean initialAlt = false; // сохраняем состояние Alt
    private boolean pendingShiftClick = false;
    private boolean dragStartedWithShift = false;

    public MovementHandler(SelectablePanel panel) {
        this.panel = panel;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        e.consume();
        // Определяем состояния модификаторов
        initialAlt = (e.getModifiersEx() & MouseEvent.ALT_DOWN_MASK) != 0;
        boolean shift = (e.getModifiersEx() & MouseEvent.SHIFT_DOWN_MASK) != 0;
        boolean alt = initialAlt;
        if (alt && !shift) {
            // Если нажата только Alt, сразу снимаем выделение и выходим
            panel.setSelected(false);
            return;
        }
        pressPoint = e.getPoint();
        moved = false;
        initialCtrl = (e.getModifiersEx() & MouseEvent.CTRL_DOWN_MASK) != 0;
        initialShift = shift;
        dragStartedWithShift = initialShift;
        pendingShiftClick = false;

        if (initialShift) {
            pendingShiftClick = true;
            if (FolderSystemPanelInstance().anchorIndex == -1) {
                FolderSystemPanelInstance().clearSelection();
                panel.setSelected(true);
                FolderSystemPanelInstance().anchorIndex = panel.getIndex();
            }
        } else if (initialCtrl) {
            FolderSystemPanelInstance().handlePanelClick(panel, e);
        } else {
            if (!panel.isSelected()) {
                FolderSystemPanelInstance().clearSelection();
                panel.setSelected(true);
                FolderSystemPanelInstance().anchorIndex = panel.getIndex();
            } else {
                FolderSystemPanelInstance().anchorIndex = panel.getIndex();
            }
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (initialAlt) {
            return;
        }
        Point current = e.getPoint();
        if (!moved && pressPoint != null) {
            double dx = current.x - pressPoint.x;
            double dy = current.y - pressPoint.y;
            if (Math.sqrt(dx * dx + dy * dy) > 5) {
                moved = true;
                if (!initialCtrl && !initialShift && !panel.isSelected()) {
                    FolderSystemPanelInstance().clearSelection();
                    panel.setSelected(true);
                    FolderSystemPanelInstance().anchorIndex = panel.getIndex();
                }
                if (initialShift && pendingShiftClick && !panel.isSelected()) {
                    FolderSystemPanelInstance().handlePanelClick(panel, e);
                    pendingShiftClick = false;
                } else if (initialShift && pendingShiftClick) {
                    pendingShiftClick = false;
                }
            }
        }
        if (moved && !FolderSystemPanelInstance().draggingGroup && panel.isSelected()) {
            FolderSystemPanelInstance().draggingGroup = true;
            FolderSystemPanelInstance().groupDragStart = SwingUtilities.convertPoint(panel, pressPoint, FolderSystemPanelInstance().getGlassPane());
            int count = 0;
            for (SelectablePanel sp : FolderSystemPanelInstance().panels) {
                if (sp.isSelected()) count++;
            }
            String ghostText = (count > 1) ? "Dragging " + count + " objects" : "Dragging object";
            FolderSystemPanelInstance().dragGlassPane.setGhostText(ghostText);
            FolderSystemPanelInstance().dragGlassPane.setGhostLocation(new Point(
                    FolderSystemPanelInstance().groupDragStart.x + 10,
                    FolderSystemPanelInstance().groupDragStart.y + 10
            ));
            FolderSystemPanelInstance().dragGlassPane.setVisible(true);
        }
        if (FolderSystemPanelInstance().draggingGroup) {
            Point glassPt = SwingUtilities.convertPoint(panel, e.getPoint(), FolderSystemPanelInstance().getGlassPane());
            FolderSystemPanelInstance().dragGlassPane.setGhostLocation(new Point(glassPt.x + 10, glassPt.y + 10));
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getClickCount() == 2) {
            panel.action();
            return;
        }

        if (FolderSystemPanelInstance().draggingGroup) {
            Point dropPoint = SwingUtilities.convertPoint(panel, e.getPoint(), FolderSystemPanelInstance().dropTargetPanel);
            Rectangle dropRect = new Rectangle(
                    0, 0,
                    DropPanelInstance().dropTargetPanel.getWidth(),
                    DropPanelInstance().dropTargetPanel.getHeight()
            );
            if (dropRect.contains(dropPoint)) {
                List<SelectablePanel> selectedItems = new ArrayList<>();
                for (SelectablePanel sp : FolderSystemPanelInstance().panels) {
                    if (sp.isSelected()) {
                        selectedItems.add(sp);
                    }
                }
                Collections.sort(selectedItems, Comparator.comparingLong(SelectablePanel::getSelectionOrder));
                DropPanelInstance().dropTargetPanel.dropItems(selectedItems);
                FolderSystemPanelInstance().clearSelection();
                FolderSystemPanelInstance().anchorIndex = -1;
            }
            FolderSystemPanelInstance().draggingGroup = false;
            FolderSystemPanelInstance().groupDragStart = null;
            FolderSystemPanelInstance().dragGlassPane.clearGhost();
        } else {
            if (!moved) {
                if (initialShift && pendingShiftClick) {
                    FolderSystemPanelInstance().handlePanelClick(panel, e);
                    pendingShiftClick = false;
                    return;
                } else if (!initialCtrl && !initialShift && !initialAlt) {
                    if (panel.isSelected()) {
                        FolderSystemPanelInstance().clearSelection();
                        panel.setSelected(true);
                        FolderSystemPanelInstance().anchorIndex = panel.getIndex();
                    }
                }
            }
        }
    }
}
