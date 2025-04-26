package swing.objects.general.panel;

import swing.objects.WrapLayout;

import java.awt.*;

/** Описывает, какой LayoutManager использовать. */
public enum PanelType {
    GRID {
        @Override
        public LayoutManager createLayout(Container c, Axis axis, int hgap, int vgap) {
            return new GridBagLayout();
        }
    },
    BORDER {
        @Override
        public LayoutManager createLayout(Container c, Axis axis, int hgap, int vgap) {
            // если axis задан, превращаем Border в Box
            return axis != null
                    ? axis.createBoxLayout(c)
                    : new BorderLayout();
        }
    },
    FLOW {
        @Override
        public LayoutManager createLayout(Container c, Axis axis, int hgap, int vgap) {
            // FlowLayout без axis: CENTER, 5,5 по умолчанию
            if (axis == null) return new FlowLayout();
            return axis.createFlowLayout(hgap, vgap);
        }
    },
    WRAP {
        @Override
        public LayoutManager createLayout(Container c, Axis axis, int hgap, int vgap) {
            // WrapLayout без axis: CENTER, 5,5
            if (axis == null) return new WrapLayout();
            return axis.createWrapLayout(hgap, vgap);
        }
    },
    BOX {
        @Override
        public LayoutManager createLayout(Container c, Axis axis, int hgap, int vgap) {
            // по умолчанию вертикальная ось
            Axis ax = (axis != null) ? axis : Axis.Y_AX;
            return ax.createBoxLayout(c);
        }
    },
    CARD {
        @Override
        public LayoutManager createLayout(Container c, Axis axis, int hgap, int vgap) {
            return new CardLayout();
        }
    };

    /**
     * Создаёт LayoutManager для данного контейнера.
     *
     * @param c     контейнер, которому ставим layout
     * @param axis  ось/выравнивание или null
     * @param hgap  горизонтальный gap (для FLOW/WRAP)
     * @param vgap  вертикальный gap   (для FLOW/WRAP)
     */
    public abstract LayoutManager createLayout(Container c, Axis axis, int hgap, int vgap);
}
