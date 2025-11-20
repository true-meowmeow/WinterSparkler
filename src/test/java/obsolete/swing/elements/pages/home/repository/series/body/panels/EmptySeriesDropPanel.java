package obsolete.swing.elements.pages.home.repository.series.body.panels;

import obsolete.swing.core.dropper.DropPanel;
import obsolete.swing.elements.pages.home.repository.series.body.droppers.DropTargetNewSeries;

public class EmptySeriesDropPanel extends DropPanel {
    public static final String PROPERTY_NAME = "_EMPTY_DROP_SERIES_PANEL_";

    public EmptySeriesDropPanel() {
        super(PROPERTY_NAME, new DropTargetNewSeries(true));
        setDimensions(ZERO, ZERO, MAX);
        //setHoverBorderEnabled(false);

        setOpaque(false);
    }
}