package obsolete.swing.elements.pages.home.repository.series.body.panels;

import obsolete.core.main.config.LayoutProperties;
import obsolete.swing.core.dropper.DropPanel;
import obsolete.swing.elements.pages.home.repository.series.body.droppers.DropTargetNewSeries;

import javax.swing.*;

public class BottomAddSeriesGroupPanel extends DropPanel {
    public static final String PROPERTY_NAME = "_BOTTOM_ADD_SERIES_GROUP_PANEL_";

    public BottomAddSeriesGroupPanel() {
        super(PROPERTY_NAME, new DropTargetNewSeries(false));

        setPreferredSize(MAX_INT, LayoutProperties.get().getHeightAddCollectionPanel());
        add(new JLabel(" + New group of series"));

        setVisible(false);
    }
}
