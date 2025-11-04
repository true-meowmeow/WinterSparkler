package swing.elements.pages.home.repository.series.body.panels;

import core.main.config.LayoutProperties;
import swing.core.dropper.DropPanel;
import swing.elements.pages.home.repository.series.body.droppers.DropTargetNewSeries;

import javax.swing.*;

public class BottomAddSeriesPanel extends DropPanel {
    public static final String PROPERTY_NAME = "_BOTTOM_ADD_SERIES_PANEL_";

    public BottomAddSeriesPanel() {
        super(PROPERTY_NAME, new DropTargetNewSeries(true));

        setPreferredSize(MAX_INT, LayoutProperties.get().getHeightAddCollectionPanel());
        add(new JLabel(" + New Series"));

        setVisible(false);
    }
}
