package swing.objects;

import javax.swing.*;
import java.awt.*;

public class JPanelGrid extends JPanel {


    public JPanelGrid(boolean clearBorder) {
        this();
        if (clearBorder) {
            setPreferredSize(new Dimension(0, 0));
            setMinimumSize(new Dimension(0, 0));
        }
    }

    public JPanelGrid() {
        setLayout(new GridBagLayout());
    }
}
