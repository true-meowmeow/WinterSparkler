package swing.objects;

import javax.swing.*;
import java.awt.*;

import static core.Methods.createBorder;

public class JPanelFlow extends JPanel {

    public JPanelFlow(boolean clearBorder) {
        this();
        if (clearBorder) {
            setPreferredSize(new Dimension(0, 0));
            setMinimumSize(new Dimension(0, 0));
        }
    }

    public JPanelFlow() {
        setLayout(new FlowLayout());
        createBorder(this);
    }
}
