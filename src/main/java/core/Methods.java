package core;

import javax.swing.*;
import java.awt.*;

public class Methods {

    public static void arePageWeightsValid(String page, double... weights) {
        double sum = 0;
        for (double weight : weights) {
            sum += weight;
        }
        if (sum != 1) {
            System.out.println("weights are unequal at " + page);
            System.exit(1);
        }
    }

    public static JPanel createVerticalPanel(int height) {
        JPanel panel = new JPanel(/*new FlowLayout(FlowLayout.CENTER)*/);   //Вроде бесполезно
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(Box.createVerticalStrut(height));

        //panel.add(new Button("fdsfds"));
        return panel;
    }


    public static GridBagConstraints newGridBagConstraintsX(int gridX, double weightX) {
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
