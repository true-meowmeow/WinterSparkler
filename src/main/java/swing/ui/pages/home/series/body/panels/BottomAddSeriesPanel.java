package swing.ui.pages.home.series.body.panels;

import swing.objects.dropper.DropPanel;
import swing.ui.pages.home.SwingHomeVariables;
import swing.ui.pages.home.series.body.droppers.DropTargetNewSeries;

import javax.swing.*;

public class BottomAddSeriesPanel extends DropPanel implements SwingHomeVariables {
    public static final String PROPERTY_NAME = "_BOTTOM_ADD_SERIES_PANEL_";

    public BottomAddSeriesPanel() {
        super(PROPERTY_NAME, new DropTargetNewSeries(true));

        int height = HEIGHT_ADD_COLLECTION_PANEL;
        setPreferredSize(MAX_INT, height);
        add(new JLabel(" + New Series"));

        setVisible(false);
    }
}