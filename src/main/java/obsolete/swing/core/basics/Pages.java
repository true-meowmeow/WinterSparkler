package obsolete.swing.core.basics;

import java.awt.*;

public class Pages extends JPanelCustom {

    public Pages(PanelType type, boolean clearBorder) {
        super(type, clearBorder);
    }

    public Pages(PanelType type) {
        super(type);
    }

    public GridBagConstraints menuGridBagConstraintsY(int gridY, double weightY) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = gridY;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 1;
        gbc.weighty = weightY;
        gbc.fill = GridBagConstraints.BOTH;
        return gbc;
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

