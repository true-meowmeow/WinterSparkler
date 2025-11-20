package obsolete.swing.elements.pages.home.repository.series;

import obsolete.swing.core.basics.JPanelCustom;
import obsolete.swing.elements.pages.home.repository.series.body.panels.SeriesPanel;

import java.awt.*;

public class SeriesFrame extends JPanelCustom {

    public SeriesFrame() {
        super(true);
        add(new SeriesPanel(), BorderLayout.CENTER);
    }
}