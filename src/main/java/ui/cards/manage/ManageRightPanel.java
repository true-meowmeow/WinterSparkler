package ui.cards.manage;

import config.MergeProperties;
import ui.layout.AbstractInnerGridPanel;
import ui.layout.LayoutCurves;

/**
 * Layout variant where the top portion is merged and represented with Panel 5.
 */
public class ManageRightPanel extends AbstractInnerGridPanel {

    private final MergeProperties mergeProperties = MergeProperties.get();

    private ManagePanels.ManageExplorerPanel explorerPanel;
    private ManagePanels.ManagePlayPanel queuePanel;
    private ManagePanels.ManageQueuePanel playPanel;

    ManageRightPanel(ManagePanels managePanelsManager) {
        this.explorerPanel = managePanelsManager.getExplorerPanel();
        this.queuePanel = managePanelsManager.getPlayPanel();
        this.playPanel = managePanelsManager.getQueuePanel();


        add(explorerPanel);
        add(playPanel);
        add(queuePanel);
    }

    @Override
    protected void layoutPanels(int x, int y, int width, int height, int hTop, int hBottom) {
        if (hTop < mergeProperties.minP1Height()) {
            showComp(explorerPanel, x, y, width, height);
            hideComp(playPanel);
            hideComp(queuePanel);
            return;
        }

        showComp(explorerPanel, x, y, width, hTop);

        int wRight = LayoutCurves.eval(width, LayoutCurves.RIGHT);
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
