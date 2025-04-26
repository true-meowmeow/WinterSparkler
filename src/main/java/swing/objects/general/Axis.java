package swing.objects.general;

import swing.objects.WrapLayout;

import javax.swing.*;
import java.awt.*;

public enum Axis {
    // Оси для BoxLayout
    X_AX(BoxLayout.X_AXIS),
    Y_AX(BoxLayout.Y_AXIS),
    LINE_AX(BoxLayout.LINE_AXIS),
    PAGE_AX(BoxLayout.PAGE_AXIS),

    // Выравнивания для FlowLayout и WrapLayout
    LEFT(FlowLayout.LEFT),
    RIGHT(FlowLayout.RIGHT),
    CENTER(FlowLayout.CENTER),
    LEADING(FlowLayout.LEADING),
    TRAILING(FlowLayout.TRAILING);

    private final int value;

    Axis(int value) {
        this.value = value;
    }

    public BoxLayout createBoxLayout(Container target) {
        return new BoxLayout(target, value);
    }

    public FlowLayout createFlowLayout(int hgap, int vgap) {
        return new FlowLayout(value, hgap, vgap);
    }

    public WrapLayout createWrapLayout(int hgap, int vgap) {
        return new WrapLayout(value, hgap, vgap);
    }
}
