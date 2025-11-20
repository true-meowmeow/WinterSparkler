package core.main.check;


import java.awt.*;
import javax.swing.*;

// Изменённый Separator – теперь задаёт ненулевую высоту, чтобы обеспечить разрыв строки
public class Separator extends JPanel {
    public Separator() {
        setOpaque(false);
        // Например, пусть высота сепаратора равна 10 пикселям,
        // а ширина может игнорироваться менеджером компоновки
        setPreferredSize(new Dimension(0, 10));
        setMaximumSize(new Dimension(Integer.MAX_VALUE, 10));

    }
    public Separator(boolean isHome) {
        if (isHome) {
            setPreferredSize(new Dimension(100, 100));
            setMaximumSize(new Dimension(100, 100));
            setBackground(Color.GRAY);
        }
    }


}
