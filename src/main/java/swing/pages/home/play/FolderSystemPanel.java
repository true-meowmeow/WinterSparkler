package swing.pages.home.play;

import core.contentManager.*;
import swing.objects.JPanelCustom;
import swing.objects.WrapLayout;
import swing.objects.selection.*;
import swing.pages.home.play.objects.FolderPanel;
import swing.pages.home.play.objects.MediaPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.nio.file.Path;
import java.util.*;
import java.util.List;

public class FolderSystemPanel extends JPanelCustom {
    private static FolderSystemPanel instance;
    private Component glassPane; // Поле для glassPane


    public static FolderSystemPanel FolderSystemPanelInstance() {
        return instance;
    }

    JPanelCustom cardPanel;


    // Глобальный счётчик для порядка выделения
    public static long globalSelectionCounter = 1;

    // Список панелей
    public ArrayList<SelectablePanel> panels;
    // Якорный индекс для диапазонного выделения (Shift)
    public int anchorIndex = -1;
    // Прямоугольник выделения при drag‑selection по фону
    public Rectangle selectionRect = null;

    // Переменные для группового перетаскивания (ghost‑эффект)
    public Point groupDragStart = null;
    public boolean draggingGroup = false;

    // Правая область – информационное окно (drop target)
    public DropTargetPanel dropTargetPanel;
    // Glass pane для ghost‑эффекта при перетаскивании
    public DragGlassPane dragGlassPane;

    public Component getGlassPane() {
        return glassPane;
    }
    @Override
    public void addNotify() {
        super.addNotify();

        Window window = SwingUtilities.getWindowAncestor(this);
        if (window instanceof JFrame) {
            JFrame frame = (JFrame) window;
            // Устанавливаем glassPane для ghost‑эффекта
            frame.setGlassPane(dragGlassPane);
        }


        // Теперь корневой элемент уже должен быть доступен
        JRootPane rootPane = SwingUtilities.getRootPane(this);
        if (rootPane != null) {
            glassPane = rootPane.getGlassPane();
            // ESC – снимаем выделение
            rootPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                    .put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "clearSelection");
            // Ctrl+D – тоже снимаем выделение
            rootPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                    .put(KeyStroke.getKeyStroke(KeyEvent.VK_D, InputEvent.CTRL_DOWN_MASK), "clearSelection");

            rootPane.getActionMap().put("clearSelection", new AbstractAction() {
                public void actionPerformed(ActionEvent e) {
                    clearSelection();
                    anchorIndex = -1;
                }
            });

            // Ctrl+A – выделять только объекты Person, у которых isFolder == false
            rootPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                    .put(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_DOWN_MASK), "selectNotFolder");
            rootPane.getActionMap().put("selectNotFolder", new AbstractAction() {
                public void actionPerformed(ActionEvent e) {
                    int firstIndex = -1;
                    for (SelectablePanel sp : panels) {
                        if (!sp.getIsFolder()) {
                            sp.setSelected(true);
                            if (firstIndex == -1) {
                                firstIndex = sp.getIndex();
                            }
                        } else {
                            sp.setSelected(false);
                        }
                    }
                    anchorIndex = firstIndex;
                }
            });

