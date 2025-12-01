package core.panels.cards.manageCard;

import core.config.MergeProperties;
import core.layouts.AbstractInnerGridPanel;
import core.objects.Curves;

/**
 * Layout variant where the top portion is merged and represented with Panel 5.
 */
public class ManageCol3 extends AbstractInnerGridPanel {

    private final MergeProperties mergeProperties = MergeProperties.get();

    private final ManageManagePanel managePanel = new ManageManagePanel();
    private final ManagePlayPanel playPanel = new ManagePlayPanel();
    private final ManageQueuePanel queuePanel = new ManageQueuePanel();

    ManageCol3() {
        add(managePanel);
        add(playPanel);
        add(queuePanel);
    }

    @Override
    protected void layoutPanels(int x, int y, int width, int height, int hTop, int hBottom) {
        if (hTop < mergeProperties.minP1Height()) {
            showComp(managePanel, x, y, width, height);
            hideComp(playPanel);
            hideComp(queuePanel);
            return;
        }

        showComp(managePanel, x, y, width, hTop);

        int wRight = Curves.eval(width, Curves.RIGHT);
        int wLeft = Math.max(0, width - wRight);

        if (wLeft < mergeProperties.minP3Width()) {
            hideComp(playPanel);
            showComp(queuePanel, x, y + hTop, width, hBottom);
            return;
        }

        showComp(queuePanel, x, y + hTop, wLeft, hBottom);
        showComp(playPanel, x + wLeft, y + hTop, wRight, hBottom);
    }
}
