package obsolete.swing.elements.pages.home.playground.queue;

import obsolete.swing.core.basics.JPanelCustom;
import obsolete.swing.elements.pages.home.RightSideMode;

import java.awt.*;

public class QueuePanel extends JPanelCustom {

    public QueuePanel() {
        this(RightSideMode.HOME);
    }

    public QueuePanel(RightSideMode mode) {
        applyMode(mode);
    }

    private void applyMode(RightSideMode mode) {
        Color background = switch (mode) {
            case HOME -> Color.BLUE;
            case MANAGE -> Color.RED;
        };
        setBackground(background);
    }
}
