package swing.objects.movement;

import swing.objects.dropper.DropPanel;
import swing.objects.dropper.DropPanelRegistry;
import swing.ui.pages.home.SwingHomeVariables;
import swing.ui.pages.home.play.SelectablePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static swing.ui.pages.home.play.ManagePanel.FolderSystemPanelInstance;

public class MovementHandler extends MouseAdapter implements SwingHomeVariables {

    private static final String NAME_EMPTY_PANEL = "_EMPTY_SLOT_";
    private static final String NAME_BOTTOM_PANEL = "_EMPTY_SLOT_2";

    private final SelectablePanel panel;
    private Point pressPoint = null;
    private boolean moved = false;
    private boolean initialCtrl = false;
    private boolean initialShift = false;
    private boolean initialAlt = false;
    private boolean pendingShiftClick = false;
    private boolean dragStartedWithShift = false;
    private static DropPanel lastHoverPanel = null;

    public MovementHandler(SelectablePanel panel) {
        this.panel = panel;
    }

    /* ----------------------------------------------------------- */
    @Override
    public void mousePressed(MouseEvent e) {
        e.consume();

        initialAlt = (e.getModifiersEx() & MouseEvent.ALT_DOWN_MASK) != 0;
        initialShift = (e.getModifiersEx() & MouseEvent.SHIFT_DOWN_MASK) != 0;

        if (initialAlt && !initialShift) {        // Alt — снимаем выделение
            panel.setSelected(false);
            return;
        }

        pressPoint = e.getPoint();
        moved = false;
        initialCtrl = (e.getModifiersEx() & MouseEvent.CTRL_DOWN_MASK) != 0;
        dragStartedWithShift = initialShift;
        pendingShiftClick = initialShift;

        /* —–– выделения —–– */
        if (initialShift) {
            pendingShiftClick = true;
            if (FolderSystemPanelInstance().anchorIndex == -1) {
                FolderSystemPanelInstance().clearSelection();
                panel.setSelected(true);
                FolderSystemPanelInstance().anchorIndex = panel.getIndex();
            }
        } else if (initialCtrl) {
            FolderSystemPanelInstance().handlePanelClick(panel, e);
        } else {
            if (!panel.isSelected()) {
                FolderSystemPanelInstance().clearSelection();
                panel.setSelected(true);
                FolderSystemPanelInstance().anchorIndex = panel.getIndex();
            } else {
                FolderSystemPanelInstance().anchorIndex = panel.getIndex();
            }
        }
    }

    /* ----------------------------------------------------------- */
    @Override
    public void mouseDragged(MouseEvent e) {
        if (initialAlt) return;

        Point current = e.getPoint();
        if (!moved && pressPoint != null) {
            double dx = current.x - pressPoint.x;
            double dy = current.y - pressPoint.y;
            if (Math.hypot(dx, dy) > 5) {                    // старт drag
                moved = true;

                if (!initialCtrl && !initialShift && !panel.isSelected()) {
                    FolderSystemPanelInstance().clearSelection();
                    panel.setSelected(true);
                    FolderSystemPanelInstance().anchorIndex = panel.getIndex();
                }
                if (initialShift && pendingShiftClick && !panel.isSelected()) {
                    FolderSystemPanelInstance().handlePanelClick(panel, e);
                    pendingShiftClick = false;
                } else if (initialShift && pendingShiftClick) {
                    pendingShiftClick = false;
                }
            }
        }

        /* начало группового drag’a */
        if (moved && !FolderSystemPanelInstance().draggingGroup && panel.isSelected()) {
            FolderSystemPanelInstance().draggingGroup = true;
            FolderSystemPanelInstance().groupDragStart = SwingUtilities.convertPoint(panel, pressPoint, FolderSystemPanelInstance().getGlassPane());

            int cnt = (int) FolderSystemPanelInstance().panels.stream().filter(SelectablePanel::isSelected).count();
            FolderSystemPanelInstance().dragGlassPane.setGhostText(cnt > 1 ? "Dragging " + cnt + " objects" : "Dragging object");
            FolderSystemPanelInstance().dragGlassPane.setGhostLocation(new Point(FolderSystemPanelInstance().groupDragStart.x + 10, FolderSystemPanelInstance().groupDragStart.y + 10));
            FolderSystemPanelInstance().dragGlassPane.setVisible(true);

            /* ── показываем панель «+ Add collection», если нужно ── */
            showBottomPanelIfNeeded();
        }

        /* перемещение «призрака» и подсветка DropPanel’ов */
        if (FolderSystemPanelInstance().draggingGroup) {
            Point glassPt = SwingUtilities.convertPoint(panel, e.getPoint(), FolderSystemPanelInstance().getGlassPane());
            FolderSystemPanelInstance().dragGlassPane.setGhostLocation(new Point(glassPt.x + 10, glassPt.y + 10));

            Point dropScreenPoint = e.getLocationOnScreen();
            DropPanel hoverPanel = null;
            for (DropPanel dp : DropPanelRegistry.getAll()) {
                if (!dp.isShowing()) continue;                // ← важно, иначе IllegalComponentStateException
                Rectangle r = new Rectangle(dp.getLocationOnScreen(), dp.getSize());
                if (r.contains(dropScreenPoint)) {
                    hoverPanel = dp;
                    break;
                }
            }
            if (hoverPanel != lastHoverPanel) {
                if (lastHoverPanel != null) lastHoverPanel.setHovered(false);
                if (hoverPanel != null) hoverPanel.setHovered(true);
                lastHoverPanel = hoverPanel;
            }
        }
    }

