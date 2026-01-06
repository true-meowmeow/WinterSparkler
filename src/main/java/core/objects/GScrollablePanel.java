package core.objects;

import core.main.check.Axis;
import core.main.check.PanelType;

import javax.swing.JViewport;
import javax.swing.Scrollable;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.awt.Rectangle;

public class GScrollablePanel extends GPanel implements Scrollable {
    private boolean trackViewportWidth = true;
    private boolean trackViewportHeight = false;
    private int unitIncrement = 10;

    public GScrollablePanel() {
        super();
    }

    public GScrollablePanel(LayoutManager layout) {
        super(layout);
    }

    public GScrollablePanel(PanelType type, Axis axis, int hgap, int vgap, boolean clearBorder) {
        super(type, axis, hgap, vgap, clearBorder);
    }

    public void setScrollableTracksViewportWidth(boolean value) {
        trackViewportWidth = value;
    }

    public void setScrollableTracksViewportHeight(boolean value) {
        trackViewportHeight = value;
    }

    public void setScrollableUnitIncrement(int value) {
        unitIncrement = Math.max(1, value);
    }

    @Override
    public Dimension getPreferredScrollableViewportSize() {
        return getPreferredSize();
    }

    @Override
    public int getScrollableUnitIncrement(Rectangle visibleRect, int orientation, int direction) {
        return unitIncrement;
    }

    @Override
    public int getScrollableBlockIncrement(Rectangle visibleRect, int orientation, int direction) {
        int block = orientation == SwingConstants.VERTICAL
                ? visibleRect.height - unitIncrement
                : visibleRect.width - unitIncrement;
        return Math.max(block, unitIncrement);
    }

    @Override
    public boolean getScrollableTracksViewportWidth() {
        return trackViewportWidth;
    }

    @Override
    public boolean getScrollableTracksViewportHeight() {
        if (!trackViewportHeight) {
            return false;
        }
        JViewport viewport = (JViewport) SwingUtilities.getAncestorOfClass(JViewport.class, this);
        if (viewport == null) {
            return true;
        }
        return viewport.getHeight() > getPreferredSize().height;
    }
}
