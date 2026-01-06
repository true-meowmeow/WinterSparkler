package core.objects;

import core.main.check.Axis;
import core.main.check.PanelType;

import javax.swing.JComponent;
import javax.swing.SwingUtilities;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LayoutManager;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.InputEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GMarqueePanel extends GScrollablePanel {
    public interface MarqueeHandler {
        void marqueeStarted(Rectangle rect, boolean additive);
        void marqueeUpdated(Rectangle rect, boolean additive);
        void marqueeFinished(Rectangle rect, boolean additive);
    }

    private static final Color DEFAULT_FILL = new Color(60, 120, 220, 60);
    private static final Color DEFAULT_BORDER = new Color(60, 120, 220);

    private final MouseAdapter marqueeAdapter = new MouseAdapter() {
        @Override
        public void mousePressed(MouseEvent e) {
            if (!SwingUtilities.isLeftMouseButton(e)) {
                return;
            }
            marqueeStart = toLocalPoint(e);
            marqueeRect = null;
            marqueeActive = false;
            marqueeAdditive = (e.getModifiersEx()
                    & (InputEvent.CTRL_DOWN_MASK | InputEvent.SHIFT_DOWN_MASK)) != 0;
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            if (marqueeStart == null) {
                return;
            }
            Point current = toLocalPoint(e);
            if (!marqueeActive) {
                if (!isDragBeyondThreshold(marqueeStart, current)) {
                    return;
                }
                marqueeActive = true;
                updateMarqueeRect(current);
                if (handler != null && marqueeRect != null) {
                    handler.marqueeStarted(new Rectangle(marqueeRect), marqueeAdditive);
                }
                return;
            }
            updateMarqueeRect(current);
            if (handler != null && marqueeRect != null) {
                handler.marqueeUpdated(new Rectangle(marqueeRect), marqueeAdditive);
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            if (marqueeStart == null) {
                return;
            }
            if (marqueeActive) {
                updateMarqueeRect(toLocalPoint(e));
                if (handler != null && marqueeRect != null) {
                    handler.marqueeFinished(new Rectangle(marqueeRect), marqueeAdditive);
                }
            }
            marqueeStart = null;
            marqueeActive = false;
            marqueeRect = null;
            repaint();
        }
    };

    private MarqueeHandler handler;
    private Rectangle marqueeRect;
    private Point marqueeStart;
    private boolean marqueeActive;
    private boolean marqueeAdditive;
    private int dragThreshold = 4;
    private Color marqueeFill = DEFAULT_FILL;
    private Color marqueeBorder = DEFAULT_BORDER;

    public GMarqueePanel() {
        super();
    }

    public GMarqueePanel(LayoutManager layout) {
        super(layout);
    }

    public GMarqueePanel(PanelType type, Axis axis, int hgap, int vgap, boolean clearBorder) {
        super(type, axis, hgap, vgap, clearBorder);
    }

    public void setMarqueeHandler(MarqueeHandler handler) {
        this.handler = handler;
    }

    public void installMarqueeListeners(JComponent target) {
        target.addMouseListener(marqueeAdapter);
        target.addMouseMotionListener(marqueeAdapter);
    }

    public void setMarqueeColors(Color fill, Color border) {
        if (fill != null) {
            marqueeFill = fill;
        }
        if (border != null) {
            marqueeBorder = border;
        }
    }

    public void setDragThreshold(int value) {
        dragThreshold = Math.max(0, value);
    }

    @Override
    public Dimension getPreferredScrollableViewportSize() {
        return getPreferredSize();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        if (marqueeRect == null) {
            return;
        }
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setColor(marqueeFill);
        g2.fill(marqueeRect);
        g2.setColor(marqueeBorder);
        g2.draw(marqueeRect);
        g2.dispose();
    }

    private void updateMarqueeRect(Point current) {
        int x = Math.min(marqueeStart.x, current.x);
        int y = Math.min(marqueeStart.y, current.y);
        int width = Math.abs(current.x - marqueeStart.x);
        int height = Math.abs(current.y - marqueeStart.y);
        marqueeRect = new Rectangle(x, y, width, height);
        repaint();
    }

    private boolean isDragBeyondThreshold(Point start, Point current) {
        return Math.abs(current.x - start.x) >= dragThreshold
                || Math.abs(current.y - start.y) >= dragThreshold;
    }

    private Point toLocalPoint(MouseEvent e) {
        return SwingUtilities.convertPoint(e.getComponent(), e.getPoint(), this);
    }
}