    /* ----------------------------------------------------------- */
    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getClickCount() == 2) {
            panel.action();
            return;
        }

        if (FolderSystemPanelInstance().draggingGroup) {
            Point dropScreenPoint = e.getLocationOnScreen();
            DropPanel targetPanel = null;
            for (DropPanel dp : DropPanelRegistry.getAll()) {
                if (!dp.isShowing()) continue;                // безопасно, панель могла быть скрыта
                try {
                    Rectangle panelBounds = new Rectangle(dp.getLocationOnScreen(), dp.getSize());
                    if (panelBounds.contains(dropScreenPoint)) {
                        targetPanel = dp;
                        break;
                    }
                } catch (IllegalComponentStateException ignored) {
                }
            }

            if (targetPanel != null) {
                /* сбор выбранных элементов */
                List<SelectablePanel> selectedItems = new ArrayList<>();
                for (SelectablePanel sp : FolderSystemPanelInstance().panels)
                    if (sp.isSelected()) selectedItems.add(sp);
                Collections.sort(selectedItems, Comparator.comparingLong(SelectablePanel::getSelectionOrder));

                // TODO: dropAction(TransferableData …)
                targetPanel.dropTargetPanel.dropAction(selectedItems);
                targetPanel.dropTargetPanel.dropAction();
                targetPanel.dropTargetPanel.dropAction("fdsfsd");
            } else {
                System.out.println("Drop не произошёл ни в одной из зарегистрированных панелей");
            }

            FolderSystemPanelInstance().clearSelection();
            FolderSystemPanelInstance().anchorIndex = -1;
            FolderSystemPanelInstance().draggingGroup = false;
            FolderSystemPanelInstance().groupDragStart = null;
            FolderSystemPanelInstance().dragGlassPane.clearGhost();

            /* спрятать нижнюю панель */
            hideBottomPanel();
            if (lastHoverPanel != null) {
                lastHoverPanel.setHovered(false);
                lastHoverPanel = null;
            }
        } else if (!moved) {                  // обычный клик
            if (initialShift && pendingShiftClick) {
                FolderSystemPanelInstance().handlePanelClick(panel, e);
                pendingShiftClick = false;
            } else if (!initialCtrl && !initialShift && !initialAlt) {
                if (panel.isSelected()) {
                    FolderSystemPanelInstance().clearSelection();
                    panel.setSelected(true);
                    FolderSystemPanelInstance().anchorIndex = panel.getIndex();
                }
            }
        }
    }

    /* ----------------------------------------------------------- */
    private void showBottomPanelIfNeeded() {
        DropPanel bottom = DropPanelRegistry.get(NAME_BOTTOM_PANEL);
        DropPanel empty = DropPanelRegistry.get(NAME_EMPTY_PANEL);

        if (bottom == null) return;
        int emptyHeight = (empty != null) ? empty.getHeight() : 0;

        if (emptyHeight < 200) {
            if (!bottom.isVisible()) {
                bottom.setVisible(true);
                Container p = bottom.getParent();
                if (p != null) {
                    p.revalidate();
                    p.repaint();
                }
            }
        }
    }

    private void hideBottomPanel() {
        DropPanel bottom = DropPanelRegistry.get(NAME_BOTTOM_PANEL);
        if (bottom != null && bottom.isVisible()) {
            bottom.setVisible(false);
            Container p = bottom.getParent();
            if (p != null) {
                p.revalidate();
                p.repaint();
            }
        }
    }
}
