package swing.elements.pages.home.repository.series.body.panels;

import swing.core.dropper.DropPanel;
import swing.elements.pages.home.SwingHomeVariables;
import swing.elements.pages.home.repository.series.body.droppers.DropTargetNewSeries;

import javax.swing.*;

public class BottomAddSeriesGroupPanel extends DropPanel implements SwingHomeVariables {
    public static final String PROPERTY_NAME = "_BOTTOM_ADD_SERIES_GROUP_PANEL_";

    public BottomAddSeriesGroupPanel() {
        super(PROPERTY_NAME, new DropTargetNewSeries(false));

        int height = HEIGHT_ADD_COLLECTION_PANEL;
        setPreferredSize(MAX_INT, height);
        add(new JLabel(" + New group of series"));

        setVisible(false);
    }
}