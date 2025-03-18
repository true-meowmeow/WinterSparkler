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
            System.out.println("Root path: " + rootPath);

            // Получаем внутреннюю мапу FilesData из CatalogData через публичный геттер
            for (Map.Entry<Path, FilesDataMap.CatalogData.FilesData> fileEntry : catalogData.getFilesDataHashMap().entrySet()) {
                Path folderPath = fileEntry.getKey();
                FilesDataMap.CatalogData.FilesData filesData = fileEntry.getValue();
                System.out.println("  Folder: " + folderPath);
                System.out.println("    FolderData: " + filesData.getFolderData());

                // Выводим информацию о медиа-данных
                for (MediaData mediaData : filesData.getMediaDataHashSet()) {
                    System.out.println("      MediaData: " + mediaData);
                }

                // Выводим информацию о вложенных папках
                for (FilesDataMap.CatalogData.FilesData.SubFolder subFolder : filesData.getFoldersDataHashSet()) {
                    System.out.println("      SubFolder: " + subFolder.getName() +
                            " (path: " + subFolder.getPath() + ")");
                }
                System.out.println();
                System.out.println();
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
        showCard("C:\\Users\\meowmeow\\Music\\testing\\core 1\\");
        cardPanel.revalidate();
        cardPanel.repaint();
    }

/*    public HashSet<FolderData> findSubFolders(Path fullPath, HashSet<FolderData> folderDataSet) {
*//*        if (!fullPath.endsWith("\\")) {
            fullPath += "\\";
        }*//*
*//*         HashSet<FolderData> subfolders = new HashSet<>();
        for (FolderData folder : folderDataSet) {
            Path folderFull = folder.getPathFull();
            if (folderFull.startsWith(fullPath) && !folderFull.equals(fullPath)) {
               String relativePart = folderFull.substring(fullPath.length());
                if (relativePart.endsWith("\\")) {
                    relativePart = relativePart.substring(0, relativePart.length() - 1);
                }
                if (!relativePart.contains("\\")) {
                    subfolders.add(folder);
                }
            }
        }
        return subfolders;*//*
        return new HashSet<FolderData>();
    }*/

/*
    public HashSet<MediaData> findMediaFilesInFolder(Path folderPath, HashSet<MediaData> mediaDataSet) {
        if (!folderPath.endsWith("\\") && !folderPath.endsWith("/")) {
            //folderPath += System.getProperty("file.separator");
        }
        HashSet<MediaData> result = new HashSet<>();
        for (MediaData mediaData : mediaDataSet) {
            if (mediaData.getFullPath().equals(folderPath)) {
                result.add(mediaData);
            }
        }
        return result;
    }
*/

    private JPanelCustom initCardPanel(FolderData folder, HashSet<FolderData> subFolders, HashSet<MediaData> subMedias) {
        JPanelCustom panel = new JPanelCustom(new BorderLayout());

        // Панель управления с кнопками и заголовком
        JPanelCustom controlPanel = new JPanelCustom(new FlowLayout(FlowLayout.LEFT));
        controlPanel.add(backButtonPanel(folder));
        controlPanel.add(titlePanel(folder.getFullPath()));
        panel.add(controlPanel, BorderLayout.NORTH);

        // Прокручиваемая панель для контента
        JScrollPane contentScroll = new JScrollPane();
        contentScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        contentScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        // Панель контента
        JPanelCustom contentPanel = new JPanelCustom(PanelType.BORDER);
        contentScroll.setViewportView(contentPanel);

        JPanelCustom contentChildPanel = createContentPanel(subFolders, subMedias);
        contentPanel.add(contentChildPanel);

        panel.add(contentScroll, BorderLayout.CENTER);
        return panel;
    }

    private JPanelCustom backButtonPanel(FolderData folder) {
        JPanelCustom panel = new JPanelCustom(PanelType.FLOW, "LEFT");
        JButton button = new JButton("back");
        button.addActionListener(e -> showCard(folder.getLinkParentPathFull().toString())); //todo redo to Path
        panel.add(button);
        return panel;
    }

    private JPanelCustom titlePanel(Path rootPath) {
        JPanelCustom panel = new JPanelCustom(PanelType.FLOW, "LEFT");
        panel.add(new Label(rootPath.toString()));  //todo redo to Path
        return panel;
    }

    // Перед переключением карточки сбрасываем выделение как медиа, так и папок
    public void showCard(String path) {
        ((CardLayout) cardPanel.getLayout()).show(cardPanel, path);
    }

    private String defaultIconPath = "anythingPathToIcon";

    private JPanelCustom createContentPanel(HashSet<FolderData> folders, HashSet<MediaData> media) {
        JPanelCustom panel = new JPanelCustom(PanelType.BORDER);
        JPanelCustom contentPanel = new JPanelCustom(PanelType.GRID);

        CoreInsides coreInsides = new CoreInsides(folders, media);
        contentPanel.setGridBagConstrains(coreInsides.foldersPanel, coreInsides.mediasPanel);

        panel.add(contentPanel, BorderLayout.CENTER);
        return panel;
    }

    private class CoreInsides {
        JPanelCustom foldersPanel = new Panel();
        JPanelCustom mediasPanel = new Panel();
        HashSet<FolderPanel> folderPanels = new HashSet<>();
        HashSet<MediaPanel> mediaPanels = new HashSet<>();

        public CoreInsides(HashSet<FolderData> folders, HashSet<MediaData> media) {
            for (FolderData folderData : folders) {
                folderPanels.add(new FolderPanel(folderData, defaultIconPath, cardPanel));
            }
            for (MediaData mediaData : media) {
                mediaPanels.add(new MediaPanel(mediaData, defaultIconPath, cardPanel));
            }
            for (FolderPanel folderPanel : folderPanels) {
                foldersPanel.add(folderPanel);
            }
            for (MediaPanel mediaPanel : mediaPanels) {
                mediasPanel.add(mediaPanel);
            }
        }

        private class Panel extends JPanelCustom {
            public Panel() {
                super(new WrapLayout(FlowLayout.LEFT, 10, 10));
            }
        }
    }
}
