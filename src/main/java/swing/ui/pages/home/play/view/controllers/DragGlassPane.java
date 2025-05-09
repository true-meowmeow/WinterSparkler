package swing.ui.pages.home.play.view.controllers;

import javax.swing.*;
import java.awt.*;

public class DragGlassPane extends JComponent {
    private String ghostText;
    private Point ghostLocation;
    private final int offset = 5;

    public void configure(int countObjects, Point point) {
        this.ghostText = countObjects > 1 ? "Dragging " + countObjects + " objects" : "Dragging object";
        setGhostLocation(point);
        setVisible(true);
    }

    public void setGhostLocation(Point point) {
        this.ghostLocation = new Point(point.x + offset, point.y + offset);
        repaint();
    }

    public void clearGhost() {
        ghostText = null;
        ghostLocation = null;
        setVisible(false);
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        if (ghostText != null && ghostLocation != null) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.7f));
            FontMetrics fm = g2.getFontMetrics();
            int textWidth = fm.stringWidth(ghostText);
            int textHeight = fm.getHeight();
            int padding = 4;
            int width = textWidth + padding * 2;
            int height = textHeight + padding * 2;
            int x = ghostLocation.x;
            int y = ghostLocation.y;
            g2.setColor(Color.YELLOW);
            g2.fillRect(x, y, width, height);
            g2.setColor(Color.BLACK);
            g2.drawRect(x, y, width, height);
            g2.drawString(ghostText, x + padding, y + fm.getAscent() + padding);
        }
    }
}
