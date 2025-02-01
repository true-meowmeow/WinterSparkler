package core;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Methods {

    public static void createBorder(JPanel jPanel) {
        Random random = new Random();
        Color randomColor;

        do {
            int r = random.nextInt(156) + 100; // 100–255
            int g = random.nextInt(156) + 100;
            int b = random.nextInt(156) + 100;

            randomColor = new Color(r, g, b);
        } while (isGrayish(randomColor)); // Проверяем, не получился ли серый

        jPanel.setBorder(BorderFactory.createLineBorder(randomColor, 1));
    }

    private static boolean isGrayish(Color color) {
        int r = color.getRed();
        int g = color.getGreen();
        int b = color.getBlue();

        // Разница между каналами должна быть значительной (иначе цвет серый)
        return (Math.abs(r - g) < 50) && (Math.abs(r - b) < 50) && (Math.abs(g - b) < 50);
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
