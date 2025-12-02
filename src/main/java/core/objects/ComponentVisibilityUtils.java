package core.objects;

import java.awt.*;

public interface ComponentVisibilityUtils {

    default void showComp(Component c, int x, int y, int w, int h) {
        c.setVisible(true);
        c.setBounds(x, y, w, h);
    }

    default void hideComp(Component c) {
        c.setVisible(false);
        c.setBounds(0, 0, 0, 0);
    }
}
