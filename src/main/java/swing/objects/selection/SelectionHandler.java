package swing.objects.selection;

import swing.ui.pages.home.play.ManagePanel;
import swing.ui.pages.home.play.SelectablePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class SelectionHandler extends MouseAdapter {
    private final JViewport viewport;
    private Rectangle selectionRect = null;
    private Point dragStart = null;
    private boolean dragging = false;
    private boolean ctrlDownAtStart = false;
    private boolean shiftDownAtStart = false;
    private boolean altDownAtStart = false;

    public SelectionHandler(JViewport viewport) {
        this.viewport = viewport;
    }

    public Rectangle getSelectionRect() {
        return selectionRect;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        dragStart = e.getPoint();
        dragging = true;
        ctrlDownAtStart = (e.getModifiersEx() & MouseEvent.CTRL_DOWN_MASK) != 0;
        shiftDownAtStart = (e.getModifiersEx() & MouseEvent.SHIFT_DOWN_MASK) != 0;
        altDownAtStart = (e.getModifiersEx() & MouseEvent.ALT_DOWN_MASK) != 0;
        if (!ctrlDownAtStart && !shiftDownAtStart && !altDownAtStart) {
            ManagePanel.FolderSystemPanelInstance().clearSelection();
        }
        selectionRect = new Rectangle(dragStart);
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (dragging && selectionRect != null) {
            Point current = e.getPoint();
            selectionRect.setBounds(
                    Math.min(dragStart.x, current.x),
                    Math.min(dragStart.y, current.y),
                    Math.abs(dragStart.x - current.x),
                    Math.abs(dragStart.y - current.y)
            );
            viewport.repaint();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (dragging && selectionRect != null) {
            dragging = false;
            // Обработка выделения для каждой панели
            for (SelectablePanel sp : ManagePanel.FolderSystemPanelInstance().panels) {
                Rectangle compBounds = SwingUtilities.convertRectangle(
                        sp.getParent(), sp.getBounds(), viewport);
                if (selectionRect.intersects(compBounds)) {
                    if (shiftDownAtStart) {
                        sp.setSelected(!altDownAtStart);
                    } else {
                        if (altDownAtStart) {
                            sp.setSelected(false);
                        } else if (ctrlDownAtStart) {
                            sp.setSelected(!sp.isSelected());
                        } else {
                            sp.setSelected(true);
                        }
                    }
                }
            }
            // Устанавливаем якорный индекс для диапазонного выделения
            int minIndex = Integer.MAX_VALUE;
            for (SelectablePanel sp : ManagePanel.FolderSystemPanelInstance().panels) {
                if (sp.isSelected() && sp.getIndex() < minIndex) {
                    minIndex = sp.getIndex();
                }
            }
            if (minIndex != Integer.MAX_VALUE) {
                ManagePanel.FolderSystemPanelInstance().anchorIndex = minIndex;
            }
            // Сбрасываем выделение
            selectionRect = null;
            viewport.repaint();
        }
    }
}
