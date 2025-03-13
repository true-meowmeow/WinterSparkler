package swing.objects;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.*;
import java.awt.dnd.*;

public class JPanelCustom extends JPanel {
    public enum PanelType {
        GRID, BORDER, FLOW, CARD
    }

    // Окно для отображения перетаскиваемого объекта (будет показан результат вызова toString())
    private final JWindow cursorWindow = new JWindow();

    // Конструкторы
    public JPanelCustom(PanelType type, boolean clearBorder) {
        this(type);
        handleClearBorder(clearBorder);
    }

    public JPanelCustom(PanelType type, String axis) {
        this(type);
        handleAXIS(type, axis);
    }

    public JPanelCustom(PanelType type) {
        setLayoutByType(type);
    }

    public JPanelCustom(LayoutManager layout) {
        super(layout);
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
            default:
                throw new IllegalArgumentException("Unknown panel type: " + type);
        }
    }

    // Настройка оси для BoxLayout и FlowLayout
    private void handleAXIS(PanelType type, String axis) {
        if (type.equals(PanelType.BORDER)) {
            switch (axis) {
                case "X" -> setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
                case "PAGE" -> setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
                case "LINE" -> setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
                case "Y" -> setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
                default -> System.out.println("Unsupported axis: " + axis);
            }
        } else if (type.equals(PanelType.FLOW)) {
            switch (axis) {
                case "LEFT" -> setLayout(new FlowLayout(FlowLayout.LEFT));
                case "RIGHT" -> setLayout(new FlowLayout(FlowLayout.RIGHT));
                case "LEADING" -> setLayout(new FlowLayout(FlowLayout.LEADING));
                case "CENTER" -> setLayout(new FlowLayout(FlowLayout.CENTER));
                case "TRAILING" -> setLayout(new FlowLayout(FlowLayout.TRAILING));
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

    // Регистрация DragGestureRecognizer для компонента и всех его потомков
    protected void registerDragGesture(Component comp) {
        DragSource.getDefaultDragSource().createDefaultDragGestureRecognizer(
                comp, DnDConstants.ACTION_COPY, new DragGestureHandler());
        if (comp instanceof Container) {
            for (Component child : ((Container) comp).getComponents()) {
                registerDragGesture(child);
            }
        }
    }

    // Метод для настройки DnD, вызывается, например, в конструкторе наследника
    public void setupDragAndDrop() {
        registerDragGesture(this);
    }

    /**
     * Метод для получения данных для перетаскивания.
     * Наследники должны переопределить его для возврата нужного объекта.
     */
    protected Object getDragData() {
        return null;
    }

    // Обработчик распознавания жеста перетаскивания
    class DragGestureHandler implements DragGestureListener {
        @Override
        public void dragGestureRecognized(DragGestureEvent dge) {
            Object dragData = getDragData();
            if (dragData == null) {
                return; // Если данных нет, перетаскивание не инициируется
            }
            // Для визуализации используем toString() объекта
            String dragText = dragData.toString();
            Point dragPoint = dge.getDragOrigin();
            SwingUtilities.convertPointToScreen(dragPoint, JPanelCustom.this);
            cursorWindow.getContentPane().removeAll();
            JLabel cursorLabel = new JLabel(dragText, SwingConstants.CENTER);
            cursorLabel.setFont(new Font("Arial", Font.PLAIN, 20));
            cursorWindow.getContentPane().add(cursorLabel);
            cursorWindow.pack();
            cursorWindow.setBackground(new Color(0, 0, 0, 0));
            cursorWindow.setFocusableWindowState(false);
            cursorWindow.setLocation(dragPoint.x + 10, dragPoint.y + 10);
            cursorWindow.setAlwaysOnTop(true);
            cursorWindow.setVisible(true);
            DragHandler dragHandler = new DragHandler();
            dge.getDragSource().addDragSourceMotionListener(dragHandler);
            dge.startDrag(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR),
                    new TransferableData(dragData), dragHandler);
        }
    }

    // Обработчик для обновления позиции курсора и завершения операции перетаскивания
    class DragHandler implements DragSourceListener, DragSourceMotionListener {
        @Override
        public void dragEnter(DragSourceDragEvent dsde) { }

        @Override
        public void dragOver(DragSourceDragEvent dsde) { }

        @Override
        public void dropActionChanged(DragSourceDragEvent dsde) { }

        @Override
        public void dragExit(DragSourceEvent dse) { }

        @Override
        public void dragDropEnd(DragSourceDropEvent dsde) {
            cursorWindow.setVisible(false);
            dsde.getDragSourceContext().getDragSource().removeDragSourceMotionListener(this);
        }

        @Override
        public void dragMouseMoved(DragSourceDragEvent dsde) {
            Point newLocation = dsde.getLocation();
            cursorWindow.setLocation(newLocation.x + 10, newLocation.y + 10);
        }
    }

    // Внутренний класс для передачи данных через Drag & Drop
    static class TransferableData implements Transferable {
        private final Object data;
        public static final DataFlavor OBJECT_DATA_FLAVOR =
                new DataFlavor(DataFlavor.javaJVMLocalObjectMimeType + ";class=java.lang.Object", "Local Object");

        public TransferableData(Object data) {
            this.data = data;
        }
        @Override
        public DataFlavor[] getTransferDataFlavors() {
            return new DataFlavor[]{OBJECT_DATA_FLAVOR};
        }
        @Override
        public boolean isDataFlavorSupported(DataFlavor flavor) {
            return flavor.equals(OBJECT_DATA_FLAVOR);
        }
        @Override
        public Object getTransferData(DataFlavor flavor) {
            if (isDataFlavorSupported(flavor)) {
                return data;
            }
            return null;
        }
    }

    // Прочие методы, например, для работы с GridBagLayout
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
