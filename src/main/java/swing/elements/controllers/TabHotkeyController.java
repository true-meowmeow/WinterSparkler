package swing.elements.controllers;

import swing.elements.Tab;
import swing.elements.TitleMenuBar;

import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;

public final class TabHotkeyController implements KeyEventDispatcher {

    private final TitleMenuBar menu;
    private boolean tabDown = false;   // флаг удержания

    public TabHotkeyController(TitleMenuBar menu) {
        this.menu = menu;
        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(this);
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent e) {

        int code = e.getKeyCode();

        // особая обработка TAB: и нажатие, и отпускание
        if (code == KeyEvent.VK_TAB) {
            // игнорируем Shift+Tab
            if (e.isShiftDown()) return false;

            if (e.getID() == KeyEvent.KEY_PRESSED) {
                if (!tabDown) {
                    menu.switchToOtherRecentTab();
                    tabDown = true;
                }
                e.consume();
                return true;
            }
            if (e.getID() == KeyEvent.KEY_RELEASED) {
                tabDown = false;    // сбрасываем флаг при отпускании
                e.consume();
                return true;
            }
            return false;
        }

        // далее — только нажатия для остальных клавиш
        if (e.getID() != KeyEvent.KEY_PRESSED) return false;
        // запрещаем срабатывание при Ctrl/Alt/Meta
        if (e.isControlDown() || e.isAltDown() || e.isMetaDown()) return false;

        char ch = e.getKeyChar();

        switch (code) {
            /* -------- навигация по вкладкам -------- */
            case KeyEvent.VK_F1 -> menu.open(Tab.HOME);
            case KeyEvent.VK_F2 -> menu.open(Tab.MANAGE);
            case KeyEvent.VK_F3 -> menu.open(Tab.GITHUB_LINK);

            /* --------- toggle settings -------- */
            case KeyEvent.VK_F12, KeyEvent.VK_BACK_QUOTE, KeyEvent.VK_DEAD_GRAVE, KeyEvent.VK_DEAD_TILDE ->
                    menu.toggleSettings();


            default -> {
                // кириллица «ё» и «Ё»
                if (ch == 'ё' || ch == 'Ё') menu.toggleSettings();      //fixme better solution will be appreciated
                else return false;                   // ничего не перехватили
            }
        }

        e.consume();          // событие обработано, дальше не идёт
        return true;
    }
}
