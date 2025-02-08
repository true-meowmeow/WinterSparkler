package swing.pages.home.series;

import java.awt.*;

public class ObservableCardLayout extends CardLayout {
    @Override
    public void show(Container parent, String name) {
        // Вызов кастомной логики перед переключением
        fireBeforeSwitchEvent(parent, name);
        super.show(parent, name);
    }

    private void fireBeforeSwitchEvent(Container parent, String name) {
        // Ваша логика обработки
        System.out.println("?>FDSMFSD");
    }
}