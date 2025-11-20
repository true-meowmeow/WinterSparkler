import com.formdev.flatlaf.FlatDarkLaf;

import javax.swing.*;
import java.awt.*;

/**
 * ResponsiveSwingDemo
 * <p>
 * Демонстрация:
 * - 3-колоночный верхний layout с брейкпоинтами и merge-режимом
 * - Внутренней 2x2 сетки с адаптивным поведением по ширине/высоте
 * <p>
 * Логика брейкпоинтов и раскладки сохранена максимально близко к исходнику.
 */
public class ResponsiveSwingDemo {

    /* ===========================
     *           CONFIG
     * =========================== */

    private static final class WindowConfig {
        static final String TITLE = "Responsive Swing Demo";
        static final int WIDTH = 1600;
        static final int HEIGHT = 900;

        static final int MIN_WIDTH = 420;
        static final int MIN_HEIGHT = 300;
    }

    private static final class GridConfig {
        static final int PREF_WIDTH = 800;
        static final int PREF_HEIGHT = 600;
        static final int MIN_WIDTH_CAP = 600;
        static final int EXTRA_MIN_HEIGHT = 200;
        static final int BOTTOM_ROW_HEIGHT = 260;
    }

    private static final class Breakpoints {
        // Внешний layout
        static final int THREE_COL_WIDTH = 1030;
        static final int MERGE_HIDE_COLS_UNDER_WIDTH = 820;

        // Сетка по ширине
        static final int WIDTH_HIDE_P4 = 600;
        static final int WIDTH_HIDE_P1_P4 = 600;
        static final int WIDTH_HIDE_P2_MID = 650;

        // Сетка по высоте
        static final int HEIGHT_P4_TO_P2 = 575;
        static final int HEIGHT_P1_P2_TO_P3 = 430;
        static final int HEIGHT_P2_TO_P3 = 200;
    }

    private static final class MergeConfig {
        static final int MIN_P3_WIDTH = 470;
        static final int MIN_P1_HEIGHT = 240;
    }

    private static final class UiText {
        static final String BTN_MERGE = "Merge";
        static final String BTN_SPLIT = "Unmerge";
        static final boolean SHOW_SPLIT_TEXT = true;
    }

    private static final class Colors {
        static final Color COL1 = new Color(0x2D6CDF);
        static final Color COL2 = new Color(0x2FBF71);
        static final Color COL3_BG = new Color(0x1E1F22);

        static final Color GRID_P1 = new Color(0xD9534F);
        static final Color GRID_P2 = new Color(0xF0AD4E);
        static final Color GRID_P3 = new Color(0x5BC0DE);
        static final Color GRID_P4 = new Color(0xB36AE2);

        static final Color TEXT_COLOR = Color.WHITE;
    }

    private static final class Fonts {
        static final Font CELL = new Font(Font.SANS_SERIF, Font.BOLD, 16);
        static final float DEMO_PANEL_TITLE_SIZE = 18f;
    }

    /* ===========================
     *       CURVES ENGINE
     * =========================== */

    private static final class Curves {

        static final class CurvePoint {
            final int atWindowWidth;
            final int valueWidth;
            final double alpha; // 0..1

            CurvePoint(int w, int v, double a) {
                this.atWindowWidth = w;
                this.valueWidth = v;
                this.alpha = a;
            }
        }

        private static CurvePoint cp(int w, int v, double a) {
            return new CurvePoint(w, v, a);
        }

        // Кривые для COL1/COL2 (оба равны)
        static final CurvePoint[] COL12 = new CurvePoint[]{
                cp(1180, 200, 0.5),
                cp(1600, 280, 0.65),
                cp(2000, 350, 0.6),
                cp(3000, 550, 0.2),
                cp(5000, 750, 0.35),
                cp(10000, 1000, 0.5)};

        // Кривая для правых панелей (Panel 2 и Panel 4)
        static final CurvePoint[] RIGHT = new CurvePoint[]{
                cp(780, 180, 0.5),
                cp(1040, 250, 0.6),
                cp(1500, 320, 0.6),
                cp(2500, 500, 0.2),
                cp(4500, 700, 0.35),
                cp(10000, 1000, 0.5)};

