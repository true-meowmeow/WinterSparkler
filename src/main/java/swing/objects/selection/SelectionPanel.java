package swing.objects.selection;

import core.contentManager.FilesDataMap;
import core.contentManager.MediaData;
import swing.objects.JPanelCustom;
import swing.pages.home.play.FolderSystemPanel;

import javax.swing.*;
import javax.swing.plaf.LayerUI;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class SelectionPanel extends JPanelCustom {

    private WrapScrollablePanel container;
    private JScrollPane scrollPane;
    // Локальная переменная для выделения
    private Rectangle selectionRect = null;

    public SelectionPanel() {
        // Используем BorderLayout для корректного размещения компонентов
        setLayout(new BorderLayout());

        container = new WrapScrollablePanel(new WrapLayout(FlowLayout.LEFT));
        scrollPane = new JScrollPane(container,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getViewport().setOpaque(false);

        // JLayer для наложения эффекта выделения
        JLayer<JComponent> jlayer = new JLayer<>(scrollPane, new LayerUI<JComponent>() {
            @Override
            public void paint(Graphics g, JComponent c) {
                super.paint(g, c);
                if (selectionRect != null) {
                    Graphics2D g2 = (Graphics2D) g;
                    g2.setColor(new Color(0, 0, 255, 50));
                    g2.fill(selectionRect);
                    g2.setColor(Color.BLUE);
                    g2.draw(selectionRect);
                }
            }
        });

        add(jlayer, BorderLayout.CENTER);
        add(Box.createVerticalGlue(), BorderLayout.SOUTH);

        // Перерисовка при изменении размеров
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                SwingUtilities.invokeLater(() -> {
                    container.invalidate();
                    container.validate();
                    container.repaint();
                    SelectionPanel.this.repaint();
                });
            }
        });

        // Регистрируем обработчики мыши один раз в конструкторе
        addMouseHandlers();
    }

    /**
     * Метод updateSet только обновляет содержимое контейнера,
     * а обработчики мыши уже зарегистрированы в конструкторе.
     */
    public void updateSet(FilesDataMap.CatalogData.FilesData filesDataHashSet) {
        container.removeAll();
        FolderSystemPanel.FolderSystemPanelInstance().panels.clear();

        int index = 0;
        // Добавляем панели папок
        for (FilesDataMap.CatalogData.FilesData.SubFolder folder : filesDataHashSet.getFoldersDataHashSet()) {
            FolderPanel fp = new FolderPanel(index++, folder);
            FolderSystemPanel.FolderSystemPanelInstance().panels.add(fp);
            container.add(fp);
        }
        // Добавляем сепаратор для завершения строки в WrapLayout
        container.add(createSeparator());
        // Добавляем панели медиа
        for (MediaData media : filesDataHashSet.getMediaDataHashSet()) {
            MediaPanel mp = new MediaPanel(index++, media);
            FolderSystemPanel.FolderSystemPanelInstance().panels.add(mp);
            container.add(mp);
        }

        container.revalidate();
        container.repaint();
    }

    /**
     * Регистрирует обработчики мыши в viewport.
     */
    private void addMouseHandlers() {
        JViewport viewport = scrollPane.getViewport();
        MouseAdapter ma = new MouseAdapter() {
            Point dragStart = null;
            boolean dragging = false;
            boolean ctrlDownAtStart = false;
            boolean shiftDownAtStart = false;
            boolean altDownAtStart = false;

            @Override
            public void mousePressed(MouseEvent e) {
                dragStart = e.getPoint();
                dragging = true;
                ctrlDownAtStart = (e.getModifiersEx() & MouseEvent.CTRL_DOWN_MASK) != 0;
                shiftDownAtStart = (e.getModifiersEx() & MouseEvent.SHIFT_DOWN_MASK) != 0;
                altDownAtStart = (e.getModifiersEx() & MouseEvent.ALT_DOWN_MASK) != 0;
                if (!ctrlDownAtStart && !shiftDownAtStart && !altDownAtStart) {
                    FolderSystemPanel.FolderSystemPanelInstance().clearSelection();
                }
                // Инициализируем выделение локально
                selectionRect = new Rectangle(dragStart);
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                if (dragging && selectionRect != null) {
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
                if (dragging && selectionRect != null) {
                    dragging = false;
                    // Обработка выделения для каждой панели
                    for (SelectablePanel sp : FolderSystemPanel.FolderSystemPanelInstance().panels) {
                        Rectangle compBounds = SwingUtilities.convertRectangle(
                                sp.getParent(), sp.getBounds(), viewport);
                        if (selectionRect.intersects(compBounds)) {
                            if (shiftDownAtStart) {
                                sp.setSelected(!altDownAtStart);
                            } else {
                                if (altDownAtStart) {
                                    sp.setSelected(false);
                                } else if (ctrlDownAtStart) {
                                    sp.setSelected(!sp.isSelected());
                                } else {
                                    sp.setSelected(true);
                                }
                            }
                        }
                    }
                    // Устанавливаем якорный индекс для диапазонного выделения
                    int minIndex = Integer.MAX_VALUE;
                    for (SelectablePanel sp : FolderSystemPanel.FolderSystemPanelInstance().panels) {
                        if (sp.isSelected() && sp.getIndex() < minIndex) {
                            minIndex = sp.getIndex();
                        }
                    }
                    if (minIndex != Integer.MAX_VALUE) {
                        FolderSystemPanel.FolderSystemPanelInstance().anchorIndex = minIndex;
                    }
                    // Сбрасываем выделение
                    selectionRect = null;
                    repaint();
                }
            }
        };
        viewport.addMouseListener(ma);
        viewport.addMouseMotionListener(ma);
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
        return container.getPreferredSize();
    }

    private static Component createSeparator() {
        return new Separator();
    }

    // Вложенные классы для создания панели папки и медиа остаются без изменений.
    class FolderPanel extends SelectablePanel {
        public FolderPanel(int index, FilesDataMap.CatalogData.FilesData.SubFolder folder) {
            super(index, folder);
        }
    }

    class MediaPanel extends SelectablePanel {
        public MediaPanel(int index, MediaData mediaData) {
            super(index, mediaData);
        }
    }
}
