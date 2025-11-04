package swing.elements.pages.home.repository.series.body.panels;

import swing.core.basics.Axis;
import swing.core.basics.JPanelCustom;
import swing.core.objects.SmoothScrollPane;

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