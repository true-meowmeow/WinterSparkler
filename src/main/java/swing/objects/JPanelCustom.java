package swing.objects;

import javax.swing.*;
import java.awt.*;

import static swing.objects.MethodsSwing.createBorder;

public class JPanelCustom extends JPanel {
    public enum PanelType {
        GRID, BORDER, FLOW, CARD
    }

    // Конструктор для использования с заранее определёнными типами (с enum)
    public JPanelCustom(PanelType type, boolean clearBorder) {
        this(type);
        //createBorder(this);
        handleClearBorder(clearBorder);
    }

    // Конструктор для использования с заранее определёнными типами (с enum) и осью для BoxLayout
    public JPanelCustom(PanelType type, String axis) {
        this(type);
        handleAXIS(type, axis);
    }

    // Конструктор по типу (с enum)
    public JPanelCustom(PanelType type) {
        setLayoutByType(type);
    }

    // Новый конструктор, позволяющий задавать любой LayoutManager
    public JPanelCustom(LayoutManager layout) {
        super(layout);
    }

    private void setLayoutByType(PanelType type) {
        switch (type) {
            case GRID:
                setLayout(new GridBagLayout());
                break;
            case BORDER:
                setLayout(new BorderLayout());
                break;
            case FLOW:
                setLayout(new FlowLayout());
                break;
            case CARD:
                setLayout(new CardLayout());
                break;
            default:
                throw new IllegalArgumentException("Unknown panel type: " + type);
        }
    }

    private void handleAXIS(PanelType type, String axis) {
        if (type.equals(PanelType.BORDER)) {
            switch (axis) {
                case "X" -> {
                    setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
                    break;
                }
                case "PAGE" -> {
                    setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
                    break;
                }
                case "LINE" -> {
                    setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
                    break;
                }
                case "Y" -> {
                    setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
                    break;
                }
                default -> System.out.println("Unsupported axis: " + axis);
            }
        } else if (type.equals(PanelType.FLOW)) {
            switch (axis) {
                case "LEFT" -> {
                    setLayout(new FlowLayout(FlowLayout.LEFT));
                    break;
                }
                case "RIGHT" -> {
                    setLayout(new FlowLayout(FlowLayout.RIGHT));
                    break;
                }
                case "LEADING" -> {
                    setLayout(new FlowLayout(FlowLayout.LEADING));
                    break;
                }
                case "CENTER" -> {
                    setLayout(new FlowLayout(FlowLayout.CENTER));
                    break;
                }
                case "TRAILING" -> {
                    setLayout(new FlowLayout(FlowLayout.TRAILING));
                    break;
                }
                default -> System.out.println("Unsupported axis: " + axis);
            }
        } else {
            System.out.println("The layout is not the border type");
        }
    }

    private void handleClearBorder(boolean clearBorder) {
        if (clearBorder) {
            setPreferredSize(new Dimension(0, 0));
            setMinimumSize(new Dimension(0, 0));
        }
    }

    // Фабричные методы для создания панелей по типу (опционально)
    public static JPanelCustom createGridPanel(boolean clearBorder) {
        return new JPanelCustom(PanelType.GRID, clearBorder);
    }

    public static JPanelCustom createBorderPanel(boolean clearBorder) {
        return new JPanelCustom(PanelType.BORDER, clearBorder);
    }

    public static JPanelCustom createFlowPanel(boolean clearBorder) {
        return new JPanelCustom(PanelType.FLOW, clearBorder);
    }
}
