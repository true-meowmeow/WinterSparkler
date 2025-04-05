package swing.objects.selection;

import javax.swing.*;
import java.awt.*;

public class WrapScrollablePanel extends JPanel implements Scrollable {

    public WrapScrollablePanel(LayoutManager layout) {
        super(layout);
        setOpaque(false);
    }

    @Override
    public Dimension getPreferredScrollableViewportSize() {
        return getPreferredSize();
    }

    // Определяет шаг прокрутки (например, 10 пикселей)
    @Override
    public int getScrollableUnitIncrement(Rectangle visibleRect, int orientation, int direction) {
        return 10;
    }

    // Определяет блок прокрутки – например, высота видимой области за вычетом 10 пикселей
    @Override
    public int getScrollableBlockIncrement(Rectangle visibleRect, int orientation, int direction) {
        if (orientation == SwingConstants.VERTICAL) {
            return visibleRect.height - 10;
        } else {
            return visibleRect.width - 10;
        }
    }

    // Заставляем растягиваться по ширине, чтобы WrapLayout мог работать корректно
    @Override
    public boolean getScrollableTracksViewportWidth() {
        return true;
    }

    @Override
    public boolean getScrollableTracksViewportHeight() {
        return false;
    }
}
