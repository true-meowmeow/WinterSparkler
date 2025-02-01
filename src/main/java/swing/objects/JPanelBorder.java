package swing.objects;

import javax.swing.*;
import java.awt.*;

import static core.Methods.createBorder;

public class JPanelBorder extends JPanel {

    public JPanelBorder(boolean clearBorder) {
        this();
        if (clearBorder) {
            setPreferredSize(new Dimension(0, 0));
            setMinimumSize(new Dimension(0, 0));
        }
    }

    public JPanelBorder() {
        setLayout(new BorderLayout());
        createBorder(this);
    }

}