        static int eval(int containerW, CurvePoint[] pts) {
            if (pts == null || pts.length == 0) return 0;

            if (containerW <= pts[0].atWindowWidth) return pts[0].valueWidth;
            int last = pts.length - 1;
            if (containerW >= pts[last].atWindowWidth) return pts[last].valueWidth;

            for (int i = 0; i < last; i++) {
                CurvePoint A = pts[i];
                CurvePoint B = pts[i + 1];
                if (containerW >= A.atWindowWidth && containerW <= B.atWindowWidth) {
                    double span = Math.max(1.0, (double) (B.atWindowWidth - A.atWindowWidth));
                    double t = (containerW - A.atWindowWidth) / span;   // 0..1
                    double tB = centeredBias01(t, B.alpha);
                    double v = A.valueWidth + tB * (B.valueWidth - A.valueWidth);
                    return (int) Math.round(v);
                }
            }
            return pts[last].valueWidth;
        }

        private static double centeredBias01(double t, double a) {
            t = clamp01(t);
            a = clamp01(a);

            // Ровно посередине — строго линейная кривая
            if (Math.abs(a - 0.5) <= 1e-6) {
                return t;
            }

            // Насколько сильно изгибаем кривую (0..1)
            double strength = Math.abs(a - 0.5) * 2.0;
            // Экспонента 1..4: 1 — линейно, 4 — сильная кривизна
            double p = 1.0 + 3.0 * strength;

            if (a < 0.5) {
                // Низкая alpha: плавный старт, ускорение в конце
                return Math.pow(t, p);
            } else {
                // Высокая alpha: быстрый старт, плавное дотягивание к концу
                return 1.0 - Math.pow(1.0 - t, p);
            }
        }

        private static double clamp01(double v) {
            return v < 0 ? 0 : (v > 1 ? 1 : v);
        }

        static int minRightWidth() {
            return RIGHT[0].valueWidth;
        }
    }

    /* ===========================
     *        COMMON UTILS
     * =========================== */

    private enum Role {COL1, COL2, COL3}

    private static JPanel demoPanel(String title, Color bg) {
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(bg);

        JLabel lbl = new JLabel(title, SwingConstants.CENTER);
        lbl.setForeground(Colors.TEXT_COLOR);
        lbl.setFont(Fonts.CELL.deriveFont(Fonts.DEMO_PANEL_TITLE_SIZE));

        p.add(lbl, BorderLayout.CENTER);
        return p;
    }

    private static JPanel cell(String title, Color bg) {
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(bg);

        JLabel lbl = new JLabel("Panel " + title, SwingConstants.CENTER);
        lbl.setForeground(Colors.TEXT_COLOR);
        lbl.setFont(Fonts.CELL);

        p.add(lbl, BorderLayout.CENTER);
        return p;
    }

    private static void showComp(Component c, int x, int y, int w, int h) {
        c.setVisible(true);
        c.setBounds(x, y, w, h);
    }

    private static void hideComp(Component c) {
        c.setVisible(false);
        c.setBounds(0, 0, 0, 0);
    }

    /* ===========================
     *      TOP 3-COLUMN LAYOUT
     * =========================== */

    static class ThreeColumnLayout implements LayoutManager2 {
        private final int breakpoint;

        private Component c1;
        private Component c2;
        private Component c3;

        // Когда true — COL1/COL2 не скрываем ниже брейкпоинта (кроме < MERGE_HIDE_COLS_UNDER_WIDTH)
        private boolean forceColsAlwaysVisible = false;

        ThreeColumnLayout(int breakpoint) {
            this.breakpoint = breakpoint;
        }

        void setForceColsAlwaysVisible(boolean force) {
            this.forceColsAlwaysVisible = force;
        }

