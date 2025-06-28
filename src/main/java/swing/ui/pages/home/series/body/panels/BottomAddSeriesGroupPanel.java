package swing.ui.pages.home.series.body.panels;

import swing.objects.dropper.DropPanel;
import swing.ui.pages.home.SwingHomeVariables;
import swing.ui.pages.home.series.body.droppers.DropTargetNewSeries;

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