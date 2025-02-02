package swing.objects;

import javax.swing.*;
import java.awt.*;

public class JPanelBasic extends JPanel {

    FontsSwing fontsSwing = new FontsSwing();

    public JPanelBasic(boolean clearBorder) {
        if (clearBorder) {
            setPreferredSize(new Dimension(0, 0));
            setMinimumSize(new Dimension(0, 0));
        }
    }
    public JPanelBasic() {
    }
}
