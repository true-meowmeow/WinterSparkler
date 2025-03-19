package swing.pages.home.play;

import core.contentManager.*;
import swing.objects.JPanelCustom;
import swing.objects.WrapLayout;
import swing.pages.home.play.objects.FolderPanel;
import swing.pages.home.play.objects.MediaPanel;

import javax.swing.*;
import java.awt.*;
import java.nio.file.Path;
import java.util.*;

public class FolderSystemPanel extends JPanelCustom {

    String currentFolder;
    JPanelCustom cardPanel = new JPanelCustom(PanelType.CARD); // панель-карточек

    public FolderSystemPanel(FilesDataMap filesDataMap) {
        super(PanelType.BORDER);
        setLayout(new BorderLayout()); // Явно задаем BorderLayout
        // Инициализируем cardPanel с CardLayout
        cardPanel = new JPanelCustom(new CardLayout());
        add(cardPanel, BorderLayout.CENTER); // Добавляем карточки в центр
    }

    // Метод обновления панели управления
    public void updateManagingPanel(FilesDataMap filesDataMap) {
        cardPanel.removeAll();  // Очистка карточек

        JPanelCustom panelMain = new JPanelCustom(PanelType.BORDER, "Y");
        // Пробегаем по корневым папкам и создаём карточки для каждой из них

        for (Map.Entry<Path, FilesDataMap.CatalogData> catalogEntry : filesDataMap.getCatalogDataHashMap().entrySet()) {
            Path rootPath = catalogEntry.getKey();
            FilesDataMap.CatalogData catalogData = catalogEntry.getValue();

            //JPanel rootPanel = createRootPanel(catalogData);
            // Получаем внутреннюю мапу FilesData из CatalogData через публичный геттер
            for (Map.Entry<Path, FilesDataMap.CatalogData.FilesData> fileEntry : catalogData.getFilesDataHashMap().entrySet()) {
                FilesDataMap.CatalogData.FilesData filesData = fileEntry.getValue();


                addCard(createFolderPanel(filesData), fileEntry.getKey());


            }
        }

/*            for (FolderData folder : folderSet) {
                // Можно добавить условие для исключения корневых карточек
                // if (root.getKey().equals(folder.getPathFull())) continue;

                HashSet<FolderData> subFolders = findSubFolders(folder.getFullPath(), folderSet);       //todo вот эти два метода не нужны если исползовать поиск по инфе из даты папки
                HashSet<MediaData> subMedias = findMediaFilesInFolder(
                        folder.getFullPath(),
                        filesDataMap.getMediaFolderDataHashMap().get(root.getKey()).getMediaDataSet()
                );

                // Создаем карточку для текущей папки
                cardPanel.add(initCardPanel(folder, subFolders, subMedias), folder.getFullPath());
            }*//*

            // Добавляем главную панель (например, для корневого меню)
            panelMain.add(new JPanel());
        }*/

        cardPanel.add(panelMain, "MainPanelWS");
        showCard("MainPanelWS");
        // Пример: переход на определённую карточку
        showCard("C:\\Users\\meowmeow\\Music\\testing\\core 1");
        cardPanel.revalidate();
        cardPanel.repaint();
    }

    private void addCard(JPanel panel, Path path) {
        cardPanel.add(panel, path.toString());
    }


    private JPanelCustom createFolderPanel(FilesDataMap.CatalogData.FilesData filesData) {
        JPanelCustom panel = new JPanelCustom(new BorderLayout());

        // Панель управления с кнопками и заголовком
        JPanelCustom controlPanel = new JPanelCustom(new FlowLayout(FlowLayout.LEFT));
        controlPanel.add(backButtonPanel(filesData.getFolderData()));
        controlPanel.add(titlePanel(filesData.getFolderData().getFullPathString()));
        panel.add(controlPanel, BorderLayout.NORTH);

        // Прокручиваемая панель для контента
        JScrollPane contentScroll = new JScrollPane();
        contentScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        contentScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        // Панель контента
        JPanelCustom contentPanel = new JPanelCustom(PanelType.BORDER);
        contentScroll.setViewportView(contentPanel);

        JPanelCustom contentChildPanel = createContentPanel(filesData);
        contentPanel.add(contentChildPanel);

        panel.add(contentScroll, BorderLayout.CENTER);
        return panel;
    }

    private JPanelCustom createContentPanel(FilesDataMap.CatalogData.FilesData filesData) {
        JPanelCustom panel = new JPanelCustom(PanelType.BORDER);
        JPanelCustom contentPanel = new JPanelCustom(PanelType.GRID);

        CoreInsides coreInsides = new CoreInsides(filesData);
        contentPanel.setGridBagConstrains(coreInsides.foldersPanel, coreInsides.mediasPanel);

        panel.add(contentPanel, BorderLayout.CENTER);
        return panel;
    }


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
        button.addActionListener(e -> showCard(folder.getFullPathString())); //todo fixme LATER LATER LATER LATER LATER LATER LATER                  showCard(folder.getLinkParentPathFull()));
        panel.add(button);
        return panel;
    }

    private JPanelCustom titlePanel(String rootPath) {
        JPanelCustom panel = new JPanelCustom(PanelType.FLOW, "LEFT");
        panel.add(new Label(rootPath));
        return panel;
    }

    public void showCard(String path) {
        ((CardLayout) cardPanel.getLayout()).show(cardPanel, path);
    }
}
