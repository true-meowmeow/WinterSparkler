package swing.objects.selection;

import core.contentManager.FilesDataMap;
import core.contentManager.MediaData;
import swing.objects.JPanelCustom;

import javax.swing.*;
import javax.swing.plaf.LayerUI;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static swing.pages.home.play.FolderSystemPanel.FolderSystemPanelInstance;

public class SelectionPanel extends JPanelCustom {

    private WrapScrollablePanel container;
    private JScrollPane scrollPane;

    public SelectionPanel() {
        setLayout(new BorderLayout());
        removeAll();

        // Создаём контейнер с WrapLayout внутри ScrollablePanel
        container = new WrapScrollablePanel(new WrapLayout(FlowLayout.LEFT));

        createBorder();


        // Оборачиваем контейнер в JScrollPane
        scrollPane = new JScrollPane(container,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getViewport().setOpaque(false);

        // Если требуется наложение выделения, оборачиваем в JLayer:
        JLayer<JComponent> jlayer = new JLayer<>(scrollPane, new LayerUI<JComponent>() {
            @Override
            public void paint(Graphics g, JComponent c) {
                super.paint(g, c);
                if (FolderSystemPanelInstance().selectionRect != null) {
                    Graphics2D g2 = (Graphics2D) g;
                    g2.setColor(new Color(0, 0, 255, 50));
                    g2.fill(FolderSystemPanelInstance().selectionRect);
                    g2.setColor(Color.BLUE);
                    g2.draw(FolderSystemPanelInstance().selectionRect);
                }
            }
        });

        //scrollPane.setPreferredSize(new Dimension(100,100));



        add(jlayer, BorderLayout.CENTER);
        add(Box.createVerticalGlue(), BorderLayout.SOUTH);

        // Обновление компонентов при изменении размеров окна
// В SelectionPanel.java
// ...
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                // Используем invokeLater для гарантии выполнения в EDT
                // после завершения текущей обработки события изменения размера.
                SwingUtilities.invokeLater(() -> {
                    if (container != null) {
                        // 1. Устанавливаем флаг недействительности компоновки
                        container.invalidate();
                        // 2. Запрашиваем пересчет и применение компоновки
                        container.validate();
                        // --- Или попробуйте принудительное выполнение ---
                        // container.doLayout(); // Принудительно выполняет layoutContainer
                        // 3. Запрашиваем перерисовку
                        container.repaint();
                    }
                    // Перерисовка родителя (если JLayer или фон SelectionPanel важны)
                    SelectionPanel.this.repaint();
                });
            }
        });
// ...
    }

    public void updateSet(FilesDataMap.CatalogData.FilesData filesDataHashSet) {
        // Очищаем содержимое контейнера и списки панелей
        container.removeAll();
        FolderSystemPanelInstance().panels.clear();

        int index = 0;
        // Добавляем панели папок
        for (FilesDataMap.CatalogData.FilesData.SubFolder folder : filesDataHashSet.getFoldersDataHashSet()) {
            FolderPanel fp = new FolderPanel(index++, folder.getName().toString());
            FolderSystemPanelInstance().panels.add(fp);
            container.add(fp);
        }
        // Добавляем сепаратор для завершения строки в WrapLayout
        container.add(createSeparator());
        // Добавляем панели медиа
        for (MediaData media : filesDataHashSet.getMediaDataHashSet()) {
            MediaPanel mp = new MediaPanel(index++, media.getName().toString());
            FolderSystemPanelInstance().panels.add(mp);
            container.add(mp);
        }

        container.revalidate();
        container.repaint();

        // Обработка выделения мышью через viewport
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
                    FolderSystemPanelInstance().clearSelection();
                }
                FolderSystemPanelInstance().selectionRect = new Rectangle(dragStart);
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                if (dragging) {
                    Point current = e.getPoint();
                    FolderSystemPanelInstance().selectionRect.setBounds(
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
                    for (SelectablePanel sp : FolderSystemPanelInstance().panels) {
                        Rectangle compBounds = SwingUtilities.convertRectangle(
                                sp.getParent(), sp.getBounds(), viewport);
                        if (FolderSystemPanelInstance().selectionRect.intersects(compBounds)) {
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
                    int minIndex = Integer.MAX_VALUE;
                    for (SelectablePanel sp : FolderSystemPanelInstance().panels) {
                        if (sp.isSelected() && sp.getIndex() < minIndex) {
                            minIndex = sp.getIndex();
                        }
                    }
                    if (minIndex != Integer.MAX_VALUE) {
                        FolderSystemPanelInstance().anchorIndex = minIndex;
                    }
                    FolderSystemPanelInstance().selectionRect = null;
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
        if (FolderSystemPanelInstance().selectionRect != null) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setColor(new Color(0, 0, 255, 50));
            g2.fill(FolderSystemPanelInstance().selectionRect);
            g2.setColor(Color.BLUE);
            g2.draw(FolderSystemPanelInstance().selectionRect);
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return container.getPreferredSize();
    }

    private static Component createSeparator() {
        return new Separator();
    }
}
