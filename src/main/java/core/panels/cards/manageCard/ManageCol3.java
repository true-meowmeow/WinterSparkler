package core.panels.cards.manageCard;

import core.config.MergeProperties;
import core.layouts.AbstractInnerGridPanel;
import core.objects.Curves;
import core.panels.obsolete.Panel4;
import core.panels.obsolete.Panel5;
import core.panels.obsolete.Panel6;
import core.panels.obsolete.PanelManager;

/**
 * Layout variant where the top portion is merged and represented with Panel 5.
 */
public class ManageCol3 extends AbstractInnerGridPanel {

    private final MergeProperties mergeProperties = MergeProperties.get();
    private final Panel6 panel6;
    private final Panel4 panel4;
    private final Panel5 panel5;

    public ManageCol3() {
        this(new PanelManager());
    }

    ManageCol3(PanelManager panelManager) {
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
