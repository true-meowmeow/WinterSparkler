package swing.objects.movement;

import javax.swing.*;
import java.awt.*;

public class DragGlassPane extends JComponent {
    private String ghostText;
    private Point ghostLocation;

    public void setGhostText(String text) {
        this.ghostText = text;
        repaint();
    }

    public void setGhostLocation(Point p) {
        this.ghostLocation = p;
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
