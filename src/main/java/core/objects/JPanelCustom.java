package core.objects;

import core.main.check.Axis;
import core.main.check.PanelType;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class JPanelCustom extends JPanel {

    public static final int MAX_INT = Integer.MAX_VALUE;
    public static final int ZERO_INT = 0;
    public static final Dimension ZERO = new Dimension(ZERO_INT, ZERO_INT);
    public static final Dimension MAX = new Dimension(MAX_INT, MAX_INT);

    private static final PanelType DEFAULT_TYPE = PanelType.BORDER;

    // Конструкторы
    public JPanelCustom(PanelType type, Axis axis, int hgap, int vgap, boolean clearBorder) {
        setLayout(type.createLayout(this, axis, hgap, vgap));
        if (clearBorder) {
            setPreferredSize(ZERO);
            setMinimumSize(ZERO);
        }
    }

    /*** Конструктор по умолчанию.*/
    public JPanelCustom() {
        this(DEFAULT_TYPE, null, 0, 0, false);
    }

    /*** Конструктор для добавления по LayoutManager.*/
    public JPanelCustom(LayoutManager layout) {
        super();
        setLayout(layout);
    }

    /*** Конструктор установки нулевых границ.*/
    public JPanelCustom(boolean clearBorder) {
        this(DEFAULT_TYPE, null, 0, 0, clearBorder);
    }

    /*** Конструктор установки нулевых границ с типом панели.*/
    public JPanelCustom(PanelType type, boolean clearBorder) {
        this(type, null, 0, 0, clearBorder);
    }

    /*** Упрощённый: без отступов.*/
    public JPanelCustom(PanelType type, Axis axis) {
        this(type, axis, 0, 0, false);
    }

    /*** Упрощённый: только ось, тип панели по умолчанию.*/
    public JPanelCustom(Axis axis) {
        this(DEFAULT_TYPE, axis, 0, 0, false);
    }

    /*** Базовый конструктор по типу без осей и отступов.*/
    public JPanelCustom(PanelType type) {
        setLayout(type.createLayout(this, null, 0, 0));
    }

    public void setBorder(int top, int left, int bottom, int right) {
        setBorder(BorderFactory.createEmptyBorder(top, left, bottom, right));
    }


    public void setDimensions(Dimension minimum, Dimension preferred, Dimension maximum) {
        setMinimumSize(minimum);
        setPreferredSize(preferred);
        setMaximumSize(maximum);
    }

    public void setPreferredSize(int x, int y) {
        super.setPreferredSize(new Dimension(x, y));
    }

    public void setMaximumSize(int x, int y) {
        super.setMaximumSize(new Dimension(x, y));
    }

    public void setMinimumSize(int x, int y) {
        super.setMinimumSize(new Dimension(x, y));
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
