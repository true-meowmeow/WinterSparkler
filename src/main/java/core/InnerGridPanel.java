package core;

import core.cols.Cell;
import core.configOld.Breakpoints;
import core.configOld.Colors;
import core.configOld.GridConfig;
import core.configOld.MergeConfig;

import javax.swing.*;
import java.awt.*;

public class InnerGridPanel extends JPanel implements LayoutManager2, ComponentVisibilityUtils {

    private final int bottomRowHeight;
    private boolean mergedTop = false;

    private final JPanel p1 = new Cell("1", Colors.GRID_P1);
    private final JPanel p2 = new Cell("2", Colors.GRID_P2);
    private final JPanel p3 = new Cell("3", Colors.GRID_P3);
    private final JPanel p4 = new Cell("4", Colors.GRID_P4);

    public InnerGridPanel(int bottomRowHeight) {
        super(null);
        this.bottomRowHeight = bottomRowHeight;
        setLayout(this);
        setOpaque(true);
        setBackground(Colors.COL3_BG.darker());

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
        // не используется
    }

    @Override
    public void addLayoutComponent(String name, Component comp) {
        // не используется
    }

    @Override
    public void removeLayoutComponent(Component comp) {
        // не используется
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
        int prefW = GridConfig.PREF_WIDTH;
        int prefH = GridConfig.PREF_HEIGHT;
        return new Dimension(in.left + in.right + prefW, in.top + in.bottom + prefH);
    }

    @Override
    public Dimension minimumLayoutSize(Container parent) {
        Insets in = parent.getInsets();
        int minRight = Curves.minRightWidth();
        int minW = Math.min(2 * minRight, GridConfig.MIN_WIDTH_CAP);
        int minH = bottomRowHeight + GridConfig.EXTRA_MIN_HEIGHT;
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
        // 1) Проверка высоты верхней панели p1
        if (hTop < MergeConfig.MIN_P1_HEIGHT) {
            // p1 занимает всю высоту, остальные скрыты
            showComp(p1, x, y, W, H);
            hideComp(p2);
            hideComp(p3);
            hideComp(p4);
            return;
        }

        // Верх: p1 на всю ширину
        showComp(p1, x, y, W, hTop);

        // p4 скрыта по условию Merge
        hideComp(p4);

        // Нижний ряд: p3 слева, p2 справа (ширина правой — по кривой RIGHT)
        int wRight = Curves.eval(W, Curves.RIGHT);
        int wLeft = Math.max(0, W - wRight);

        // 2) Проверка минимальной ширины p3
        if (wLeft < MergeConfig.MIN_P3_WIDTH) {
            // скрываем p2, p3 получает всю нижнюю строку
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

        // Ширина: что прятать
        if (W < Breakpoints.WIDTH_HIDE_P1_P4) {
            hide2 = true;
            hide4 = true;
        } else if (W < Breakpoints.WIDTH_HIDE_P4) {
            hide4 = true;
        }

        // Высота: особые режимы
        if (H < Breakpoints.HEIGHT_P2_TO_P3) {
            // Сверху только P1, снизу только P3 (как было)
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
            if (H < Breakpoints.HEIGHT_P1_P2_TO_P3) {
                hide1 = true;
                extend3Up = true;
                if (W < Breakpoints.WIDTH_HIDE_P2_MID && !hide2) {
                    hide2 = true;
                }
            }
            if (H < Breakpoints.HEIGHT_P4_TO_P2) {
                hide4 = true;
                if (!hide2) extend2Down = true;
            }
        }

        // Правило: узко и высоко → сверху P2, снизу P3
        if (W < Breakpoints.WIDTH_HIDE_P1_P4 && H >= Breakpoints.HEIGHT_P1_P2_TO_P3) {
            hide1 = true;    // убираем P1
            hide2 = false;   // P2 должна быть видима
            hide4 = true;    // P4 не нужна
            extend2Down = false;
            extend3Up = false;

            // P2 занимает весь верхний ряд
            wRightTop = W;
            wLeftTop = 0;
        }

        // Уточнение ширин после флагов скрытия/растяжения
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

        // Применяем флаги к панелям
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