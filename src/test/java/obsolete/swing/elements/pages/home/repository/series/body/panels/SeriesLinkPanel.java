package obsolete.swing.elements.pages.home.repository.series.body.panels;

import obsolete.swing.core.basics.Axis;
import obsolete.swing.core.basics.JPanelCustom;
import obsolete.swing.core.basics.PanelType;
import obsolete.swing.elements.pages.home.repository.series.body.objects.SeriesManager;

public class SeriesLinkPanel extends JPanelCustom {

    private static final SeriesLinkPanel INSTANCE = new SeriesLinkPanel();

    private SeriesManager seriesManager;

    private SeriesLinkPanel() {
        super(PanelType.BOX, Axis.Y_AX);

        //seriesManager = new SeriesManager(this);
    }

    public static SeriesLinkPanel getInstance() {
        return INSTANCE;
    }


    public void resequence() {
        //for (int i = 0; i < seriesDropPanel.getObjects().size(); i++)
        //    seriesDropPanel.getObjects().get(i).setPositionList(i);
    }

    public void refreshUI() {
        removeAll();
        //seriesDropPanel.getObjects().sort(Comparator.comparingInt(CollectionObject::getPositionList));
        //seriesDropPanel.getObjects().forEach(this::add);
        revalidate();
        repaint();
    }

    public SeriesManager getSeriesManager() {
        return seriesManager;
    }
}
