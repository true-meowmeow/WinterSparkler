import swing.DemoPrefs;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.*;
import java.awt.dnd.*;
import java.awt.image.BufferedImage;

public class DraggableObjectImage extends JPanel {
    // Окно, которое будет отображать увеличенное изображение вместо курсора
    private final JWindow cursorWindow = new JWindow();
    private final String objectName;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {


            DemoPrefs.init("/core");
            DemoPrefs.setupLaf(args);


            JFrame mainFrame = new JFrame("Drag & Drop Example");
            mainFrame.setSize(1200, 600);
            mainFrame.setLayout(new GridLayout(1, 3));

            DraggableObjectImage objectA = new DraggableObjectImage("Object Alpha");
            DraggableObjectImage objectB = new DraggableObjectImage("Object Beta");
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

    public DraggableObjectImage(String name) {
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

    // Метод создания изображения из содержимого JFrame
    public static Image createImageFromFrame(JFrame frame) {
        BufferedImage image = new BufferedImage(frame.getWidth(), frame.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();
        frame.paint(g2d);
        g2d.dispose();
        return image;
    }

    class DragGestureHandler implements DragGestureListener {
        @Override
        public void dragGestureRecognized(DragGestureEvent dge) {
            // Получаем начальную точку перетаскивания в экранных координатах
            Point dragPoint = dge.getDragOrigin();
            SwingUtilities.convertPointToScreen(dragPoint, DraggableObjectImage.this);
            // Берем изображение из родительского окна (JFrame)
            JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(DraggableObjectImage.this);
            Image frameImage = createImageFromFrame(topFrame);
            // Масштабируем изображение до нужного размера (200x100)
            Image cursorImage = frameImage.getScaledInstance(200, 100, Image.SCALE_SMOOTH);

            // Подготавливаем наше окно: очищаем старое содержимое, добавляем новый JLabel с изображением
            cursorWindow.getContentPane().removeAll();
            JLabel cursorLabel = new JLabel(new ImageIcon(cursorImage));
            cursorWindow.getContentPane().add(cursorLabel);
            cursorWindow.pack();
            cursorWindow.setBackground(new Color(0, 0, 0, 0)); // Прозрачный фон
            cursorWindow.setFocusableWindowState(false);
            // Устанавливаем начальное положение окна рядом с точкой перетаскивания
            cursorWindow.setLocation(dragPoint.x + 10, dragPoint.y + 10);
            cursorWindow.setAlwaysOnTop(true);
            cursorWindow.setVisible(false);

            // Создаем обработчик перетаскивания, который обрабатывает и события перетаскивания, и движения мыши
            DragHandler dragHandler = new DragHandler();
            // Регистрируем обработчик движения мыши
            DragSource dragSource = dge.getDragSource();
            dragSource.addDragSourceMotionListener(dragHandler);
            // Используем перегруженный метод startDrag, передавая прозрачное drag‑image и смещение


            dge.startDrag(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR), cursorImage, new Point(0, 0),
                    new TransferableData(objectName), dragHandler);

            //dge.startDrag(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR), new TransferableData(objectName), dragHandler);

        }
    }
    //Image cursorImage2 = new ImageIcon("path/to/your/cursor.png").getImage();
    // Создание пользовательского курсора
    // Определение точки "горячей точки" курсора (точка, которая будет считать координаты клика)
    Point hotspot = new Point(0, 0);
    //Cursor customCursor = Toolkit.getDefaultToolkit().createCustomCursor(cursorImage2, hotspot, "MyCustomCursor");
    Cursor customCursorr = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);

    class DragSourceHandler extends DragSourceAdapter {
        @Override
        public void dragEnter(DragSourceDragEvent dsde) {
            dsde.getDragSourceContext().setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)
            );
        }

        @Override
        public void dragOver(DragSourceDragEvent dsde) {
            dsde.getDragSourceContext().setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)
            );
        }

        @Override
        public void dragDropEnd(DragSourceDropEvent dsde) {
            if (dsde.getDropSuccess()) {
                System.out.println("Successfully dropped: " + objectName);
            }
        }
    }
    // Обработчик, реализующий DragSourceListener и DragSourceMotionListener
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
            // По окончании перетаскивания скрываем окно
            cursorWindow.setVisible(false);
            // Удаляем обработчик движения, чтобы избежать утечек
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