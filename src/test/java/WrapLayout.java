import java.awt.*;
import javax.swing.*;

public class WrapLayout extends FlowLayout {
    // Дополнительные параметры для обычных блоков
    private int blockHgap = 10; // горизонтальный зазор между обычными блоками
    private int blockVgap = 10; // вертикальный зазор между обычными строками

    // Отдельный зазор для сепараторов (К реальному зазору прибавится вертикальный гап)
    private int separatorVgap = 0; // значение по умолчанию

    public WrapLayout() {
        super();
    }

    public WrapLayout(int align) {
        super(align);
    }

    public WrapLayout(int align, int hgap, int vgap) {
        // Параметры FlowLayout мы не используем для обычных блоков, но можно их синхронизировать с нашими
        super(align, hgap, vgap);
        this.blockHgap = hgap;
        this.blockVgap = vgap;
    }

    // Сеттеры для управления зазорами обычных блоков
    public void setBlockHgap(int gap) {
        this.blockHgap = gap;
    }

    public void setBlockVgap(int gap) {
        this.blockVgap = gap;
    }

    // Сеттер для сепаратора
    public void setSeparatorVgap(int gap) {
        this.separatorVgap = gap;
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
            int targetWidth = target.getSize().width;
            if (targetWidth == 0)
                targetWidth = Integer.MAX_VALUE;
            Insets insets = target.getInsets();
            int horizontalInsetsAndGap = insets.left + insets.right + (blockHgap * 2);
            int maxWidth = targetWidth - horizontalInsetsAndGap;

            Dimension dim = new Dimension(0, 0);
            int rowWidth = 0;
            int rowHeight = 0;

            int nmembers = target.getComponentCount();
            for (int i = 0; i < nmembers; i++) {
                Component m = target.getComponent(i);
                if (!m.isVisible()) {
                    continue;
                }
                // Если компонент-сепаратор, завершаем текущую строку и добавляем сепаратор как отдельную строку
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

            Container scrollPane = SwingUtilities.getAncestorOfClass(JScrollPane.class, target);
            if (scrollPane != null) {
                dim.width -= (blockHgap + 1);
            }

            return dim;
        }
    }

    // Метод для добавления строки с заданным зазором
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
            int maxWidth = target.getWidth() - (insets.left + insets.right + blockHgap * 2);
            int x = insets.left + blockHgap;
            int y = insets.top + blockVgap;
            int rowHeight = 0;
            int nmembers = target.getComponentCount();

            for (int i = 0; i < nmembers; i++) {
                Component m = target.getComponent(i);
                if (!m.isVisible())
                    continue;

                Dimension d = m.getPreferredSize();

                // Если это сепаратор
                if (m instanceof Separator) {
                    if (x > insets.left + blockHgap) {
                        // Завершаем текущую строку
                        x = insets.left + blockHgap;
                        y += rowHeight + blockVgap;
                        rowHeight = 0;
                    }
                    m.setBounds(x, y, Math.min(d.width, maxWidth), d.height);
                    // После сепаратора используем настраиваемый зазор
                    y += d.height + separatorVgap;
                    x = insets.left + blockHgap;
                    continue;
                }

                if (x + d.width > maxWidth + insets.left) {
                    // Начинаем новую строку
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
class Separator extends JPanel {
    public Separator() {
        setOpaque(false);
        setMaximumSize(new Dimension(0,0));
        setPreferredSize(new Dimension(0,0));
    }
}

