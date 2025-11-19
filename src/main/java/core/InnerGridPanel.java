package core;

import core.cols.Cell;
import core.config.BreakpointsProperties;
import core.config.GridProperties;
import core.config.MergeProperties;
import core.config.ThemeProperties;

import javax.swing.*;
import java.awt.*;

public class InnerGridPanel extends JPanel implements LayoutManager2, ComponentVisibilityUtils {

    private final int bottomRowHeight;
    private final BreakpointsProperties breakpoints = BreakpointsProperties.get();
    private final MergeProperties mergeProperties = MergeProperties.get();
    private final GridProperties grid = GridProperties.get();
    private final ThemeProperties theme = ThemeProperties.get();
    private boolean mergedTop = false;

    private final JPanel p1 = new Cell("1", theme.gridPanelOneColor());
    private final JPanel p2 = new Cell("2", theme.gridPanelTwoColor());
    private final JPanel p3 = new Cell("3", theme.gridPanelThreeColor());
    private final JPanel p4 = new Cell("4", theme.gridPanelFourColor());

    public InnerGridPanel(int bottomRowHeight) {
        super(null);
        this.bottomRowHeight = bottomRowHeight;
        setLayout(this);
        setOpaque(true);
        setBackground(theme.columnThreeBackgroundColor().darker());

        add(p1);
        add(p2);
        add(p3);
        add(p4);
    }

    public boolean isMergedTop() {
        return mergedTop;
    }

    public void setMergedTop(boolean merged) {
        this.mergedTop = merged;
        revalidate();
        repaint();
    }

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
        int minH = bottomRowHeight + grid.extraMinHeight();
        return new Dimension(in.left + in.right + minW, in.top + in.bottom + minH);
    }

    @Override
    public void layoutContainer(Container parent) {
        Insets in = parent.getInsets();
        int W = parent.getWidth() - in.left - in.right;
        int H = parent.getHeight() - in.top - in.bottom;
        if (W <= 0 || H <= 0) return;

        int x = in.left;
        int y = in.top;

        int hBottom = Math.min(bottomRowHeight, Math.max(0, H));
        int hTop = Math.max(0, H - hBottom);

        if (mergedTop) {
            layoutMergeMode(x, y, W, H, hTop, hBottom);
        } else {
            layoutSplitMode(x, y, W, H, hTop, hBottom);
        }
    }

    private void layoutMergeMode(int x, int y, int W, int H, int hTop, int hBottom) {
        if (hTop < mergeProperties.minP1Height()) {
            showComp(p1, x, y, W, H);
            hideComp(p2);
            hideComp(p3);
            hideComp(p4);
            return;
        }

        showComp(p1, x, y, W, hTop);
        hideComp(p4);

        int wRight = Curves.eval(W, Curves.RIGHT);
        int wLeft = Math.max(0, W - wRight);

        if (wLeft < mergeProperties.minP3Width()) {
            hideComp(p2);
            showComp(p3, x, y + hTop, W, hBottom);
            return;
        }

        showComp(p3, x, y + hTop, wLeft, hBottom);
        showComp(p2, x + wLeft, y + hTop, wRight, hBottom);
    }

    private void layoutSplitMode(int x, int y, int W, int H, int hTop, int hBottom) {
        int wRightTop = Curves.eval(W, Curves.RIGHT);
        int wLeftTop = Math.max(0, W - wRightTop);

        int wRightBottom = Curves.eval(W, Curves.RIGHT);
        int wLeftBottom = Math.max(0, W - wRightBottom);

        boolean hide1 = false, hide2 = false, hide4 = false;
        boolean extend2Down = false, extend3Up = false;

        if (W < breakpoints.widthHideP1P4()) {
            hide2 = true;
            hide4 = true;
        } else if (W < breakpoints.widthHideP4()) {
            hide4 = true;
        }

        if (H < breakpoints.heightP2ToP3()) {
            hide2 = true;
            hide4 = true;
            extend2Down = false;
            extend3Up = false;

            showComp(p1, x, y, W, hTop);
            hideComp(p2);
            hideComp(p4);
            showComp(p3, x, y + hTop, W, hBottom);
            return;
        } else {
            if (H < breakpoints.heightP1P2ToP3()) {
                hide1 = true;
                extend3Up = true;
                if (W < breakpoints.widthHideP2Mid() && !hide2) {
                    hide2 = true;
                }
            }
            if (H < breakpoints.heightP4ToP2()) {
                hide4 = true;
                if (!hide2) extend2Down = true;
            }
        }

        if (W < breakpoints.widthHideP1P4() && H >= breakpoints.heightP1P2ToP3()) {
            hide1 = true;
            hide2 = false;
            hide4 = true;
            extend2Down = false;
            extend3Up = false;

            wRightTop = W;
            wLeftTop = 0;
        }

        if (hide2) {
            wRightTop = 0;
            wLeftTop = W;
        }
        if (hide4 && !extend2Down) {
            wRightBottom = 0;
            wLeftBottom = W;
        }
        if (extend2Down) {
            int rightW = hide2 ? 0 : wRightTop;
            wRightBottom = rightW;
            wLeftBottom = Math.max(0, W - rightW);
        }

        if (!hide1) {
            showComp(p1, x, y, wLeftTop, hTop);
        } else {
            hideComp(p1);
        }

        if (!hide2) {
            int h2 = extend2Down ? (hTop + hBottom) : hTop;
            showComp(p2, x + wLeftTop, y, wRightTop, h2);
        } else {
            hideComp(p2);
        }

        if (extend3Up) {
            int rightOcc = (!hide2) ? wRightTop : 0;
            int wLeftTall = Math.max(0, W - rightOcc);
            showComp(p3, x, y, wLeftTall, hTop + hBottom);
        } else {
            showComp(p3, x, y + hTop, wLeftBottom, hBottom);
        }

        if (!hide4 && !extend2Down) {
            showComp(p4, x + wLeftBottom, y + hTop, wRightBottom, hBottom);
        } else {
            hideComp(p4);
        }
    }
}
