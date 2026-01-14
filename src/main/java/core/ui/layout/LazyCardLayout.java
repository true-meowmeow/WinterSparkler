package core.ui.layout;

import java.awt.*;

/**
 * CardLayout variant that only lays out the currently visible card.
 * This avoids repeatedly setting bounds on all hidden cards on every
 * resize/validate pass.
 */
public class LazyCardLayout extends CardLayout {

    @Override
    public void layoutContainer(Container parent) {
        synchronized (parent.getTreeLock()) {
            Insets insets = parent.getInsets();
            int n = parent.getComponentCount();

            Component visible = null;
            for (int i = 0; i < n; i++) {
                Component c = parent.getComponent(i);
                if (c.isVisible()) {
                    visible = c;
                    break;
                }
            }

            // Ensure some card is visible if none are currently shown.
            if (visible == null && n > 0) {
                visible = parent.getComponent(0);
                visible.setVisible(true);
            }

            if (visible != null) {
                int hgap = getHgap();
                int vgap = getVgap();
                visible.setBounds(
                        hgap + insets.left,
                        vgap + insets.top,
                        parent.getWidth() - (hgap * 2 + insets.left + insets.right),
                        parent.getHeight() - (vgap * 2 + insets.top + insets.bottom)
                );
            }
        }
    }
}
