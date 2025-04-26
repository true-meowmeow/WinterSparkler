package swing.objects.general;

import swing.objects.general.panel.JPanelCustom;
import swing.objects.general.panel.PanelType;

import javax.swing.*;
import java.awt.*;

public class Pages extends JPanelCustom {

    public Pages(PanelType type, boolean clearBorder) {
        super(type, clearBorder);
    }

    public Pages(PanelType type) {
        super(type);
    }

    //Grid
    //todo Переработать системы с Grid панелями
    public void setGridBagConstrains(JPanelCustom panel1, JPanelCustom panel2) {
        if (!getLayout().getClass().getName().contains("GridBagLayout")) {
            System.out.println("Незаконная попытка установить GridBagConstraints");
            return;
        }
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.weighty = 0;
        gbc.gridy = 0;
        this.add(panel1, gbc);
        gbc.gridy = 1;
        this.add(panel2, gbc);
        gbc.gridy = 2;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;
        this.add(Box.createGlue(), gbc);
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