            // Ctrl+Shift+A – выделить все панели
            rootPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                    .put(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_DOWN_MASK | InputEvent.SHIFT_DOWN_MASK), "selectAll");
            rootPane.getActionMap().put("selectAll", new AbstractAction() {
                public void actionPerformed(ActionEvent e) {
                    for (SelectablePanel sp : panels) {
                        sp.setSelected(true);
                    }
                    anchorIndex = 0;
                }
            });
        }
    }

    public FolderSystemPanel() {
        super(PanelType.BORDER, "Y");
        instance = this;
        //add(createFrameFolderPanel());
        //add(createFrameFolderBottomPanel());

        dragGlassPane = new DragGlassPane();    // Инициализируем glass pane для ghost‑эффекта
        add(selectionPanel);
    }

    SelectionPanel selectionPanel = new SelectionPanel();

    public void updateManagingPanel(FilesDataMap filesDataMap) {
        //cardPanel.removeAll();  // Очистка карточек

        //setBorder(BorderFactory.createLineBorder(Color.GREEN));

        //todo создать подпанель чтообы очищать её ->
        //JScrollPane selectionScroll = new JScrollPane(selectionPanel);
        //add(selectionScroll);
        panels = new ArrayList<>(12);   //todo создавать на основе размера требуемого hashset или прекратить его использование

        JPanelCustom panelMain = new JPanelCustom(PanelType.BORDER, "Y");   //Это панель со всеми root's
        // Создаем 40 объектов Person с именем и папкой ли это


        // Пробегаем по корневым папкам и создаём карточки для каждой из них


        for (Map.Entry<Path, FilesDataMap.CatalogData> catalogEntry : filesDataMap.getCatalogDataHashMap().entrySet()) {
            FilesDataMap.CatalogData catalogData = catalogEntry.getValue();

            // Получаем внутреннюю мапу FilesData из CatalogData через публичный геттер
            for (Map.Entry<Path, FilesDataMap.CatalogData.FilesData> fileEntry : catalogData.getFilesDataHashMap().entrySet()) {
                FilesDataMap.CatalogData.FilesData filesData = fileEntry.getValue();

                //persons.add(new Person(filesData.getFolderData().getNamePath().toString(), 100, false));


                //addCard(createFolderPanel(filesData), fileEntry.getKey());
            }
        }

        Path path = Path.of("T:\\testing\\core 1");

        selectionPanel.updateSet(filesDataMap.getFilesDataByFullPath(path));
        //cardPanel.add(panelMain, "MainPanelWS");
        //showCard("MainPanelWS");
        // Пример: переход на определённую карточку
        //showCard("C:\\Users\\meowmeow\\Music\\testing\\core 1");
/*        cardPanel.revalidate();
        cardPanel.repaint();*/
    }


    private void addCard(JScrollPane panel, Path path) {
        String key = path.toString();
        cardPanel.add(panel, key);
    }

    private JPanelCustom createFrameFolderPanel() {

        JPanelCustom panel = new JPanelCustom(new FlowLayout(FlowLayout.LEFT));
        panel.add(titlePanel("rootPath\nrootPath\n"));
        panel.setBorder(BorderFactory.createLineBorder(Color.BLUE));
        panel.setMaximumSize(new Dimension(panel.getMaximumSize().width, panel.getPreferredSize().height));     //todo fixme

        return panel;
        //JPanelCustom panel = new JPanelCustom(new BorderLayout());

/*
        // Панель управления с кнопками и заголовком
        JPanelCustom controlPanel = new JPanelCustom(new FlowLayout(FlowLayout.LEFT));
        //controlPanel.add(backButtonPanel(filesData.getFolderData()));
        controlPanel.add(titlePanel("title"));
        panel.add(controlPanel*//*, BorderLayout.NORTH*//*);
*//*        controlPanel.add(backButtonPanel(filesData.getFolderData()));
        controlPanel.add(titlePanel(filesData.getFolderData().getFullPathString()));*//*
        return panel;*/
    }

    private JPanelCustom createFrameFolderBottomPanel() {
        cardPanel = new JPanelCustom(new CardLayout());
        cardPanel.setBorder(BorderFactory.createLineBorder(Color.ORANGE));
        return cardPanel;
    }


