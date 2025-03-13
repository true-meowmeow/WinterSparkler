package swing.objects;

import swing.pages.home.play.objects.FolderPanel;
import swing.pages.home.play.objects.MediaPanel;
import swing.pages.home.play.objects.SelectionManager;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.*;
import java.awt.dnd.*;
import java.awt.event.InputEvent;

public class JPanelCustom extends JPanel {
    public enum PanelType {
        GRID, BORDER, FLOW, CARD
    }

    // Окно для отображения перетаскиваемого объекта (визуальный эффект во время drag)
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
        // В базовом классе drop-логика не инициализируется автоматически!
        // Для включения drop нужно явно вызвать setupDropTarget() в подклассе.
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
            default:
                throw new IllegalArgumentException("Unknown panel type: " + type);
        }
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

    // Регистрация DragGestureRecognizer для компонента и его потомков
    protected void registerDragGesture(Component comp) {
        DragSource.getDefaultDragSource().createDefaultDragGestureRecognizer(
                comp, DnDConstants.ACTION_COPY, new DragGestureHandler());
        if (comp instanceof Container) {
            for (Component child : ((Container) comp).getComponents()) {
                registerDragGesture(child);
            }
        }
    }

    // Метод для настройки DnD (drag) – вызывается явно, когда нужно инициировать перетаскивание
    public void setupDragAndDrop() {
        registerDragGesture(this);
    }

    /**
     * Метод для получения данных для перетаскивания.
     * Подклассы должны переопределить его для возврата нужного объекта.
     */
    protected Object getDragData() {
        return null;
    }

    // **** Drop-логика (вызывается только если явно включена) ****

    /**
     * Инициализация DropTarget.
     * Вызовите этот метод в конструкторе подкласса, если хотите, чтобы панель принимала drop-события.
     */
    public void setupDropTarget() {
        new DropTarget(this, DnDConstants.ACTION_COPY, new DropTargetHandler(), true);
    }

    /**
     * Метод для обработки объекта, полученного при drop.
     * Подклассы могут переопределить для специальной обработки.
     *
     * @param dropData объект, полученный при drop.
     */
    protected void handleDrop(Object dropData) {
        JLabel label = new JLabel("Dropped: " + dropData.toString());
        label.setForeground(Color.BLUE);
        add(label);
        revalidate();
        repaint();
    }

// Внутри класса JPanelCustom

    class DragGestureHandler implements DragGestureListener {
        @Override
        public void dragGestureRecognized(DragGestureEvent dge) {
            Object dragData = getDragData();
            if (dragData == null) {
                return;
            }

            int modifiers = dge.getTriggerEvent().getModifiersEx();
            boolean ctrlPressed = (modifiers & InputEvent.CTRL_DOWN_MASK) != 0;

            boolean alreadySelected = false;
            if (dragData instanceof FolderPanel) {
                alreadySelected = ((FolderPanel) dragData).isSelected();
            } else if (dragData instanceof MediaPanel) {
                alreadySelected = ((MediaPanel) dragData).isSelected();
            }

            // Если объект НЕ был выделен ранее, сбрасываем выделение других объектов
            if (!alreadySelected && !ctrlPressed) {
                SelectionManager.clearAllSelections();
            }

            if (dragData instanceof FolderPanel) {
                SelectionManager.toggleFolderSelection((FolderPanel) dragData, ctrlPressed);
            } else if (dragData instanceof MediaPanel) {
                SelectionManager.toggleMediaSelection((MediaPanel) dragData, ctrlPressed);
            }

            // Остальной код, создающий курсор и начинающий перетаскивание
            Point dragPoint = dge.getDragOrigin();
            SwingUtilities.convertPointToScreen(dragPoint, JPanelCustom.this);
            cursorWindow.getContentPane().removeAll();
            JLabel cursorLabel = new JLabel(dragData.toString(), SwingConstants.CENTER);
            cursorLabel.setFont(new Font("Arial", Font.PLAIN, 20));
            cursorWindow.getContentPane().add(cursorLabel);
            cursorWindow.pack();
            cursorWindow.setBackground(new Color(0, 0, 0, 0));
            cursorWindow.setFocusableWindowState(false);
            cursorWindow.setLocation(dragPoint.x + 10, dragPoint.y + 10);
            cursorWindow.setAlwaysOnTop(true);
            cursorWindow.setVisible(true);

            DragHandler dragHandler = new DragHandler(dragData);
            dge.getDragSource().addDragSourceMotionListener(dragHandler);
            dge.startDrag(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR),
                    new TransferableData(dragData), dragHandler);
        }
    }


    // Внутри класса JPanelCustom
    class DragHandler implements DragSourceListener, DragSourceMotionListener {
        private Object dragData;

        public DragHandler(Object dragData) {
            this.dragData = dragData;
        }

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
            // Для FolderPanel: если выделена только одна панель, сбрасываем выделение
            if (dragData instanceof FolderPanel) {
                FolderPanel folderPanel = (FolderPanel) dragData;
                if (SelectionManager.selectedFolderPanels.size() <= 1) {
                    folderPanel.setSelected(false);
                    SelectionManager.selectedFolderPanels.remove(folderPanel);
                }
            }
            // Для MediaPanel: если выделена только одна панель, сбрасываем выделение
            else if (dragData instanceof MediaPanel) {
                MediaPanel mediaPanel = (MediaPanel) dragData;
                if (SelectionManager.selectedMediaPanels.size() <= 1) {
                    mediaPanel.setSelected(false);
                    SelectionManager.selectedMediaPanels.remove(mediaPanel);
                }
            }
            cursorWindow.setVisible(false);
            dsde.getDragSourceContext().getDragSource().removeDragSourceMotionListener(this);
        }

        @Override
        public void dragMouseMoved(DragSourceDragEvent dsde) {
            Point newLocation = dsde.getLocation();
            cursorWindow.setLocation(newLocation.x + 10, newLocation.y + 10);
        }
    }

    // Обработчик drop-событий
    class DropTargetHandler extends DropTargetAdapter {
        @Override
        public void drop(DropTargetDropEvent dtde) {
            try {
                if (dtde.isDataFlavorSupported(TransferableData.OBJECT_DATA_FLAVOR)) {
                    dtde.acceptDrop(DnDConstants.ACTION_COPY);
                    Object data = dtde.getTransferable()
                            .getTransferData(TransferableData.OBJECT_DATA_FLAVOR);
                    handleDrop(data);
                    dtde.dropComplete(true);
                } else {
                    dtde.rejectDrop();
                }
            } catch (Exception e) {
                dtde.rejectDrop();
            }
        }
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
}
