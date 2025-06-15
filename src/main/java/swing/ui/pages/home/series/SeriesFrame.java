package swing.ui.pages.home.series;

import swing.objects.core.Axis;
import swing.objects.core.JPanelCustom;
import swing.objects.dropper.DropPanel;
import swing.objects.core.PanelType;
import swing.objects.objects.SmoothScrollPane;
import swing.ui.pages.home.play.view.CombinedPanel;
import swing.ui.pages.home.series.droppers.DropTargetSeries;

import javax.swing.*;
import java.awt.*;

public class SeriesFrame extends JPanelCustom {

    public SeriesFrame() {
        super(true);
        add(new ControlsPanel(), BorderLayout.NORTH);
        add(new SeriesPanel(), BorderLayout.CENTER);
    }
}