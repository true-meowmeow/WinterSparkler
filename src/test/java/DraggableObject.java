import swing.DemoPrefs;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.*;
import java.awt.dnd.*;

public class DraggableObject extends JPanel {
    // Окно, которое будет отображать имя объекта вместо курсора
    private final JWindow cursorWindow = new JWindow();
    private final String objectName;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            DemoPrefs.init("/core");
            DemoPrefs.setupLaf(args);

            JFrame mainFrame = new JFrame("Drag & Drop Example");
            mainFrame.setSize(1200, 600);
            mainFrame.setLayout(new GridLayout(1, 3));

            DraggableObject objectA = new DraggableObject("Object Alpha");
            DraggableObject objectB = new DraggableObject("Object Beta");
            DropTargetPanel dropPanel = new DropTargetPanel(); // Добавляем зону сброса

            mainFrame.add(objectA);
            mainFrame.add(objectB);
            mainFrame.add(dropPanel);
            mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            try {
                UIManager.setLookAndFeel("com.formdev.flatlaf.FlatDarkLaf");
                UIManager.setLookAndFeel("com.formdev.flatlaf.FlatLightLaf");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            SwingUtilities.updateComponentTreeUI(mainFrame); // для главного окна
            mainFrame.setVisible(true);
        });
    }

    public DraggableObject(String name) {
        this.objectName = name;
        setupUI();
        setupDragAndDrop();
    }

    private void setupUI() {
        setLayout(new BorderLayout());
        setBackground(new Color(200, 200, 255));
        setBorder(BorderFactory.createTitledBorder(objectName));
        setPreferredSize(new Dimension(300, 200));

        JLabel content = new JLabel("Drag me!", SwingConstants.CENTER);
        add(content, BorderLayout.CENTER);
    }

    private void setupDragAndDrop() {
        DragSource dragSource = DragSource.getDefaultDragSource();
        dragSource.createDefaultDragGestureRecognizer(
                this, DnDConstants.ACTION_COPY, new DragGestureHandler());
    }

    class DragGestureHandler implements DragGestureListener {
        @Override
        public void dragGestureRecognized(DragGestureEvent dge) {
            // Получаем начальную точку перетаскивания в экранных координатах
            Point dragPoint = dge.getDragOrigin();
            SwingUtilities.convertPointToScreen(dragPoint, DraggableObject.this);

            // Создаем новое окно (JWindow), которое будет содержать только имя объекта
            cursorWindow.getContentPane().removeAll(); // Очистим старое содержимое
            JLabel cursorLabel = new JLabel(objectName, SwingConstants.CENTER);
            cursorLabel.setFont(new Font("Arial", Font.PLAIN, 20));
            cursorWindow.getContentPane().add(cursorLabel);
            cursorWindow.pack();
            cursorWindow.setBackground(new Color(0, 0, 0, 0)); // Прозрачный фон
            cursorWindow.setFocusableWindowState(false);
            cursorWindow.setLocation(dragPoint.x + 10, dragPoint.y + 10); // Устанавливаем начальную позицию окна
            cursorWindow.setAlwaysOnTop(true);
            cursorWindow.setVisible(true);

            // Создаем обработчик перетаскивания
            DragHandler dragHandler = new DragHandler();
            DragSource dragSource = dge.getDragSource();
            dragSource.addDragSourceMotionListener(dragHandler);

            // Запускаем перетаскивание с использованием пустого изображения, передавая только данные
            dge.startDrag(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR), new TransferableData(objectName), dragHandler);
        }
    }

    class DragHandler implements DragSourceListener, DragSourceMotionListener {
        @Override
        public void dragEnter(DragSourceDragEvent dsde) {}

        @Override
        public void dragOver(DragSourceDragEvent dsde) {}

        @Override
        public void dropActionChanged(DragSourceDragEvent dsde) {}

        @Override
        public void dragExit(DragSourceEvent dse) {}

        @Override
        public void dragDropEnd(DragSourceDropEvent dsde) {
            // По окончании перетаскивания скрываем окно
            cursorWindow.setVisible(false);
            DragSource ds = dsde.getDragSourceContext().getDragSource();
            ds.removeDragSourceMotionListener(this);
        }

        @Override
        public void dragMouseMoved(DragSourceDragEvent dsde) {
            // Обновляем положение окна, чтобы оно следовало за курсором
            Point newLocation = dsde.getLocation();
            cursorWindow.setLocation(newLocation.x + 10, newLocation.y + 10);
        }
    }

    // Класс для передачи данных (в данном примере передается строка)
    static class TransferableData implements Transferable {
        private final String data;

        public TransferableData(String data) {
            this.data = data;
        }

        @Override
        public DataFlavor[] getTransferDataFlavors() {
            return new DataFlavor[]{DataFlavor.stringFlavor};
        }

        @Override
        public boolean isDataFlavorSupported(DataFlavor flavor) {
            return flavor.equals(DataFlavor.stringFlavor);
        }

        @Override
        public Object getTransferData(DataFlavor flavor) {
            return data;
        }
    }

    static class DropTargetPanel extends JPanel {
        public DropTargetPanel() {
            setBackground(new Color(220, 220, 220));
            setBorder(BorderFactory.createLineBorder(Color.BLUE, 2));
            setPreferredSize(new Dimension(300, 200));

            new DropTarget(this, DnDConstants.ACTION_COPY, new DropTargetHandler(), true);
        }

        class DropTargetHandler extends DropTargetAdapter {
            @Override
            public void drop(DropTargetDropEvent dtde) {
                try {
                    if (dtde.isDataFlavorSupported(DataFlavor.stringFlavor)) {
                        dtde.acceptDrop(DnDConstants.ACTION_COPY);
                        String data = (String) dtde.getTransferable()
                                .getTransferData(DataFlavor.stringFlavor);

                        JLabel label = new JLabel("Dropped: " + data);
                        label.setForeground(Color.BLUE);
                        add(label);
                        revalidate();
                        dtde.dropComplete(true);
                    }
                } catch (Exception e) {
                    dtde.rejectDrop();
                }
            }
        }
    }
}
