package swing.ui.pages.home.series.body.panels;

import swing.objects.dropper.DropPanel;
import swing.ui.pages.home.series.body.droppers.DropTargetNewSeries;

public class EmptySeriesDropPanel extends DropPanel {
    public static final String PROPERTY_NAME = "_EMPTY_DROP_SERIES_PANEL_";

    public EmptySeriesDropPanel() {
        super(PROPERTY_NAME, new DropTargetNewSeries(true));
        setDimensions(ZERO, ZERO, MAX);
        //setHoverBorderEnabled(false);

        setOpaque(false);
    }
}