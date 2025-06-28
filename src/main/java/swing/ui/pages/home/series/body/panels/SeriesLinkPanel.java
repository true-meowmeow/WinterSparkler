package swing.ui.pages.home.series.body.panels;

import swing.objects.core.Axis;
import swing.objects.core.JPanelCustom;
import swing.objects.core.PanelType;
import swing.ui.pages.home.series.body.objects.SeriesManager;

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
