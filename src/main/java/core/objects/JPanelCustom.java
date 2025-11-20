package core.objects;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class JPanelCustom extends JPanel {


    public JPanelCustom() {
        super(new BorderLayout());
    }
    //for testing ->
    public void createBorder() {
        createBorder(this);
    }

    public void createBorder(JPanel jPanel) {
        Random rand = new Random();
        Color randomColor = new Color(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256));
        jPanel.setBorder(BorderFactory.createLineBorder(randomColor, 2));
    }
}
