package obsolete.swing.elements.pages.home.playground.obsolete.play.view.controllers;

import obsolete.swing.core.dropper.DropPanel;
import obsolete.swing.core.dropper.DropPanelRegistry;
import obsolete.core.contentManager.TransferableManageData;
import obsolete.swing.elements.pages.home.repository.collections.body.panels.BottomAddCollectionGroupPanel;
import obsolete.swing.elements.pages.home.repository.collections.body.panels.BottomAddCollectionPanel;
import obsolete.swing.elements.pages.home.repository.collections.body.panels.EmptyCollectionDropPanel;
import obsolete.swing.elements.pages.home.playground.obsolete.play.view.selection.SelectablePanel;
import obsolete.swing.elements.pages.home.repository.series.body.panels.BottomAddSeriesGroupPanel;
import obsolete.swing.elements.pages.home.repository.series.body.panels.BottomAddSeriesPanel;
import obsolete.swing.elements.pages.home.repository.series.body.panels.EmptySeriesDropPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static obsolete.swing.elements.pages.home.playground.obsolete.play.view.ManagePanel.getInstance;

public class MovementHandler extends MouseAdapter {


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

                dropAction(targetPanel);

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

    private void dropAction(DropPanel targetPanel) {    ///  Drop action from manage panel
        TransferableManageData transferableManageData = new TransferableManageData();

        targetPanel.dropTargetPanel.dropAction(transferableManageData);
        targetPanel.dropTargetPanel.dropAction();
    }

    /* ----------------------------------------------------------- */
    private void showBottomPanelIfNeeded() {                            ///  Current state is always true
        DropPanel bottom = DropPanelRegistry.get(BottomAddCollectionGroupPanel.PROPERTY_NAME);
        DropPanel bottom2 = DropPanelRegistry.get(BottomAddCollectionPanel.PROPERTY_NAME);

        DropPanel bottomSeries = DropPanelRegistry.get(BottomAddSeriesPanel.PROPERTY_NAME);
        DropPanel bottomSeriesGroup = DropPanelRegistry.get(BottomAddSeriesGroupPanel.PROPERTY_NAME);

        DropPanel empty = DropPanelRegistry.get(EmptyCollectionDropPanel.PROPERTY_NAME);
        DropPanel emptySeries = DropPanelRegistry.get(EmptySeriesDropPanel.PROPERTY_NAME);

        int emptyHeight = (empty != null) ? empty.getHeight() : 0;

        //if (emptyHeight < 200) {
        if (bottom != null && !bottom.isVisible()) {
            bottom.setVisible(true);
            Container p = bottom.getParent();
            if (p != null) {
                p.revalidate();
                p.repaint();
            }
        }
        //}

        if (bottom2 != null && !bottom2.isVisible()) {
            bottom2.setVisible(true);
            Container p = bottom2.getParent();
            if (p != null) {
                p.revalidate();
                p.repaint();
            }
        }


        if (bottomSeriesGroup != null && !bottomSeriesGroup.isVisible()) {
            bottomSeriesGroup.setVisible(true);
            Container p = bottomSeriesGroup.getParent();
            if (p != null) {
                p.revalidate();
                p.repaint();
            }
        }

        if (bottomSeries != null && !bottomSeries.isVisible()) {
            bottomSeries.setVisible(true);
            Container p = bottomSeries.getParent();
            if (p != null) {
                p.revalidate();
                p.repaint();
            }
        }
    }

    private void hideBottomPanel() {
        DropPanel bottom = DropPanelRegistry.get(BottomAddCollectionGroupPanel.PROPERTY_NAME);
        DropPanel bottom2 = DropPanelRegistry.get(BottomAddCollectionPanel.PROPERTY_NAME);

        DropPanel bottomSeries = DropPanelRegistry.get(BottomAddSeriesPanel.PROPERTY_NAME);
        DropPanel bottomSeriesGroup = DropPanelRegistry.get(BottomAddSeriesGroupPanel.PROPERTY_NAME);

        if (bottom != null && bottom.isVisible()) {
            bottom.setVisible(false);
            Container p = bottom.getParent();
            if (p != null) {
                p.revalidate();
                p.repaint();
            }
        }

        if (bottom2 != null && bottom2.isVisible()) {
            bottom2.setVisible(false);
            Container p = bottom2.getParent();
            if (p != null) {
                p.revalidate();
                p.repaint();
            }
        }


        if (bottomSeriesGroup != null && bottomSeriesGroup.isVisible()) {
            bottomSeriesGroup.setVisible(false);
            Container p = bottomSeriesGroup.getParent();
            if (p != null) {
                p.revalidate();
                p.repaint();
            }
        }

        if (bottomSeries != null && bottomSeries.isVisible()) {
            bottomSeries.setVisible(false);
            Container p = bottomSeries.getParent();
            if (p != null) {
                p.revalidate();
                p.repaint();
            }
        }
    }
}
