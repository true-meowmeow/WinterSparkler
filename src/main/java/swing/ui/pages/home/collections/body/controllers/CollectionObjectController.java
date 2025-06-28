package swing.ui.pages.home.collections.body.controllers;

import swing.ui.pages.home.collections.body.objects.CollectionObject;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class CollectionObjectController {
    private static CollectionObject openedPanel = null;

    private final CollectionObject view;
    private Point pressPoint;
    private boolean dragging = false;
    private boolean isDragHighlight = false;
    private int lastY;

    public CollectionObjectController(CollectionObject view) {
        this.view = view;
        initMouse();
    }

    private void initMouse() {
        MouseAdapter m = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                pressPoint = e.getPoint();
                dragging = false;
                isDragHighlight = false;
                lastY = e.getYOnScreen();
                if (view != openedPanel) view.setFocus();
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                if (pressPoint == null) return;
                if (!dragging && shouldStartDragging(e)) {
                    dragging = true;
                    isDragHighlight = true;
                    view.setDragHighlight();
                }
                if (!dragging) return;
                reorder(e);
                lastY = e.getYOnScreen();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (isDragHighlight) {
                    restoreBackground();
                } else {
                    openPanel();
                    //System.out.println("Открываем коллекцию: " + view.getTitle());
                }
                pressPoint = null;
                dragging = false;
                isDragHighlight = false;
            }
        };
        view.addMouseListener(m);
        view.addMouseMotionListener(m);
    }

    private boolean shouldStartDragging(MouseEvent e) {
        Container parent = view.getParent();
        if (parent == null) return false;
        Point p = SwingUtilities.convertPoint(view, e.getPoint(), parent);
        for (Component c : parent.getComponents()) {
            if (c == view || !(c instanceof CollectionObject)) continue;
            if (p.y >= c.getY() && p.y <= c.getY() + c.getHeight()) return true;
        }
        return false;
    }

    private void reorder(MouseEvent e) {
        Container parent = view.getParent();
        if (parent == null) return;
        int deltaY = e.getYOnScreen() - lastY;
        Component[] comps = parent.getComponents();
        int from = parent.getComponentZOrder(view), to = from;
        Point mouse = SwingUtilities.convertPoint(view, e.getPoint(), parent);

        for (int i = 0; i < comps.length; i++) {
            Component c = comps[i];
            if (c == view) continue;
            Rectangle b = c.getBounds();
            if (deltaY > 0 && mouse.y >= b.y && i > from) {
                to = i;
                break;
            }
            if (deltaY < 0 && mouse.y <= b.y + b.height && i < from) {
                to = i;
                break;
            }
        }
        if (to != from) {
            parent.remove(view);
            parent.add(view, to);
            parent.revalidate();
            parent.repaint();
        }
    }

    private void restoreBackground() {
        if (openedPanel == view) view.setOpened();
        else view.setNormal();
    }

    private void openPanel() {
        if (openedPanel != null && openedPanel != view) openedPanel.setNormal();
        openedPanel = view;
        view.setOpened();
    }
}