/*
    private JScrollPane createFolderPanel(FilesDataMap.CatalogData.FilesData filesData) {
        // Прокручиваемая панель для контента
        JScrollPane contentScroll = new JScrollPane();
        contentScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        contentScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        contentScroll.setViewportView(createContentPanel(filesData));
        return contentScroll;
    }

    private JPanelCustom createContentPanel(FilesDataMap.CatalogData.FilesData filesData) {
        JPanelCustom contentPanel = new JPanelCustom(PanelType.GRID);

        CoreInsides coreInsides = new CoreInsides(filesData);
        contentPanel.setGridBagConstrains(coreInsides.foldersPanel, coreInsides.mediasPanel);

        return contentPanel;
    }
*/


    private class CoreInsides {
        JPanelCustom foldersPanel = new Panel();
        JPanelCustom mediasPanel = new Panel();

        public CoreInsides(FilesDataMap.CatalogData.FilesData filesData) {      //todo передалть под отработанную папочную систему демо

            for (FilesDataMap.CatalogData.FilesData.SubFolder folders : filesData.getFoldersDataHashSet()) {
                foldersPanel.add(new FolderPanel(folders, cardPanel));
            }

            for (MediaData mediaData : filesData.getMediaDataHashSet()) {
                mediasPanel.add(new MediaPanel(mediaData, cardPanel));
            }
        }

        private class Panel extends JPanelCustom {
            public Panel() {
                super(new WrapLayout(FlowLayout.LEFT, 10, 10));
            }
        }
    }


    private JPanelCustom backButtonPanel(FolderData folder) {
        JPanelCustom panel = new JPanelCustom(PanelType.FLOW, "LEFT");
        JButton button = new JButton("back");
        button.addActionListener(e -> showCard(folder.getFullPathString()));             //todo fixme LATER LATER LATER LATER LATER LATER LATER                  showCard(folder.getLinkParentPathFull()));
        panel.add(button);
        return panel;
    }

    private JPanelCustom titlePanel(String rootPath) {
        JPanelCustom panel = new JPanelCustom(PanelType.FLOW, "LEFT");
        panel.add(new Label(rootPath));
        return panel;
    }

    public void showCard(String path) {                                 //todo Установить в changeCard новый viewport & names & links
        ((CardLayout) cardPanel.getLayout()).show(cardPanel, path);
    }

    public void clearSelection() {
        for (SelectablePanel p : panels) {
            p.setSelected(false);
        }
    }

    private int getSelectedCount() {
        int count = 0;
        for (SelectablePanel sp : panels) {
            if (sp.isSelected()) {
                count++;
            }
        }
        return count;
    }

    public void handlePanelClick(SelectablePanel panel, MouseEvent e) {
        boolean ctrl = (e.getModifiersEx() & MouseEvent.CTRL_DOWN_MASK) != 0;
        boolean shift = (e.getModifiersEx() & MouseEvent.SHIFT_DOWN_MASK) != 0;
        boolean alt = (e.getModifiersEx() & MouseEvent.ALT_DOWN_MASK) != 0;
        int index = panel.getIndex();

        if (shift) {
            if (anchorIndex == -1) {
                anchorIndex = index;
                if (alt) {
                    panel.setSelected(false);
                    if (getSelectedCount() == 0) {
                        anchorIndex = -1;
                    }
                } else if (ctrl) {
                    panel.setSelected(!panel.isSelected());
                } else {
                    panel.setSelected(true);
                }
            } else {
                int bestAnchor;
                // Если панель уже выбрана – используем якорь, иначе ищем ближайшую выбранную панель
                if (panel.isSelected()) {
                    bestAnchor = anchorIndex;
                } else {
                    bestAnchor = findNearestSelected(index);
                }
                // Для Ctrl+Shift не переопределяем bestAnchor, а используем найденное значение

                int start = Math.min(bestAnchor, index);
                int end = Math.max(bestAnchor, index);
                for (int i = start; i <= end; i++) {
                    if (alt) {
                        panels.get(i).setSelected(false);
                    } else if (ctrl) {
                        panels.get(i).setSelected(!panels.get(i).isSelected());
                    } else {
                        panels.get(i).setSelected(true);
                    }
                }
                if (alt && getSelectedCount() == 0) {
                    anchorIndex = -1;
                } else {
                    anchorIndex = index;
                }
            }
        } else if (ctrl) {
            boolean newSelection = !panel.isSelected();
            panel.setSelected(newSelection);
            if (newSelection) {
                anchorIndex = index;
            }
        } else {
            clearSelection();
            panel.setSelected(true);
            anchorIndex = index;
        }
    }

    private int findNearestSelected(int targetIndex) {
        int nearest = -1;
        int minDiff = Integer.MAX_VALUE;
        for (SelectablePanel sp : panels) {
            if (sp.isSelected()) {
                int diff = Math.abs(sp.getIndex() - targetIndex);
                if (diff < minDiff) {
                    minDiff = diff;
                    nearest = sp.getIndex();
                }
            }
        }
        return (nearest == -1 ? targetIndex : nearest);
    }

    public void updateAnchorAfterDeselection(int deselectedIndex) {
        int candidateAbove = -1;
        int candidateBelow = -1;
        int minAboveDiff = Integer.MAX_VALUE;
        int minBelowDiff = Integer.MAX_VALUE;
        for (SelectablePanel sp : panels) {
            if (sp.isSelected()) {
                int idx = sp.getIndex();
                if (idx > deselectedIndex) {
                    int diff = idx - deselectedIndex;
                    if (diff < minAboveDiff) {
                        minAboveDiff = diff;
                        candidateAbove = idx;
                    }
                } else if (idx < deselectedIndex) {
                    int diff = deselectedIndex - idx;
                    if (diff < minBelowDiff) {
                        minBelowDiff = diff;
                        candidateBelow = idx;
                    }
                }
            }
        }
        if (candidateAbove == -1 && candidateBelow == -1) {
            anchorIndex = -1;
        } else if (candidateAbove == -1) {
            anchorIndex = candidateBelow;
        } else if (candidateBelow == -1) {
            anchorIndex = candidateAbove;
        } else {
            if (minAboveDiff < minBelowDiff) {
                anchorIndex = candidateAbove;
            } else if (minBelowDiff < minAboveDiff) {
                anchorIndex = candidateBelow;
            } else {
                anchorIndex = candidateAbove; // при равенстве выбираем панель с большим индексом (выше)
            }
        }
    }
}
