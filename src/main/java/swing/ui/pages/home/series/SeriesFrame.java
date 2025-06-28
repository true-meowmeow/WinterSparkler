package swing.ui.pages.home.series;

import swing.objects.core.JPanelCustom;
import swing.ui.pages.home.series.body.panels.SeriesPanel;
import swing.ui.pages.home.series.controls.ControlsPanel;

import java.awt.*;

public class SeriesFrame extends JPanelCustom {

    public SeriesFrame() {
        super(true);
        add(new ControlsPanel(), BorderLayout.NORTH);
        add(new SeriesPanel(), BorderLayout.CENTER);
    }
}