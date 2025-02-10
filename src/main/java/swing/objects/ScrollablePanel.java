package swing.objects;

import com.formdev.flatlaf.util.UIScale;

import javax.swing.*;
import java.awt.*;

public class ScrollablePanel
        extends JPanel
        implements Scrollable
{
    @Override
    public Dimension getPreferredScrollableViewportSize() {
        return new Dimension( getPreferredSize().width, UIScale.scale( 400 ) );
    }

    @Override
    public int getScrollableUnitIncrement( Rectangle visibleRect, int orientation, int direction ) {
        return UIScale.scale( 50 );
    }

    @Override
    public int getScrollableBlockIncrement( Rectangle visibleRect, int orientation, int direction ) {
        return (orientation == SwingConstants.VERTICAL) ? visibleRect.height : visibleRect.width;
    }

    @Override
    public boolean getScrollableTracksViewportWidth() {
        return true;
    }

    @Override
    public boolean getScrollableTracksViewportHeight() {
        return false;
    }
}
