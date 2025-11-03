package swing.elements.pages.home.repository.series;

import swing.core.basics.JPanelCustom;
import swing.elements.pages.home.repository.series.body.panels.SeriesPanel;

import java.awt.*;

public class SeriesFrame extends JPanelCustom {

    public SeriesFrame() {
        super(true);
        add(new SeriesPanel(), BorderLayout.CENTER);
    }
}