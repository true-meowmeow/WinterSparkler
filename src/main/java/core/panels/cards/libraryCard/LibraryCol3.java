package core.panels.cards.libraryCard;

import core.layouts.AbstractInnerGridPanel;
import core.objects.Curves;
import core.panels.obsolete.*;

/**
 * Layout variant that keeps the top row split into Panels 1 and 2 with all four
 * panels visible based on the responsive breakpoints.
 */
public class LibraryCol3 extends AbstractInnerGridPanel {

    private final LibraryPlaylistPanel playlistPanel = new LibraryPlaylistPanel();
    private final LibraryQueuePanel queuePanel = new LibraryQueuePanel();
    private final LibraryPlayPanel playPanel = new LibraryPlayPanel();
    private final LibraryCoverPanel coverPanel = new LibraryCoverPanel();

    LibraryCol3() {

        add(playlistPanel);
        add(queuePanel);
        add(playPanel);
        add(coverPanel);
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

            showComp(playlistPanel, x, y, width, hTop);
            hideComp(queuePanel);
            hideComp(coverPanel);
            showComp(playPanel, x, y + hTop, width, hBottom);
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
            showComp(playlistPanel, x, y, wLeftTop, hTop);
        } else {
            hideComp(playlistPanel);
        }

        if (!hide2) {
            int h2 = extend2Down ? (hTop + hBottom) : hTop;
            showComp(queuePanel, x + wLeftTop, y, wRightTop, h2);
        } else {
            hideComp(queuePanel);
        }

        if (extend3Up) {
            int rightOcc = (!hide2) ? wRightTop : 0;
            int wLeftTall = Math.max(0, width - rightOcc);
            showComp(playPanel, x, y, wLeftTall, hTop + hBottom);
        } else {
            showComp(playPanel, x, y + hTop, wLeftBottom, hBottom);
        }

        if (!hide4 && !extend2Down) {
            showComp(coverPanel, x + wLeftBottom, y + hTop, wRightBottom, hBottom);
        } else {
            hideComp(coverPanel);
        }
    }
}
