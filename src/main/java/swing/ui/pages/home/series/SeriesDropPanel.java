package swing.ui.pages.home.series;

import swing.objects.dropper.DropPanel;
import swing.ui.pages.home.series.droppers.DropTargetSeries;

import java.awt.*;

public class SeriesDropPanel extends DropPanel {
    public static final String PROPERTY_NAME = "_SERIES_PANEL_";
    public SeriesDropPanel() {
        super(PROPERTY_NAME, new DropTargetSeries());
        setBackground(Color.LIGHT_GRAY);
    }
}
