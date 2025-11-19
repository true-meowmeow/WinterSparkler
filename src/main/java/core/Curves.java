package core;

public class Curves {

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
    public static final CurvePoint[] COL12 = new CurvePoint[]{
            cp(1180, 200, 0.5),
            cp(1600, 280, 0.65),
            cp(2000, 350, 0.6),
            cp(3000, 550, 0.2),
            cp(5000, 750, 0.35),
            cp(10000, 1000, 0.5)};

    // Кривая для правых панелей (Panel 2 и Panel 4)
    public static final CurvePoint[] RIGHT = new CurvePoint[]{
            cp(780, 180, 0.5),
            cp(1040, 250, 0.6),
            cp(1500, 320, 0.6),
            cp(2500, 500, 0.2),
            cp(4500, 700, 0.35),
            cp(10000, 1000, 0.5)};

    public static int eval(int containerW, CurvePoint[] pts) {
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

    public static int minRightWidth() {
        return RIGHT[0].valueWidth;
    }
}
