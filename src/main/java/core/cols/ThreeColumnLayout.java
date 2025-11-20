package core.cols;

import core.objects.ComponentVisibilityUtils;
import core.objects.Curves;
import core.JRoot;
import core.config.BreakpointsProperties;
import core.config.CoreProperties;

import java.awt.*;

public class ThreeColumnLayout implements LayoutManager2, ComponentVisibilityUtils {
    private final int breakpoint;
    private final BreakpointsProperties breakpoints = BreakpointsProperties.get();

    private Component c1;
    private Component c2;
    private Component c3;

    // Когда true — COL1/COL2 не скрываем ниже брейкпоинта (кроме < MERGE_HIDE_COLS_UNDER_WIDTH)
    private boolean forceColsAlwaysVisible = false;

    public ThreeColumnLayout(int breakpoint) {
        this.breakpoint = breakpoint;
    }

    public void setForceColsAlwaysVisible(boolean force) {
        this.forceColsAlwaysVisible = force;
    }

    @Override
    public void addLayoutComponent(Component comp, Object constraints) {
        if (constraints instanceof JRoot.Role role) {
            switch (role) {
                case COL1 -> c1 = comp;
                case COL2 -> c2 = comp;
                case COL3 -> c3 = comp;
            }
        }
    }

    @Override
    public void addLayoutComponent(String name, Component comp) {
        if ("col1".equals(name)) {
            addLayoutComponent(comp, JRoot.Role.COL1);
        } else if ("col2".equals(name)) {
            addLayoutComponent(comp, JRoot.Role.COL2);
        } else if ("col3".equals(name)) {
            addLayoutComponent(comp, JRoot.Role.COL3);
        }
    }

    @Override
    public void removeLayoutComponent(Component comp) {
        if (comp == c1) c1 = null;
        if (comp == c2) c2 = null;
        if (comp == c3) c3 = null;
    }

    @Override
    public Dimension preferredLayoutSize(Container parent) {
        Insets in = parent.getInsets();
        CoreProperties props = CoreProperties.get();
        int w = props.windowWidth();
        int h = props.windowHeight();
        return new Dimension(in.left + in.right + w, in.top + in.bottom + h);
    }

    @Override
    public Dimension minimumLayoutSize(Container parent) {
        Insets in = parent.getInsets();
        CoreProperties props = CoreProperties.get();
        int w = props.windowMinWidth();
        int h = props.windowMinHeight();
        return new Dimension(in.left + in.right + w, in.top + in.bottom + h);
    }

    @Override
    public void layoutContainer(Container parent) {
        Insets in = parent.getInsets();
        int W = parent.getWidth() - in.left - in.right;
        int H = parent.getHeight() - in.top - in.bottom;
        if (W < 0 || H < 0) return;

        // Если Merge активен и ширина < MERGE_HIDE_COLS_UNDER_WIDTH, скрываем COL1/COL2
        if (forceColsAlwaysVisible && W < breakpoints.mergeHideColumnsWidth()) {
            layoutOnlyCol3(in, W, H);
        }
        // Если ниже брейкпоинта и НЕ форсируем видимость — показываем только col3
        else if (W < breakpoint && !forceColsAlwaysVisible) {
            layoutOnlyCol3(in, W, H);
        } else {
            layoutThreeColumns(in, W, H);
        }
    }

    private void layoutOnlyCol3(Insets in, int W, int H) {
        int x = in.left;
        int y = in.top;

        if (c1 != null) {
            hideComp(c1);
        }
        if (c2 != null) {
            hideComp(c2);
        }
        if (c3 != null) {
            showComp(c3, x, y, W, H);
        }
    }

    private void layoutThreeColumns(Insets in, int W, int H) {
        int x = in.left;
        int y = in.top;

        // Ширина колонок 1 и 2 берётся по кривой COL12
        int w12 = Curves.eval(W, Curves.COL12);
        int maxEach = Math.max(0, (W - 2) / 2);
        w12 = Math.min(w12, maxEach);

        int w1 = w12;
        int w2 = w12;
        int w3 = Math.max(0, W - (w1 + w2));

        if (c1 != null) {
            showComp(c1, x, y, w1, H);
        }
        if (c2 != null) {
            showComp(c2, x + w1, y, w2, H);
        }
        if (c3 != null) {
            showComp(c3, x + w1 + w2, y, w3, H);
        }
    }

    @Override
    public Dimension maximumLayoutSize(Container target) {
        return new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE);
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
    public void invalidateLayout(Container target) {
        // no-op
    }
}
