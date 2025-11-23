package core.panels;

import core.config.BreakpointsProperties;
import core.config.GridProperties;
import core.config.ThemeProperties;
import core.objects.ComponentVisibilityUtils;
import core.objects.Curves;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

/**
 * Shared base for inner grid panels. Handles sizing mechanics and delegates the
 * actual layout of individual panel combinations to subclasses.
 */
abstract class AbstractInnerGridPanel extends JPanel implements LayoutManager2, ComponentVisibilityUtils {

    protected final BreakpointsProperties breakpoints = BreakpointsProperties.get();
    protected final GridProperties grid = GridProperties.get();
    protected final ThemeProperties theme = ThemeProperties.get();
    protected final PanelManager panelManager;

    protected AbstractInnerGridPanel() {
        this(new PanelManager());
    }

    protected AbstractInnerGridPanel(PanelManager panelManager) {
        super(null);
        this.panelManager = Objects.requireNonNull(panelManager, "panelManager");
        setLayout(this);
        setOpaque(true);
        setBackground(theme.columnThreeBackgroundColor().darker());
    }

    protected abstract void layoutPanels(int x, int y, int width, int height, int hTop, int hBottom);

    @Override
    public void addLayoutComponent(Component comp, Object constraints) {
        // no-op
    }

    @Override
    public void addLayoutComponent(String name, Component comp) {
        // no-op
    }

    @Override
    public void removeLayoutComponent(Component comp) {
        // no-op
    }

    @Override
    public void invalidateLayout(Container target) {
        // no-op
    }

    @Override
    public float getLayoutAlignmentX(Container target) {
        return 0.5f;
    }

    @Override
    public float getLayoutAlignmentY(Container target) {
        return 0.5f;
    }

    @Override
    public Dimension maximumLayoutSize(Container target) {
        return new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE);
    }

    @Override
    public Dimension preferredLayoutSize(Container parent) {
        Insets in = parent.getInsets();
        int prefW = grid.preferredWidth();
        int prefH = grid.preferredHeight();
        return new Dimension(in.left + in.right + prefW, in.top + in.bottom + prefH);
    }

    @Override
    public Dimension minimumLayoutSize(Container parent) {
        Insets in = parent.getInsets();
        int minRight = Curves.minRightWidth();
        int minW = Math.min(2 * minRight, grid.minWidthCap());
        int minH = grid.bottomRowHeight() + grid.extraMinHeight();
        return new Dimension(in.left + in.right + minW, in.top + in.bottom + minH);
    }

    @Override
    public void layoutContainer(Container parent) {
        Insets in = parent.getInsets();
        int width = parent.getWidth() - in.left - in.right;
        int height = parent.getHeight() - in.top - in.bottom;
        if (width <= 0 || height <= 0) {
            return;
        }

        int x = in.left;
        int y = in.top;

        int hBottom = Math.min(grid.bottomRowHeight(), Math.max(0, height));
        int hTop = Math.max(0, height - hBottom);

        layoutPanels(x, y, width, height, hTop, hBottom);
    }
}
