package swing.objects.selection;

import java.awt.*;
import java.util.Random;
import javax.swing.*;

public class WrapLayout extends FlowLayout {
    public int blockHgap = 10; // горизонтальный зазор
    public int blockVgap = 10; // вертикальный зазор между строками
    public int separatorVgap = 10; // дополнительный вертикальный зазор для сепаратора

    public WrapLayout() {
        super();
    }

    public WrapLayout(int align) {
        super(align);
    }

    public WrapLayout(int align, int hgap, int vgap) {
        super(align, hgap, vgap);
        this.blockHgap = hgap;
        this.blockVgap = vgap;
    }

    @Override
    public Dimension preferredLayoutSize(Container target) {
        return layoutSize(target, true);
    }

    @Override
    public Dimension minimumLayoutSize(Container target) {
        Dimension minimum = layoutSize(target, false);
        minimum.width -= (blockHgap + 1);
        return minimum;
    }

    private Dimension layoutSize(Container target, boolean preferred) {
        synchronized (target.getTreeLock()) {
            int targetWidth;
            JViewport viewport = (JViewport) SwingUtilities.getAncestorOfClass(JViewport.class, target);
            if (viewport != null) {
                targetWidth = viewport.getExtentSize().width;
            } else {
                targetWidth = target.getSize().width;
            }
            if (targetWidth <= 0) {
                targetWidth = Integer.MAX_VALUE;
            }

            Insets insets = target.getInsets();
            int horizontalInsetsAndGap = insets.left + insets.right + (blockHgap * 2);
            int maxWidth = targetWidth - horizontalInsetsAndGap;

            Dimension dim = new Dimension(0, 0);
            int rowWidth = 0;
            int rowHeight = 0;
            int nmembers = target.getComponentCount();

            for (int i = 0; i < nmembers; i++) {
                Component m = target.getComponent(i);
                if (!m.isVisible())
                    continue;

                if (m instanceof Separator) {
                    if (rowWidth > 0) {
                        addRow(dim, rowWidth, rowHeight, blockVgap);
                        rowWidth = 0;
                        rowHeight = 0;
                    }
                    Dimension d = preferred ? m.getPreferredSize() : m.getMinimumSize();
                    addRow(dim, d.width, d.height, separatorVgap);
                    continue;
                }

                Dimension d = preferred ? m.getPreferredSize() : m.getMinimumSize();
                if (rowWidth + d.width > maxWidth) {
                    addRow(dim, rowWidth, rowHeight, blockVgap);
                    rowWidth = 0;
                    rowHeight = 0;
                }
                if (rowWidth != 0) {
                    rowWidth += blockHgap;
                }
                rowWidth += d.width;
                rowHeight = Math.max(rowHeight, d.height);
            }
            addRow(dim, rowWidth, rowHeight, blockVgap);
            dim.width += horizontalInsetsAndGap;
            dim.height += insets.top + insets.bottom + blockVgap * 2;
            return dim;
        }
    }

    private void addRow(Dimension dim, int rowWidth, int rowHeight, int gap) {
        dim.width = Math.max(dim.width, rowWidth);
        if (dim.height > 0) {
            dim.height += gap;
        }
        dim.height += rowHeight;
    }
    @Override
    public void layoutContainer(Container target) {
        synchronized (target.getTreeLock()) {
            Insets insets = target.getInsets();
            int maxWidth = target.getWidth();
            int x = insets.left + blockHgap;
            int y = insets.top + blockVgap;
            int rowHeight = 0;
            int nmembers = target.getComponentCount();

            for (int i = 0; i < nmembers; i++) {
                Component m = target.getComponent(i);
                if (!m.isVisible())
                    continue;

                Dimension d = m.getPreferredSize();
                if (m instanceof Separator) {
                    if (x > insets.left + blockHgap) {
                        x = insets.left + blockHgap;
                        y += rowHeight + blockVgap;
                        rowHeight = 0;
                    }
                    m.setBounds(x, y, Math.min(d.width, maxWidth), d.height);
                    y += d.height + separatorVgap;
                    x = insets.left + blockHgap;
                    continue;
                }

                if (x + d.width > maxWidth + insets.left) {
                    x = insets.left + blockHgap;
                    y += rowHeight + blockVgap;
                    rowHeight = 0;
                }

                m.setBounds(x, y, d.width, d.height);
                x += d.width + blockHgap;
                rowHeight = Math.max(rowHeight, d.height);
            }
        }
    }

}
