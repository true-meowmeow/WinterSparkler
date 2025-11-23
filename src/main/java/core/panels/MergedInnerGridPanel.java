package core.panels;

import core.config.MergeProperties;
import core.objects.Curves;

/**
 * Layout variant where the top portion is merged and represented with Panel 5.
 */
public class MergedInnerGridPanel extends AbstractInnerGridPanel {

    private final MergeProperties mergeProperties = MergeProperties.get();
    private final Panel6 panel6;
    private final Panel4 panel4;
    private final Panel5 panel5;

    public MergedInnerGridPanel() {
        this(new PanelManager());
    }

    MergedInnerGridPanel(PanelManager panelManager) {
        super(panelManager);
        this.panel6 = panelManager.getPanel6();
        this.panel4 = panelManager.getPanel4();
        this.panel5 = panelManager.getPanel5();

        add(panel6);
        add(panel4);
        add(panel5);
    }

    @Override
    protected void layoutPanels(int x, int y, int width, int height, int hTop, int hBottom) {
        if (hTop < mergeProperties.minP1Height()) {
            showComp(panel6, x, y, width, height);
            hideComp(panel4);
            hideComp(panel5);
            return;
        }

        showComp(panel6, x, y, width, hTop);

        int wRight = Curves.eval(width, Curves.RIGHT);
        int wLeft = Math.max(0, width - wRight);

        if (wLeft < mergeProperties.minP3Width()) {
            hideComp(panel4);
            showComp(panel5, x, y + hTop, width, hBottom);
            return;
        }

        showComp(panel5, x, y + hTop, wLeft, hBottom);
        showComp(panel4, x + wLeft, y + hTop, wRight, hBottom);
    }
}
