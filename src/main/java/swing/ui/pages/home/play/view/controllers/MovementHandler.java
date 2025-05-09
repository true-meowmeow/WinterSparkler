package swing.ui.pages.home.play.view.controllers;

import swing.objects.dropper.DropPanel;
import swing.objects.dropper.DropPanelRegistry;
import swing.ui.pages.home.SwingHomeVariables;
import swing.ui.pages.home.play.view.SelectablePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static swing.ui.pages.home.play.view.ManagePanel.getInstance;

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
    //private boolean dragStartedWithShift = false;
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
        //dragStartedWithShift = initialShift;
        pendingShiftClick = initialShift;

        /* —–– выделения —–– */
        if (initialShift) {
            pendingShiftClick = true;
            if (getInstance().manageController.getAnchorIndex() == -1) {
                getInstance().manageController.clearSelection();
                panel.setSelected(true);
                getInstance().manageController.setAnchorIndex(panel.getIndex());
            }
        } else if (initialCtrl) {
            getInstance().manageController.handlePanelClick(panel, e);
        } else {
            if (!panel.isSelected()) {
                getInstance().manageController.clearSelection();
                panel.setSelected(true);
                getInstance().manageController.setAnchorIndex(panel.getIndex());
            } else {
                getInstance().manageController.setAnchorIndex(panel.getIndex());
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
                    getInstance().manageController.clearSelection();
                    panel.setSelected(true);
                    getInstance().manageController.setAnchorIndex(panel.getIndex());
                }
                if (initialShift && pendingShiftClick && !panel.isSelected()) {
                    getInstance().manageController.handlePanelClick(panel, e);
                    pendingShiftClick = false;
                } else if (initialShift && pendingShiftClick) {
                    pendingShiftClick = false;
                }
            }
        }

        /* начало группового drag’a */
        if (moved && !getInstance().draggingGroup && panel.isSelected()) {
            getInstance().draggingGroup = true;
            getInstance().groupDragStart = SwingUtilities.convertPoint(panel, pressPoint, getInstance().glassPane);

            int cnt = (int) getInstance().manageController.panels.stream().filter(SelectablePanel::isSelected).count();
            getInstance().dragGlassPane.configure(cnt, getInstance().groupDragStart);

            /* ── показываем панель «+ Add collection», если нужно ── */
            showBottomPanelIfNeeded();
        }

        /* перемещение «призрака» и подсветка DropPanel’ов */
        if (getInstance().draggingGroup) {
            Point glassPt = SwingUtilities.convertPoint(panel, e.getPoint(), getInstance().glassPane);
            getInstance().dragGlassPane.setGhostLocation(new Point(glassPt.x + 10, glassPt.y + 10));

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

        if (getInstance().draggingGroup) {
            Point dropScreenPoint = e.getLocationOnScreen();
            DropPanel targetPanel = null;
            for (DropPanel dp : DropPanelRegistry.getAll()) {    /// Перебирает все панели дропа
                if (!dp.isShowing()) continue;
                try {
                    Rectangle panelBounds = new Rectangle(dp.getLocationOnScreen(), dp.getSize());
                    if (panelBounds.contains(dropScreenPoint)) {    ///  При совпадении по координатам ставит объект
                        targetPanel = dp;
                        break;
                    }
                } catch (IllegalComponentStateException ignored) {
                }
            }

            if (targetPanel != null) {
                /* сбор выбранных элементов */
                List<SelectablePanel> selectedItems = new ArrayList<>();
                for (SelectablePanel sp : getInstance().manageController.panels)
                    if (sp.isSelected()) selectedItems.add(sp);
                Collections.sort(selectedItems, Comparator.comparingLong(SelectablePanel::getSelectionOrder));

                /// Drop action
                targetPanel.dropTargetPanel.dropAction(selectedItems);
                targetPanel.dropTargetPanel.dropAction();

                getInstance().manageController.clearSelection();
                getInstance().manageController.clearAnchorIndex();
            } else {
                System.out.println("Drop не произошёл ни в одной из зарегистрированных панелей");
            }

            getInstance().draggingGroup = false;
            getInstance().groupDragStart = null;
            getInstance().dragGlassPane.clearGhost();


            /* спрятать нижнюю панель */
            hideBottomPanel();
            if (lastHoverPanel != null) {
                lastHoverPanel.setHovered(false);
                lastHoverPanel = null;
            }
        } else if (!moved) {                  // обычный клик
            if (initialShift && pendingShiftClick) {
                getInstance().manageController.handlePanelClick(panel, e);
                pendingShiftClick = false;
            } else if (!initialCtrl && !initialShift && !initialAlt) {
                if (panel.isSelected()) {
                    getInstance().manageController.clearSelection();
                    panel.setSelected(true);
                    getInstance().manageController.setAnchorIndex(panel.getIndex());
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
