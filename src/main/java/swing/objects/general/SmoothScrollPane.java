package swing.objects.general;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * A {@link JScrollPane} with smooth, inertial scrolling similar to modern IDEs.
 * <p>
 *     <b>Tunable at runtime</b> –the three key parameters that control the feel of the scroll
 *     are exposed via setters and an extended constructor:
 *     <ul>
 *         <li><strong>scrollFactor</strong>– how many <em>pixels of velocity</em> are added per wheel "notch".</li>
 *         <li><strong>friction</strong>– multiplier(0&lt;f&lt;1) applied every frame. Lower values stop faster.</li>
 *         <li><strong>minVelocity</strong>– velocity threshold below which motion is considered finished.</li>
 *     </ul>
 */
public class SmoothScrollPane extends JScrollPane {

    // ────────────────────────────────────────────────────────────────────────────
    //  Defaults (can be overridden per‑instance)
    // ────────────────────────────────────────────────────────────────────────────

    private static final int FPS = 1000;                       // animation frame‑rate
    private static final int TIMER_DELAY = 1000 / FPS;       // ms between frames

    private double scrollFactor;                      // px velocity per wheel notch
    private double friction;                          // 0<f<1
    private double minVelocity ;                      // px / frame

    // ────────────────────────────────────────────────────────────────────────────
    //  State (per instance)
    // ────────────────────────────────────────────────────────────────────────────

    private double velocityY = 0.0; // px / frame
    private double velocityX = 0.0; // px / frame (⇧+wheel)

    private final Timer animationTimer;

    // ────────────────────────────────────────────────────────────────────────────
    //  Construction
    // ────────────────────────────────────────────────────────────────────────────

    /** Creates a pane with default feel. */
    public SmoothScrollPane(Component view) {
        this(view, 17, 0.75, 0.5);
    }

    /**
     * Creates a pane with fully‑customised parameters.
     *
     * @param view         the component to display
     * @param scrollFactor pixels of velocity added per wheel notch (positive = faster)
     * @param friction     0&lt;f&lt;1, smaller = stronger deceleration
     * @param minVelocity  below this (px / frame) the animation stops
     */
    public SmoothScrollPane(Component view,
                            double scrollFactor,
                            double friction,
                            double minVelocity) {
        super(view, VERTICAL_SCROLLBAR_AS_NEEDED, HORIZONTAL_SCROLLBAR_NEVER);
        setWheelScrollingEnabled(false);

        this.scrollFactor = scrollFactor;
        this.friction     = validateCoefficient(friction, "friction");
        this.minVelocity  = validatePositive(minVelocity, "minVelocity");

        animationTimer = new Timer(TIMER_DELAY, e -> runAnimationStep());
        animationTimer.setRepeats(true);

        addMouseWheelListener(this::handleWheelEvent);
    }

    // ────────────────────────────────────────────────────────────────────────────
    //  Wheel events → add velocity
    // ────────────────────────────────────────────────────────────────────────────

    private void handleWheelEvent(MouseWheelEvent e) {
        double delta = e.getPreciseWheelRotation() * scrollFactor;
        if (e.isShiftDown()) {
            velocityX += delta;
        } else {
            velocityY += delta;
        }
        if (!animationTimer.isRunning()) {
            animationTimer.start();
        }
        e.consume();
    }

    // ────────────────────────────────────────────────────────────────────────────
    //  Animation loop – runs on EDT every frame
    // ────────────────────────────────────────────────────────────────────────────

    private void runAnimationStep() {
        boolean movedSomething = false;

        // ── Vertical ─────────────────────────────────────────────────────────
        JScrollBar vBar = getVerticalScrollBar();
        if (vBar != null && vBar.isVisible() && Math.abs(velocityY) >= minVelocity) {
            boolean moved = scrollBarBy(vBar, velocityY);
            movedSomething |= moved;
            if (moved) {
                velocityY *= friction;
            } else {
                velocityY = 0.0; // clamp at limit –drop inertia so direction change is instant
            }
        } else {
            velocityY = 0.0;
        }

        // ── Horizontal (⇧+wheel) ──────────────────────────────────────────
        JScrollBar hBar = getHorizontalScrollBar();
        if (hBar != null && hBar.isVisible() && Math.abs(velocityX) >= minVelocity) {
            boolean moved = scrollBarBy(hBar, velocityX);
            movedSomething |= moved;
            if (moved) {
                velocityX *= friction;
            } else {
                velocityX = 0.0;
            }
        } else {
            velocityX = 0.0;
        }

        if (!movedSomething) {
            animationTimer.stop();
        }
    }

    // ────────────────────────────────────────────────────────────────────────────
    //  Helpers
    // ────────────────────────────────────────────────────────────────────────────

    private static boolean scrollBarBy(JScrollBar bar, double delta) {
        int start = bar.getValue();
        int end   = (int) Math.round(start + delta);
        int clamp = Math.max(bar.getMinimum(), Math.min(end, bar.getMaximum() - bar.getVisibleAmount()));
        if (clamp != start) {
            bar.setValue(clamp);
            return true;
        }
        return false;
    }

    private static double validateCoefficient(double value, String name) {
        if (value <= 0 || value >= 1) {
            throw new IllegalArgumentException(name + " must be in (0,1)");
        }
        return value;
    }

    private static double validatePositive(double value, String name) {
        if (value <= 0) {
            throw new IllegalArgumentException(name + " must be > 0");
        }
        return value;
    }
}
