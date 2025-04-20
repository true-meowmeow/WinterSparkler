package swing.objects.general;

import swing.objects.WrapLayout;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class JPanelCustom extends JPanel {

    private PanelType defaultPanel  = PanelType.BORDER;

    private String defaultIconPath = "anythingPathToIcon";

    public enum PanelType {
        GRID, BORDER, FLOW, CARD, BOX, WRAP
    }


    // Окно для отображения перетаскиваемого объекта (визуальный эффект во время drag)
    //private final JWindow cursorWindow = new JWindow();

    // Конструкторы
    public JPanelCustom(PanelType type, boolean clearBorder) {
        this(type);
        handleClearBorder(clearBorder);
    }

    // Конструкторы
    public JPanelCustom(boolean clearBorder) {
        this();
        handleClearBorder(clearBorder);
    }

    public JPanelCustom(PanelType type, String axis) {
        handleAXIS(type, axis);
    }

    public JPanelCustom(String axis) {
        handleAXIS(defaultPanel, axis);
    }

    public JPanelCustom(PanelType type, String axis, int a, int b) {
        this(type);
        handleAXIS(type, axis, a, b);
    }


    public JPanelCustom() {
        setLayoutByType(defaultPanel);
    }

    public JPanelCustom(PanelType type) {
        setLayoutByType(type);
        // В базовом классе drop-логика не инициализируется автоматически!
        // Для включения drop нужно явно вызвать setupDropTarget() в подклассе.         актуально ли я не помню
    }

    public JPanelCustom(LayoutManager layout) {
        super(layout);
        // Не инициализируем drop-логику автоматически
    }

    // Установка layout в зависимости от типа панели
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
            case BOX:
                setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
                break;
            case WRAP:
                setLayout(new WrapLayout());
                break;
            default:
                throw new IllegalArgumentException("Unknown panel type: " + type);
        }
    }

    // Настройка оси для BoxLayout и FlowLayout
    private void handleAXIS(PanelType type, String axis, int a, int b) {
        if (type.equals(PanelType.FLOW)) {
            switch (axis) {
                case "LEFT":
                    setLayout(new FlowLayout(FlowLayout.LEFT, a, b));
                    break;
                case "RIGHT":
                    setLayout(new FlowLayout(FlowLayout.RIGHT, a, b));
                    break;
                case "LEADING":
                    setLayout(new FlowLayout(FlowLayout.LEADING, a, b));
                    break;
                case "CENTER":
                    setLayout(new FlowLayout(FlowLayout.CENTER, a, b));
                    break;
                case "TRAILING":
                    setLayout(new FlowLayout(FlowLayout.TRAILING, a, b));
                    break;
                default:
                    System.out.println("Unsupported axis: " + axis);
            }
        } else {
            System.out.println("The layout is not of border type");
        }
    }

    public void setAXIS(PanelType type, String axis) {
        handleAXIS(type, axis);
    }

    // Настройка оси для BoxLayout и FlowLayout
    private void handleAXIS(PanelType type, String axis) {
        if (type.equals(PanelType.BORDER)) {
            switch (axis) {
                case "X":
                    setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
                    break;
                case "PAGE":
                    setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
                    break;
                case "LINE":
                    setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
                    break;
                case "Y":
                    setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
                    break;
                default:
                    System.out.println("Unsupported axis: " + axis);
            }
        } else if (type.equals(PanelType.FLOW)) {
            switch (axis) {
                case "LEFT":
                    setLayout(new FlowLayout(FlowLayout.LEFT));
                    break;
                case "RIGHT":
                    setLayout(new FlowLayout(FlowLayout.RIGHT));
                    break;
                case "LEADING":
                    setLayout(new FlowLayout(FlowLayout.LEADING));
                    break;
                case "CENTER":
                    setLayout(new FlowLayout(FlowLayout.CENTER));
                    break;
                case "TRAILING":
                    setLayout(new FlowLayout(FlowLayout.TRAILING));
                    break;
                default:
                    System.out.println("Unsupported axis: " + axis);
            }
        } else if (type.equals(PanelType.WRAP)) {
            switch (axis) {
                case "LEFT":
                    setLayout(new WrapLayout(FlowLayout.LEFT));
                    break;
                case "RIGHT":
                    setLayout(new WrapLayout(FlowLayout.RIGHT));
                    break;
                case "LEADING":
                    setLayout(new WrapLayout(FlowLayout.LEADING));
                    break;
                case "CENTER":
                    setLayout(new WrapLayout(FlowLayout.CENTER));
                    break;
                case "TRAILING":
                    setLayout(new WrapLayout(FlowLayout.TRAILING));
                    break;
                default:
                    System.out.println("Unsupported axis: " + axis);
            }
        } else if (type.equals(PanelType.BOX)) {
            switch (axis) {
                case "PAGE":
                    setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
                    break;
                case "LINE":
                    setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
                    break;
                case "X":
                    setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
                    break;
                case "Y":
                    setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
                    break;
                default:
                    System.out.println("Unsupported axis: " + axis);
            }
        } else {
            System.out.println("The layout is not of border type");
        }
    }

    private void handleClearBorder(boolean clearBorder) {
        if (clearBorder) {
            setPreferredSize(new Dimension(0, 0));
            setMinimumSize(new Dimension(0, 0));
        }
    }

    // Фабричные методы для создания панелей
    public static JPanelCustom createGridPanel(boolean clearBorder) {
        return new JPanelCustom(PanelType.GRID, clearBorder);
    }

    public static JPanelCustom createBorderPanel(boolean clearBorder) {
        return new JPanelCustom(PanelType.BORDER, clearBorder);
    }

    public static JPanelCustom createFlowPanel(boolean clearBorder) {
        return new JPanelCustom(PanelType.FLOW, clearBorder);
    }

    // Прочие методы для работы с layout (например, GridBagLayout)
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
