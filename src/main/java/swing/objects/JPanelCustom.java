package swing.objects;

import javax.swing.*;
import java.awt.*;

import static swing.objects.MethodsSwing.createBorder;

public class JPanelCustom extends JPanel {
    public enum PanelType {
        GRID, BORDER, FLOW
    }

    // Основной конструктор
    public JPanelCustom(PanelType type, boolean clearBorder) {
        this(type);
        //createBorder(this);
        handleClearBorder(clearBorder);
    }

    // Основной конструктор
    public JPanelCustom(PanelType type, String axis) {
        this(type);
        handleAXIS(type, axis);
    }

    // Упрощенные конструкторы
    public JPanelCustom(PanelType type) {
        setLayoutByType(type);
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

    // Методы для создания конкретных типов (опционально)
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