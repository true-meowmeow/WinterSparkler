package swing.ui.pages.home.collections;

import swing.objects.general.JPanelCustom;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CollectionItemPanel extends JPanelCustom {

    private static final int HEIGHT = 80;

    private static final Color BG_NORMAL         = new Color(0xF5F5F5);
    private static final Color BG_OPENED         = new Color(0xDCDCDC); // открытая коллекция
    private static final Color BG_DRAG_HIGHLIGHT = new Color(0xDCDCDC); // нажатая / перетаскиваемая
    private static final Color BG_FOCUS          = new Color(0xE0E0E0); // фон во время перемещения

    private static CollectionItemPanel openedPanel = null;

    private Point pressPoint;
    private boolean dragging            = false;
    private boolean isDraggingHighlight = false;
    private int lastY;
    private final String title;

    public CollectionItemPanel(String title) {
        this.title = title;
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(0, HEIGHT));
        setBackground(BG_NORMAL);
        add(new JLabel(title, SwingConstants.CENTER), BorderLayout.CENTER);
        initMouse();
    }

    /* ==================== MOUSE ==================== */

    private void initMouse() {
        MouseAdapter m = new MouseAdapter() {
            @Override public void mousePressed(MouseEvent e) {
                pressPoint          = e.getPoint();
                dragging            = false;
                isDraggingHighlight = false;
                lastY               = e.getYOnScreen();  // ← обращаемся к полю класса

                if (getBackground() != BG_OPENED) {
                    setBackground(BG_FOCUS);
                }
            }

            @Override public void mouseDragged(MouseEvent e) {
                if (pressPoint == null) return;

                if (!dragging && shouldStartDragging(e)) {
                    dragging            = true;
                    isDraggingHighlight = true;
                    //setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));           :/
                    setBackground(BG_DRAG_HIGHLIGHT);
                }
                if (!dragging) return;

                reorderInsideParent(e);
                lastY = e.getYOnScreen();  // ← тоже
            }

            @Override public void mouseReleased(MouseEvent e) {
                setCursor(Cursor.getDefaultCursor());

                if (isDraggingHighlight) {
                    restoreBackgroundAfterDrag();
                } else {
                    openThisPanel();
                    System.out.println("Открываем коллекцию: " + title);
                }

                pressPoint          = null;
                dragging            = false;
                isDraggingHighlight = false;
            }
        };

        addMouseListener(m);
        addMouseMotionListener(m);
    }

    /* ==================== DRAG‑HELPERS ==================== */

    /** старт перетаскивания: курсор входит в зону соседней панели */
    private boolean shouldStartDragging(MouseEvent e) {
        Container parent = getParent();
        if (parent == null) return false;

        Point p = SwingUtilities.convertPoint(this, e.getPoint(), parent);
        for (Component c : parent.getComponents()) {
            if (c == this || !(c instanceof CollectionItemPanel)) continue;
            Rectangle r = c.getBounds();
            if (p.y >= r.y && p.y <= r.y + r.height) return true;
        }
        return false;
    }

    private void reorderInsideParent(MouseEvent e) {
        Container parent = getParent();
        if (parent == null) return;

        int currentY = e.getYOnScreen();
        int deltaY   = currentY - lastY;

        Component[] comps = parent.getComponents();
        int fromIndex      = parent.getComponentZOrder(this);
        int toIndex        = fromIndex;
        Point mousePoint   = SwingUtilities.convertPoint(this, e.getPoint(), parent);

        for (int i = 0; i < comps.length; i++) {
            Component comp = comps[i];
            if (comp == this || !(comp instanceof CollectionItemPanel)) continue;
            Rectangle bounds = comp.getBounds();
            if (deltaY > 0 && mousePoint.y >= bounds.y && i > fromIndex) {
                toIndex = i;
                break;
            }
            if (deltaY < 0 && mousePoint.y <= bounds.y + bounds.height && i < fromIndex) {
                toIndex = i;
                break;
            }
        }

        if (toIndex != fromIndex) {
            parent.remove(this);
            parent.add(this, toIndex);
            parent.revalidate();
            parent.repaint();
        }
    }

    private void restoreBackgroundAfterDrag() {
        if (openedPanel == this) {
            setBackground(BG_OPENED);
        } else {
            setBackground(BG_NORMAL);
        }
    }

    /* ==================== OPEN / SELECT ==================== */

    private void openThisPanel() {
        if (openedPanel != null && openedPanel != this) {
            openedPanel.setBackground(BG_NORMAL);
        }
        openedPanel = this;
        setBackground(BG_OPENED);
    }

    /* ==================== LAYOUT CONSTRAINTS ==================== */

    @Override public Dimension getMaximumSize()   { return new Dimension(Integer.MAX_VALUE, HEIGHT); }
    @Override public Dimension getPreferredSize() { return new Dimension(0, HEIGHT); }
}
