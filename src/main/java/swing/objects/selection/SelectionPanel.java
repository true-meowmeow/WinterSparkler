package swing.objects.selection;

import core.contentManager.FilesDataMap;
import core.contentManager.MediaData;
import swing.objects.*;
import swing.objects.general.JPanelCustom;
import swing.objects.general.SmoothScrollPane;
import swing.ui.pages.home.play.ManagePanel;
import swing.ui.pages.home.play.SelectablePanel;

import javax.swing.*;
import javax.swing.plaf.LayerUI;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.nio.file.Path;
import java.util.Map;

public class SelectionPanel extends JPanelCustom {

    private JPanelCustom container;
    private JScrollPane scrollPane;
    // Удаляем локальное поле selectionRect. Теперь оно содержится в SelectionHandler.
    private SelectionHandler selectionHandler;
    private final static int scrollSpeedThrottle = 60; // delay in milli seconds

    public SelectionPanel() {
        // Используем BorderLayout для корректного размещения компонентов
        setLayout(new BorderLayout());

        container = new JPanelCustom(PanelType.WRAP, "LEFT");
        scrollPane = new SmoothScrollPane(container);
        scrollPane.getViewport().setOpaque(false);


        // Создаём JLayer для наложения эффекта выделения, используя информацию из selectionHandler
        selectionHandler = new SelectionHandler(scrollPane.getViewport());
        JLayer<JComponent> jlayer = new JLayer<>(scrollPane, new LayerUI<JComponent>() {
            @Override
            public void paint(Graphics g, JComponent c) {
                super.paint(g, c);
                Rectangle selRect = selectionHandler.getSelectionRect();
                if (selRect != null) {
                    Graphics2D g2 = (Graphics2D) g;
                    g2.setColor(new Color(0, 0, 255, 50));
                    g2.fill(selRect);
                    g2.setColor(Color.BLUE);
                    g2.draw(selRect);
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

        // Регистрируем обработчики мыши через выделенный класс
        addMouseHandlers();
    }

    /**
     * Метод updateSet только обновляет содержимое контейнера,
     * а обработчики мыши уже зарегистрированы в конструкторе.
     */
    public void updateSet(FilesDataMap.CatalogData.FilesData filesDataHashSet) {
        container.removeAll();
        ManagePanel.FolderSystemPanelInstance().panels.clear();

        int index = 0;
        // Добавляем панели папок
        for (FilesDataMap.CatalogData.FilesData.SubFolder folder : filesDataHashSet.getFoldersDataHashSet()) {
            FolderPanel fp = new FolderPanel(index++, folder);
            ManagePanel.FolderSystemPanelInstance().panels.add(fp);
            container.add(fp);
        }
        // Добавляем сепаратор для завершения строки в WrapLayout
        container.add(createSeparator());
        // Добавляем панели медиа
        for (MediaData media : filesDataHashSet.getMediaDataHashSet()) {
            MediaPanel mp = new MediaPanel(index++, media);
            ManagePanel.FolderSystemPanelInstance().panels.add(mp);
            container.add(mp);
        }

        container.revalidate();
        container.repaint();
    }

    public void updateSetHome(FilesDataMap filesDataMap) {
        container.removeAll();
        ManagePanel.FolderSystemPanelInstance().panels.clear();

        int index = 0;
        outerLoop:
        for (Map.Entry<Path, FilesDataMap.CatalogData> entry : filesDataMap.getCatalogDataHashMap().entrySet()) {
            Path path = entry.getKey();
            FilesDataMap.CatalogData catalogData = entry.getValue();

            if (catalogData.getFilesDataHashMap().size() <= 1) {
                for (FilesDataMap.CatalogData.FilesData filesData : catalogData.getFilesDataHashMap().values()) {
                    if (filesData.getMediaDataHashSet().size() <= 0 && filesData.getFoldersDataHashSet().size() <= 0) {
                        continue outerLoop;
                    }
                }
            }

            JLabel name = new JLabel(String.valueOf(path));
            container.add(name);
            container.add(createSeparator());

            FilesDataMap.CatalogData.FilesData filesDataHashSet = catalogData.getFilesDataWithPath(path);

            // Добавляем панели папок
            for (FilesDataMap.CatalogData.FilesData.SubFolder folder : filesDataHashSet.getFoldersDataHashSet()) {
                FolderPanel fp = new FolderPanel(index++, folder);
                ManagePanel.FolderSystemPanelInstance().panels.add(fp);
                container.add(fp);
            }
            // Добавляем сепаратор для завершения строки в WrapLayout
            container.add(createSeparator());
            // Добавляем панели медиа
            for (MediaData media : filesDataHashSet.getMediaDataHashSet()) {
                MediaPanel mp = new MediaPanel(index++, media);
                ManagePanel.FolderSystemPanelInstance().panels.add(mp);
                container.add(mp);
            }

            container.add(createSeparator());
        }
        container.revalidate();
        container.repaint();
    }

    /**
     * Регистрирует обработчики мыши в viewport, используя выделенный класс SelectionHandler.
     */
    private void addMouseHandlers() {
        JViewport viewport = scrollPane.getViewport();
        viewport.addMouseListener(selectionHandler);
        viewport.addMouseMotionListener(selectionHandler);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Дополнительно можно отрисовывать выделение на уровне панели,
        // если это необходимо. Обычно выделение отрисовывается в LayerUI.
        Rectangle selRect = selectionHandler.getSelectionRect();
        if (selRect != null) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setColor(new Color(0, 0, 255, 50));
            g2.fill(selRect);
            g2.setColor(Color.BLUE);
            g2.draw(selRect);
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return container.getPreferredSize();
    }

    private static Component createSeparator() {
        return new Separator();
    }

    private static Component createSeparatorHome() {
        return new Separator(true);
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
