package core.objects;

import core.config.CurveProperties;

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

    private static final CurveProperties CURVE_PROPERTIES = CurveProperties.get();

    public static final CurvePoint[] COL12 = loadCurve(CURVE_PROPERTIES.col12());

    public static final CurvePoint[] RIGHT = loadCurve(CURVE_PROPERTIES.right());

    private static CurvePoint[] loadCurve(CurveProperties.CurveValue[] definitions) {
        CurvePoint[] points = new CurvePoint[definitions.length];
        for (int i = 0; i < definitions.length; i++) {
            CurveProperties.CurveValue def = definitions[i];
            points[i] = cp(def.windowWidth(), def.valueWidth(), def.alpha());
        }
        return points;
    }

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

        if (Math.abs(a - 0.5) <= 1e-6) {
            return t;
        }

        double strength = Math.abs(a - 0.5) * 2.0;

        double p = 1.0 + 3.0 * strength;

        if (a < 0.5) {

            return Math.pow(t, p);
        } else {

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
