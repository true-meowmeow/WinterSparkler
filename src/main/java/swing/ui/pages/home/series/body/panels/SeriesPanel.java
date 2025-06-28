package swing.ui.pages.home.series.body.panels;

import swing.objects.core.Axis;
import swing.objects.core.JPanelCustom;
import swing.objects.objects.SmoothScrollPane;

import java.awt.*;

public class SeriesPanel extends JPanelCustom {

    public SeriesPanel() {
        initSeriesFieldPanel();

        SmoothScrollPane scroller = new SmoothScrollPane(seriesFieldPanel);
        add(scroller);

        JPanelCustom bottomContainer = new JPanelCustom(Axis.Y_AX);
        bottomContainer.add(new BottomAddSeriesPanel());
        bottomContainer.add(new BottomAddSeriesGroupPanel());

        add(bottomContainer, BorderLayout.SOUTH);
    }

    JPanelCustom seriesFieldPanel = new JPanelCustom();

    private void initSeriesFieldPanel() {
        seriesFieldPanel.add(SeriesLinkPanel.getInstance(), BorderLayout.NORTH);

        EmptySeriesDropPanel emptyPanel = new EmptySeriesDropPanel();
        seriesFieldPanel.add(emptyPanel, BorderLayout.CENTER);
    }
}