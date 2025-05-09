package swing.ui.pages.home.play.view.controllers;

import swing.objects.objects.PathManager;
import swing.ui.pages.home.play.view.ManagePanel;
import swing.ui.pages.home.play.view.selection.SelectablePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

public class KeyBindingConfigurator {
    public KeyBindingConfigurator(ManagePanel parent) {

        // установим glassPane для ghost-эффекта
        Window window = SwingUtilities.getWindowAncestor(parent);
        if (window instanceof JFrame) {
            ((JFrame) window).setGlassPane(parent.dragGlassPane);
        }

        JRootPane root = SwingUtilities.getRootPane(parent);
        if (root == null) return;
        parent.setGlassPane(root.getGlassPane());

        InputMap im = root.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap am = root.getActionMap();

        // Home / Back навигация
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "goHome");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_HOME, 0), "goHome");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_BACK_SPACE, 0), "goBack");
        am.put("goHome", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PathManager.getInstance().goToHome();
            }
        });
        am.put("goBack", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PathManager.getInstance().goToParentDirectory();
            }
        });

        // очистка выделения
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_D, InputEvent.CTRL_DOWN_MASK), "clearSel");
        am.put("clearSel", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                parent.manageController.clearSelection();
                parent.manageController.clearAnchorIndex();
            }
        });

        // Ctrl+A — выбрать только файлы
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_DOWN_MASK), "selectNotFolder");
        am.put("selectNotFolder", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int first = -1;
                for (SelectablePanel sp : parent.manageController.panels) {
                    if (!sp.getIsFolder()) {
                        sp.setSelected(true);
                        if (first == -1) first = sp.getIndex();
                    } else {
                        sp.setSelected(false);
                    }
                }
                parent.manageController.setAnchorIndex(first);
            }
        });

        // Ctrl+Shift+A — выбрать всё
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_DOWN_MASK | InputEvent.SHIFT_DOWN_MASK), "selectAll");
        am.put("selectAll", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (SelectablePanel sp : parent.manageController.panels) sp.setSelected(true);
                parent.manageController.setAnchorIndex(0);
            }
        });
    }
}