        @Override
        public void addLayoutComponent(Component comp, Object constraints) {
            if (constraints instanceof Role role) {
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
                addLayoutComponent(comp, Role.COL1);
            } else if ("col2".equals(name)) {
                addLayoutComponent(comp, Role.COL2);
            } else if ("col3".equals(name)) {
                addLayoutComponent(comp, Role.COL3);
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
            int w = WindowConfig.WIDTH;
            int h = WindowConfig.HEIGHT;
            return new Dimension(in.left + in.right + w, in.top + in.bottom + h);
        }

        @Override
        public Dimension minimumLayoutSize(Container parent) {
            Insets in = parent.getInsets();
            int w = WindowConfig.MIN_WIDTH;
            int h = WindowConfig.MIN_HEIGHT;
            return new Dimension(in.left + in.right + w, in.top + in.bottom + h);
        }

        @Override
        public void layoutContainer(Container parent) {
            Insets in = parent.getInsets();
            int W = parent.getWidth() - in.left - in.right;
            int H = parent.getHeight() - in.top - in.bottom;
            if (W < 0 || H < 0) return;

            // Если Merge активен и ширина < MERGE_HIDE_COLS_UNDER_WIDTH, скрываем COL1/COL2
            if (forceColsAlwaysVisible && W < Breakpoints.MERGE_HIDE_COLS_UNDER_WIDTH) {
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
            int w3 = Math.max(0, W - (w1 + w2 + 2));

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

    /* ===========================
     *        INNER 2x2 GRID
     * =========================== */

    static class InnerGridPanel extends JPanel implements LayoutManager2 {

        private final int bottomRowHeight;
        private boolean mergedTop = false;

        private final JPanel p1 = cell("1", Colors.GRID_P1);
        private final JPanel p2 = cell("2", Colors.GRID_P2);
        private final JPanel p3 = cell("3", Colors.GRID_P3);
        private final JPanel p4 = cell("4", Colors.GRID_P4);

        InnerGridPanel(int bottomRowHeight) {
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

        boolean isMergedTop() {
            return mergedTop;
        }

        void setMergedTop(boolean merged) {
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

    /* ===========================
     *             MAIN
     * =========================== */

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ResponsiveSwingDemo::start);
    }

    private static void start() {
        FlatDarkLaf.setup();

        JFrame frame = createFrame();
        JPanel root = createRootPanel();

        frame.setContentPane(root);
        frame.setVisible(true);
    }

    private static JFrame createFrame() {
        JFrame f = new JFrame(WindowConfig.TITLE);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        f.setSize(WindowConfig.WIDTH, WindowConfig.HEIGHT);
        f.setMinimumSize(new Dimension(WindowConfig.MIN_WIDTH, WindowConfig.MIN_HEIGHT));
        f.setLocationRelativeTo(null);

        return f;
    }

    private static JPanel createRootPanel() {
        ThreeColumnLayout layout = new ThreeColumnLayout(Breakpoints.THREE_COL_WIDTH);
        JPanel root = new JPanel(layout);

        JPanel col1 = demoPanel("COL 1", Colors.COL1);
        JPanel col2 = demoPanel("COL 2", Colors.COL2);
        JPanel col3 = createCol3(layout, root);

        // Можно добавлять либо через enum Role, либо строками "col1"/"col2"/"col3"
        root.add(col1, Role.COL1);
        root.add(col2, Role.COL2);
        root.add(col3, Role.COL3);

        return root;
    }

    private static JPanel createCol3(ThreeColumnLayout rootLayout, JPanel root) {
        JPanel col3 = new JPanel(new BorderLayout());
        col3.setBackground(Colors.COL3_BG);

        // Тулбар
        JToolBar toolbar = new JToolBar();
        toolbar.setFloatable(false);

        JButton mergeBtn = new JButton(UiText.BTN_MERGE);
        toolbar.add(mergeBtn);

        // Внутренняя сетка
        InnerGridPanel grid = new InnerGridPanel(GridConfig.BOTTOM_ROW_HEIGHT);

        // Логика Merge/Unmerge
        mergeBtn.addActionListener(e -> {
            boolean newMode = !grid.isMergedTop();
            grid.setMergedTop(newMode);
            rootLayout.setForceColsAlwaysVisible(newMode);
            if (UiText.SHOW_SPLIT_TEXT) {
                mergeBtn.setText(newMode ? UiText.BTN_SPLIT : UiText.BTN_MERGE);
            }
            root.revalidate();
            root.repaint();
        });

        col3.add(toolbar, BorderLayout.NORTH);
        col3.add(grid, BorderLayout.CENTER);

        return col3;
    }
}
