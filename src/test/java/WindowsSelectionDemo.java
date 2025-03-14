import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class WindowsSelectionDemo extends JFrame {
    // Массив уникальных коротких английских имён (40 штук)
    private static final String[] NAMES = {
            "Ace", "Ben", "Cam", "Dan", "Eve", "Fay", "Gus", "Hal", "Ivy", "Jay",
            "Kay", "Lee", "Max", "Ned", "Oli", "Pam", "Quin", "Ray", "Sam", "Tia",
            "Uma", "Vic", "Wes", "Xen", "Yen", "Zed", "Amy", "Bob", "Cox", "Dex",
            "Eli", "Fox", "Gem", "Hex", "Ian", "Jax", "Kit", "Lux", "Moe", "Neo"
    };

    // Глобальный счётчик для порядка выделения
    private static long globalSelectionCounter = 1;

    // Список файлов (панелей) в левой области
    private List<SelectablePanel> panels = new ArrayList<>();
    // Индекс последнего выбранного для Shift‑выделения
    private int anchorIndex = -1;
    // Прямоугольник выделения при drag‑selection по фону
    private Rectangle selectionRect = null;

    // Переменные для группового перетаскивания (ghost‑эффект)
    private Point groupDragStart = null;
    private boolean draggingGroup = false;

    // Правая область – информационное окно (drop target), куда "передаются" ссылки
    private DropTargetPanel dropTargetPanel;
    // Glass pane для отображения ghost‑эффекта при перетаскивании
    private DragGlassPane dragGlassPane;

    public WindowsSelectionDemo() {
        setTitle("Windows-like Selection Demo");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 600);

        // Инициализируем glass pane для ghost‑эффекта
        dragGlassPane = new DragGlassPane();
        setGlassPane(dragGlassPane);

        // Создаем левую область – панель с файлами для выделения
        SelectionPanel selectionPanel = new SelectionPanel();
        JScrollPane selectionScroll = new JScrollPane(selectionPanel);

        // Создаем правую область – информационное окно для drop'а
        dropTargetPanel = new DropTargetPanel();
        JScrollPane dropScroll = new JScrollPane(dropTargetPanel);

        // Размещаем обе области в одном окне через разделитель
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, selectionScroll, dropScroll);
        splitPane.setDividerLocation(700);
        add(splitPane);

        // Key binding: при нажатии ESC снимаем выделение
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                .put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "clearSelection");
        getRootPane().getActionMap().put("clearSelection", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                clearSelection();
            }
        });

        // Добавляем key binding для Ctrl+A: выделяет все панели
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                .put(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_DOWN_MASK), "selectAll");
        getRootPane().getActionMap().put("selectAll", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                for (SelectablePanel sp : panels) {
                    sp.setSelected(true);
                }
            }
        });

        setLocationRelativeTo(null);
        setVisible(true);
    }

    // Сброс выделения для всех файлов
    private void clearSelection() {
        for (SelectablePanel p : panels) {
            p.setSelected(false);
        }
    }

    /**
     * Обработка клика по файлу с учётом Ctrl и Shift.
     * Если файл уже выделен, оставляем группу выделенных файлов (как в Windows),
     * иначе сбрасываем предыдущие выделения и выделяем только этот файл.
     */
    private void handlePanelClick(SelectablePanel panel, MouseEvent e) {
        boolean ctrl = (e.getModifiersEx() & MouseEvent.CTRL_DOWN_MASK) != 0;
        boolean shift = (e.getModifiersEx() & MouseEvent.SHIFT_DOWN_MASK) != 0;
        int index = panel.getIndex();

        if (shift) {
            // Если якорь не установлен, считаем его первым элементом
            if (anchorIndex == -1) {
                anchorIndex = 0;
            }
            int start = Math.min(anchorIndex, index);
            int end = Math.max(anchorIndex, index);
            if (!ctrl) {
                clearSelection();
            }
            for (int i = start; i <= end; i++) {
                panels.get(i).setSelected(true);
            }
        } else if (ctrl) {
            panel.setSelected(!panel.isSelected());
            anchorIndex = index;
        } else {
            // Если файл не выделен, сбрасываем все и выделяем только его.
            // Если же он уже выделен, оставляем группу только при drag‑операции.
            if (!panel.isSelected()) {
                clearSelection();
                panel.setSelected(true);
            }
            anchorIndex = index;
        }
    }

    // Файл – представляется панелью с уникальным именем, отображаемым по центру, с возможностью выделения и запуска drag‑операции
    class SelectablePanel extends JPanel {
        private int index;
        private boolean selected = false;
        private String name; // Уникальное имя
        // Порядок выделения
        private long selectionOrder = 0;

        public SelectablePanel(int index, int x, int y) {
            this.index = index;
            this.name = (index < NAMES.length) ? NAMES[index] : "Item" + index;
            setBackground(Color.LIGHT_GRAY);
            setBorder(BorderFactory.createLineBorder(Color.BLACK));
            setBounds(x, y, 80, 80);

            // Каждый объект использует свой MouseAdapter с отслеживанием перемещения
            MouseAdapter ma = new MouseAdapter() {
                Point pressPoint = null;
                boolean moved = false;
                // Сохраняем начальное состояние модификаторов
                boolean initialCtrl = false;
                boolean initialShift = false;

                @Override
                public void mousePressed(MouseEvent e) {
                    e.consume();
                    pressPoint = e.getPoint();
                    moved = false;
                    initialCtrl = (e.getModifiersEx() & MouseEvent.CTRL_DOWN_MASK) != 0;
                    initialShift = (e.getModifiersEx() & MouseEvent.SHIFT_DOWN_MASK) != 0;
                    // Обработка выделения согласно логике Windows
                    handlePanelClick(SelectablePanel.this, e);
                }

                @Override
                public void mouseDragged(MouseEvent e) {
                    Point current = e.getPoint();
                    if (!moved && pressPoint != null) {
                        double dx = current.x - pressPoint.x;
                        double dy = current.y - pressPoint.y;
                        if (Math.sqrt(dx * dx + dy * dy) > 5) {
                            moved = true;
                        }
                    }
                    // Если перемещение произошло и объект выделен, запускаем ghost‑перетаскивание
                    if (moved && !draggingGroup && isSelected()) {
                        draggingGroup = true;
                        groupDragStart = SwingUtilities.convertPoint(SelectablePanel.this, pressPoint, getGlassPane());
                        int count = 0;
                        for (SelectablePanel sp : panels) {
                            if (sp.isSelected()) count++;
                        }
                        String ghostText = (count > 1) ? "Dragging " + count + " objects" : "Dragging object";
                        dragGlassPane.setGhostText(ghostText);
                        dragGlassPane.setGhostLocation(new Point(groupDragStart.x + 10, groupDragStart.y + 10));
                        dragGlassPane.setVisible(true);
                    }
                    if (draggingGroup) {
                        Point glassPt = SwingUtilities.convertPoint(SelectablePanel.this, e.getPoint(), getGlassPane());
                        dragGlassPane.setGhostLocation(new Point(glassPt.x + 10, glassPt.y + 10));
                    }
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    if (draggingGroup) {
                        // Если перетаскивание произошло – выполняем drop-логику
                        // Преобразуем координаты относительно drop‑target панели (учитывая возможную прокрутку)
                        Point dropPoint = SwingUtilities.convertPoint(SelectablePanel.this, e.getPoint(), dropTargetPanel);
                        if (new Rectangle(0, 0, dropTargetPanel.getWidth(), dropTargetPanel.getHeight()).contains(dropPoint)) {
                            // Собираем выделенные файлы и сортируем по порядку выделения
                            List<SelectablePanel> selectedItems = new ArrayList<>();
                            for (SelectablePanel sp : panels) {
                                if (sp.isSelected()) {
                                    selectedItems.add(sp);
                                }
                            }
                            Collections.sort(selectedItems, Comparator.comparingLong(SelectablePanel::getSelectionOrder));
                            dropTargetPanel.dropItems(selectedItems);
                            clearSelection();
                        }
                        draggingGroup = false;
                        groupDragStart = null;
                        dragGlassPane.clearGhost();
                    } else {
                        // Если перетаскивание не произошло (простой клик)
                        // Используем сохранённое начальное состояние модификаторов
                        if (!initialCtrl && !initialShift) {
                            clearSelection();
                            SelectablePanel.this.setSelected(true);
                        }
                    }
                }
            };
            addMouseListener(ma);
            addMouseMotionListener(ma);
        }

        public int getIndex() {
            return index;
        }

        public boolean isSelected() {
            return selected;
        }

        public String getNameText() {
            return name;
        }

        public long getSelectionOrder() {
            return selectionOrder;
        }

        public void setSelected(boolean selected) {
            if (selected && !this.selected) {
                // При установке выделения, если ранее не выделен, присваиваем порядок выделения
                this.selectionOrder = globalSelectionCounter++;
            }
            if (!selected) {
                this.selectionOrder = 0;
            }
            this.selected = selected;
            if (selected) {
                setBorder(BorderFactory.createLineBorder(Color.BLUE, 3));
            } else {
                setBorder(BorderFactory.createLineBorder(Color.BLACK));
            }
            repaint();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            // Выводим имя по центру панели
            g.setColor(Color.BLACK);
            FontMetrics fm = g.getFontMetrics();
            int stringWidth = fm.stringWidth(name);
            int stringAscent = fm.getAscent();
            int x = (getWidth() - stringWidth) / 2;
            int y = (getHeight() + stringAscent) / 2;
            g.drawString(name, x, y);
        }
    }

    // Левая панель, содержащая все файлы с возможностью выделения drag‑прямоугольником
    class SelectionPanel extends JPanel {
        public SelectionPanel() {
            setLayout(null);
            int margin = 10;
            int panelSize = 80;
            int panelsPerRow = 8;
            for (int i = 0; i < 40; i++) {
                int x = margin + (i % panelsPerRow) * (panelSize + margin);
                int y = margin + (i / panelsPerRow) * (panelSize + margin);
                SelectablePanel sp = new SelectablePanel(i, x, y);
                panels.add(sp);
                add(sp);
            }
            // Реализация выделения drag‑прямоугольником по фону
            MouseAdapter ma = new MouseAdapter() {
                Point dragStart = null;
                boolean dragging = false;
                boolean ctrlDownAtStart = false;

                @Override
                public void mousePressed(MouseEvent e) {
                    if (e.getSource() == SelectionPanel.this) {
                        dragStart = e.getPoint();
                        dragging = true;
                        ctrlDownAtStart = (e.getModifiersEx() & MouseEvent.CTRL_DOWN_MASK) != 0;
                        selectionRect = new Rectangle(dragStart);
                    }
                }

                @Override
                public void mouseDragged(MouseEvent e) {
                    if (dragging) {
                        Point current = e.getPoint();
                        selectionRect.setBounds(
                                Math.min(dragStart.x, current.x),
                                Math.min(dragStart.y, current.y),
                                Math.abs(dragStart.x - current.x),
                                Math.abs(dragStart.y - current.y)
                        );
                        repaint();
                    }
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    if (dragging) {
                        dragging = false;
                        if (selectionRect.width < 5 && selectionRect.height < 5) {
                            clearSelection();
                        } else {
                            if (!ctrlDownAtStart) {
                                clearSelection();
                            }
                            for (SelectablePanel sp : panels) {
                                if (selectionRect.intersects(sp.getBounds())) {
                                    sp.setSelected(true);
                                }
                            }
                        }
                        selectionRect = null;
                        repaint();
                    }
                }
            };
            addMouseListener(ma);
            addMouseMotionListener(ma);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (selectionRect != null) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setColor(new Color(0, 0, 255, 50));
                g2.fill(selectionRect);
                g2.setColor(Color.BLUE);
                g2.draw(selectionRect);
            }
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(10 + 8 * (80 + 10), 10 + 5 * (80 + 10));
        }
    }

    // Правая панель – информационное окно (drop target), где выводится информация о переданных файлах
    class DropTargetPanel extends JPanel {
        private JLabel infoLabel;

        public DropTargetPanel() {
            setLayout(new BorderLayout());
            setBackground(Color.WHITE);
            infoLabel = new JLabel("Drop items here", SwingConstants.CENTER);
            add(infoLabel, BorderLayout.CENTER);
        }

        // Выводим список имён в порядке выделения и их количество
        public void dropItems(List<SelectablePanel> items) {
            StringBuilder namesList = new StringBuilder();
            for (SelectablePanel sp : items) {
                namesList.append(sp.getNameText()).append(", ");
            }
            if (namesList.length() >= 2) {
                namesList.setLength(namesList.length() - 2);
            }
            String info = "Dropped: [" + namesList + "] (Count: " + items.size() + ")";
            infoLabel.setText(info);
            System.out.println(info);
        }
    }

    // Glass pane для отображения ghost‑эффекта при перетаскивании
    class DragGlassPane extends JComponent {
        private String ghostText;
        private Point ghostLocation;

        public void setGhostText(String text) {
            this.ghostText = text;
            repaint();
        }

        public void setGhostLocation(Point p) {
            this.ghostLocation = p;
            repaint();
        }

        public void clearGhost() {
            ghostText = null;
            ghostLocation = null;
            setVisible(false);
            repaint();
        }

        @Override
        protected void paintComponent(Graphics g) {
            if (ghostText != null && ghostLocation != null) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.7f));
                FontMetrics fm = g2.getFontMetrics();
                int textWidth = fm.stringWidth(ghostText);
                int textHeight = fm.getHeight();
                int padding = 4;
                int width = textWidth + padding * 2;
                int height = textHeight + padding * 2;
                int x = ghostLocation.x;
                int y = ghostLocation.y;
                g2.setColor(Color.YELLOW);
                g2.fillRect(x, y, width, height);
                g2.setColor(Color.BLACK);
                g2.drawRect(x, y, width, height);
                g2.drawString(ghostText, x + padding, y + fm.getAscent() + padding);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new WindowsSelectionDemo());
    }
}
