package swing.objects.selection;

import core.contentManager.FilesDataMap;
import core.contentManager.MediaData;
import swing.objects.JPanelCustom;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static swing.pages.home.play.FolderSystemPanel.FolderSystemPanelInstance;


public class SelectionPanel extends JPanelCustom {

    private int PANEL_COUNT;
    private int PANEL_WIDTH = 160;  // Новая фиксированная ширина
    private int PANEL_HEIGHT = 80;  // Высота панели (можно изменить при необходимости)

    private JPanelCustom folderPanel;
    private JPanelCustom mediaPanel;
    private List<JPanel> smallPanels = new ArrayList<>();
    private JScrollPane scrollPane;
    public void updateSet(FilesDataMap.CatalogData.FilesData filesDataHashSet) {
        scrollPane = new JScrollPane();
        folderPanel = new JPanelCustom(PanelType.BORDER);
        folderPanel.setLayout(new GridBagLayout());
        mediaPanel = new JPanelCustom(PanelType.BORDER);
        mediaPanel.setLayout(new GridBagLayout());

        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        JPanel container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        container.add(folderPanel);
        container.add(mediaPanel);


        // Добавляем слушатель изменения размера для ScrollPane
        scrollPane.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                adjustLayout();
            }
        });

        int index = 0;      //wtf is this for?
        for (FilesDataMap.CatalogData.FilesData.SubFolder folder : filesDataHashSet.getFoldersDataHashSet()) {
            PANEL_COUNT++;
            FolderPanel sp = new FolderPanel(index++, folder.getName().toString());
            FolderSystemPanelInstance().panels.add(sp);     // Вроде для учитывании в системе переноса и выделении
            folderPanel.add(sp);
        }
        for (MediaData media : filesDataHashSet.getMediaDataHashSet()) {
            MediaPanel sp = new MediaPanel(index++, media.getName().toString());
            FolderSystemPanelInstance().panels.add(sp);
            mediaPanel.add(sp);
        }
        scrollPane.setViewportView(container);
        add(scrollPane);


        MouseAdapter ma = new MouseAdapter() {
            Point dragStart = null;
            boolean dragging = false;
            boolean ctrlDownAtStart = false;
            boolean shiftDownAtStart = false;
            boolean altDownAtStart = false;

            @Override
            public void mousePressed(MouseEvent e) {
                if (e.getSource() == SelectionPanel.this) {
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
                        if (FolderSystemPanelInstance().selectionRect.intersects(sp.getBounds())) {
                            if (shiftDownAtStart) {
                                if (altDownAtStart) {
                                    sp.setSelected(false);
                                }/* else if (ctrlDownAtStart) {
                                        sp.setSelected(!sp.isSelected());
                                    }*/ else {
                                    sp.setSelected(true);
                                }
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
        addMouseListener(ma);
        addMouseMotionListener(ma);
    }

    private void adjustLayout() {
        // Получаем доступную ширину области просмотра
        int viewportWidth = scrollPane.getViewport().getWidth();

        // Если ширина еще не определена, используем оценочное значение
        if (viewportWidth <= 0) {
            viewportWidth = getWidth() - 30;
        }

        int spacing = 10; //todo Переместить отступ между панелями

        // Вычисляем количество панелей в строке (учитывая отступы)
        int panelsPerRow = Math.max(1, viewportWidth / (PANEL_WIDTH + spacing));

        // Рассчитываем требуемую высоту контента
        int rows = (int) Math.ceil((double) PANEL_COUNT / panelsPerRow);
        int contentHeight = rows * (PANEL_HEIGHT + spacing) + spacing;

        // Обновляем предпочтительный размер mainPanel для правильной работы прокрутки
        folderPanel.setPreferredSize(new Dimension(viewportWidth, contentHeight));

        // Размещаем все панели в сетке
        for (int i = 0; i < smallPanels.size(); i++) {
            JPanel panel = smallPanels.get(i);
            int row = i / panelsPerRow;
            int col = i % panelsPerRow;

            int x = spacing + col * (PANEL_WIDTH + spacing);
            int y = spacing + row * (PANEL_HEIGHT + spacing);

            panel.setBounds(x, y, PANEL_WIDTH, PANEL_HEIGHT);
        }

        // Обновляем интерфейс
        folderPanel.revalidate();
        folderPanel.repaint();

        System.out.println("Resize: Width=" + viewportWidth +
                ", Panels per row=" + panelsPerRow);
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
        return new Dimension(10 + 8 * (80 + 10), 10 + 5 * (80 + 10));
    }
}