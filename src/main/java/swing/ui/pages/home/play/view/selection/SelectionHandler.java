package swing.ui.pages.home.play.view.selection;

import swing.ui.pages.home.play.view.ManagePanel;
import swing.ui.pages.home.play.view.SelectablePanel;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class SelectionHandler extends MouseAdapter implements ChangeListener {

    private final JViewport viewport;
    private Rectangle selectionRect;          // coords: view
    private Point dragStartView;              // coords: view
    private Point lastMouseViewport;          // coords: viewport
    private boolean dragging;
    private boolean ctrlStart, shiftStart, altStart;

    public SelectionHandler(JViewport viewport) {
        this.viewport = viewport;
        viewport.addChangeListener(this);     // реагируем на прокрутку
    }

    /* ---------- API ---------- */
    public Rectangle getSelectionRect() {
        if (selectionRect == null) return null;
        Rectangle r = new Rectangle(selectionRect);
        Point vp = viewport.getViewPosition();
        r.translate(-vp.x, -vp.y);
        return r;
    }

    /* ---------- Mouse ---------- */
    @Override public void mousePressed(MouseEvent e) {
        dragStartView = SwingUtilities.convertPoint(
                viewport, e.getPoint(), viewport.getView());
        lastMouseViewport = e.getPoint();

        ctrlStart  = (e.getModifiersEx() & MouseEvent.CTRL_DOWN_MASK)  != 0;
        shiftStart = (e.getModifiersEx() & MouseEvent.SHIFT_DOWN_MASK) != 0;
        altStart   = (e.getModifiersEx() & MouseEvent.ALT_DOWN_MASK)   != 0;

        if (!ctrlStart && !shiftStart && !altStart) {
            ManagePanel.getInstance().manageController.clearSelection();
        }

        selectionRect = new Rectangle(dragStartView);
        dragging = true;
        viewport.repaint();
    }

    @Override public void mouseDragged(MouseEvent e) {
        if (!dragging) return;
        lastMouseViewport = e.getPoint();
        updateSelectionRect();
    }

    @Override public void mouseReleased(MouseEvent e) {
        if (!dragging) return;
        dragging = false;

        Rectangle vpRect = getSelectionRect();
        for (SelectablePanel sp : ManagePanel.getInstance().manageController.panels) {
            Rectangle comp = SwingUtilities.convertRectangle(
                    sp.getParent(), sp.getBounds(), viewport);

            if (vpRect.intersects(comp)) {
                if (shiftStart)              sp.setSelected(!altStart);
                else if (altStart)           sp.setSelected(false);
                else if (ctrlStart)          sp.setSelected(!sp.isSelected());
                else                         sp.setSelected(true);
            }
        }

        int min = Integer.MAX_VALUE;
        for (SelectablePanel sp : ManagePanel.getInstance().manageController.panels) {
            if (sp.isSelected() && sp.getIndex() < min) min = sp.getIndex();
        }
        if (min != Integer.MAX_VALUE) {
            ManagePanel.getInstance().manageController.setAnchorIndex(min);
        }

        selectionRect = null;
        viewport.repaint();
    }

    /* ---------- Scroll listener ---------- */
    @Override public void stateChanged(ChangeEvent e) {
        if (dragging) updateSelectionRect();
    }

    /* ---------- Helpers ---------- */
    private void updateSelectionRect() {
        if (selectionRect == null) return;

        Point curView = SwingUtilities.convertPoint(
                viewport, lastMouseViewport, viewport.getView());

        selectionRect.setBounds(
                Math.min(dragStartView.x, curView.x),
                Math.min(dragStartView.y, curView.y),
                Math.abs(dragStartView.x - curView.x),
                Math.abs(dragStartView.y - curView.y));

        viewport.repaint();
    }
}
