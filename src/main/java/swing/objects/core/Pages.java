package swing.objects.core;

import java.awt.*;

public class Pages extends JPanelCustom {

    public Pages(PanelType type, boolean clearBorder) {
        super(type, clearBorder);
    }

    public Pages(PanelType type) {
        super(type);
    }

    public GridBagConstraints menuGridBagConstraintsX(int gridX, double weightX) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = gridX;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = weightX;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;
        return gbc;
    }
}
