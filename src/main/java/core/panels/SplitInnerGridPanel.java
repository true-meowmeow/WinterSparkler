package core.panels;

import core.objects.Curves;

/**
 * Layout variant that keeps the top row split into Panels 1 and 2 with all four
 * panels visible based on the responsive breakpoints.
 */
public class SplitInnerGridPanel extends AbstractInnerGridPanel {

    private final Panel3 panel3;
    private final Panel4 panel4;
    private final Panel5 panel5;
    private final Panel6 panel6;

    public SplitInnerGridPanel() {
        this(new PanelManager());
    }

    SplitInnerGridPanel(PanelManager panelManager) {
        super(panelManager);
        this.panel3 = panelManager.getPanel3();
        this.panel4 = panelManager.getPanel4();
        this.panel5 = panelManager.getPanel5();
        this.panel6 = panelManager.getPanel6();

        add(panel3);
        add(panel4);
        add(panel5);
        add(panel6);
    }

    @Override
    protected void layoutPanels(int x, int y, int width, int height, int hTop, int hBottom) {
        int wRightTop = Curves.eval(width, Curves.RIGHT);
        int wLeftTop = Math.max(0, width - wRightTop);

        int wRightBottom = Curves.eval(width, Curves.RIGHT);
        int wLeftBottom = Math.max(0, width - wRightBottom);

        boolean hide1 = false, hide2 = false, hide4 = false;
        boolean extend2Down = false, extend3Up = false;

        if (width < breakpoints.widthHideP1P4()) {
            hide2 = true;
            hide4 = true;
        } else if (width < breakpoints.widthHideP4()) {
            hide4 = true;
        }

        if (height < breakpoints.heightP2ToP3()) {
            hide2 = true;
            hide4 = true;
            extend2Down = false;
            extend3Up = false;

            showComp(panel3, x, y, width, hTop);
            hideComp(panel4);
            hideComp(panel6);
            showComp(panel5, x, y + hTop, width, hBottom);
            return;
        } else {
            if (height < breakpoints.heightP1P2ToP3()) {
                hide1 = true;
                extend3Up = true;
                if (width < breakpoints.widthHideP2Mid() && !hide2) {
                    hide2 = true;
                }
            }
            if (height < breakpoints.heightP4ToP2()) {
                hide4 = true;
                if (!hide2) extend2Down = true;
            }
        }

        if (width < breakpoints.widthHideP1P4() && height >= breakpoints.heightP1P2ToP3()) {
            hide1 = true;
            hide2 = false;
            hide4 = true;
            extend2Down = false;
            extend3Up = false;

            wRightTop = width;
            wLeftTop = 0;
        }

        if (hide2) {
            wRightTop = 0;
            wLeftTop = width;
        }
        if (hide4 && !extend2Down) {
            wRightBottom = 0;
            wLeftBottom = width;
        }
        if (extend2Down) {
            int rightW = hide2 ? 0 : wRightTop;
            wRightBottom = rightW;
            wLeftBottom = Math.max(0, width - rightW);
        }

        if (!hide1) {
            showComp(panel3, x, y, wLeftTop, hTop);
        } else {
            hideComp(panel3);
        }

        if (!hide2) {
            int h2 = extend2Down ? (hTop + hBottom) : hTop;
            showComp(panel4, x + wLeftTop, y, wRightTop, h2);
        } else {
            hideComp(panel4);
        }

        if (extend3Up) {
            int rightOcc = (!hide2) ? wRightTop : 0;
            int wLeftTall = Math.max(0, width - rightOcc);
            showComp(panel5, x, y, wLeftTall, hTop + hBottom);
        } else {
            showComp(panel5, x, y + hTop, wLeftBottom, hBottom);
        }

        if (!hide4 && !extend2Down) {
            showComp(panel6, x + wLeftBottom, y + hTop, wRightBottom, hBottom);
        } else {
            hideComp(panel6);
        }
    }
}
