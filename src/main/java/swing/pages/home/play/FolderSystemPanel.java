package swing.pages.home.play;

import core.Main;
import core.contentManager.*;
import swing.objects.JPanelCustom;
import swing.objects.WrapLayout;
import swing.pages.home.play.objects.FolderPanel;
import swing.pages.home.play.objects.MediaPanel;

import javax.swing.*;
import java.awt.*;
import java.util.*;

public class FolderSystemPanel extends JPanelCustom {

    //private String currentRoot = null;
    //private ScrollablePanel contentPanel; // Основной контейнер для панелей


    public FolderSystemPanel(FilesDataMap filesDataMap) {
        super(PanelType.BORDER);
        setLayout(new BorderLayout()); // Явно задаем BorderLayout

        // Инициализируем cardPanel с CardLayout
        cardPanel = new JPanelCustom(new CardLayout());

        add(cardPanel, BorderLayout.CENTER); // Добавляем скролл в центр
    }

    String currentFolder;
    JPanelCustom cardPanel = new JPanelCustom(PanelType.CARD);      //todo изменить все имена в картах чтобы принимали путь корня чтобы избежать ошибки при одинаковом названии папок в разных корнях


    //todo Я не против поместить это в отдельный поток и отображать каждый корень папки по мере загрузки
    //Сейчас есть проблема с большим количеством файлов, он отрабатывает неважно.
    public void updateManagingPanel(FilesDataMap filesDataMap) {
        // Предполагается, что cardPanel и другие UI-компоненты уже объявлены
        cardPanel.removeAll();  // Каждая карта — все фильтрованные файлы по выбранной папке

        JPanelCustom panel = new JPanelCustom(PanelType.BORDER, "Y");

        JPanelCustom panelMain = new JPanelCustom(PanelType.BORDER, "Y");
        for (Map.Entry<String, FilesDataMap.FilesData> root : filesDataMap.getMediaFolderDataHashMap().entrySet()) {

            FilesDataMap.FilesData mfd = root.getValue();
            HashSet<FolderData> folderSet = mfd.getFolderDataSet();

            for (FolderData folder : folderSet) {

                if (root.getKey().equals(folder.getPathFull())) {   //Убирает создание корневых карт
                    //continue;         //todo вернуть когда main карта будет сделана
                }
                HashSet<FolderData> subFolders = findSubFolders(folder.getPathFull(), folderSet);                                                                               //Получаю все папки в текущей папке
                HashSet<MediaData> subMedias = findMediaFilesInFolder(folder.getPathFull(), filesDataMap.getMediaFolderDataHashMap().get(root.getKey()).getMediaDataSet());     //Получаю все аудиофайлы в текущей папке


                //Вызываю метод создания карты, передавая текущий полный путь папки, все папки в ней и все медиафайлы в ней
                cardPanel.add(initCardPanel(folder, subFolders, subMedias), folder.getPathFull());  //Создание панели для каждой папки с файлами


                //todo создать панель main меню
            }

            panelMain.add(new JPanel()/*root.getKey()*/);   //Метод добавления в себя по оси y панелей главных меню
        }
        cardPanel.add(panelMain, "MainPanelWS");
        showCard("MainPanelWS");
        showCard("C:\\Users\\meowmeow\\Music\\testing\\core 1\\");

        // Здесь может идти дальнейшая логика по наполнению панели данными,
        // например, добавление компонентов в panel, затем его размещение в cardPanel
        // cardPanel.add(panel);
        cardPanel.revalidate();
        cardPanel.repaint();
    }



    public HashSet<FolderData> findSubFolders(String fullPath, HashSet<FolderData> folderDataSet) {
        // Если путь не заканчивается на "\", добавляем его
        if (!fullPath.endsWith("\\")) {
            fullPath += "\\";
        }

        HashSet<FolderData> subfolders = new HashSet<>();

        for (FolderData folder : folderDataSet) {
            String folderFull = folder.getPathFull();
            // Проверяем, что папка находится внутри fullPath, но не является самой этой папкой
            if (folderFull.startsWith(fullPath) && !folderFull.equals(fullPath)) {
                // Получаем часть пути, которая идёт после fullPath
                String relativePart = folderFull.substring(fullPath.length());
                // Удаляем завершающий разделитель, если он есть
                if (relativePart.endsWith("\\")) {
                    relativePart = relativePart.substring(0, relativePart.length() - 1);
                }
                // Если оставшийся путь не содержит дополнительных "\",
                // значит папка является непосредственной подпапкой
                if (!relativePart.contains("\\")) {
                    subfolders.add(folder);
                }
            }
        }

        return subfolders;
    }

    public HashSet<MediaData> findMediaFilesInFolder(String folderPath, HashSet<MediaData> mediaDataSet) {
        // Нормализуем путь: если не заканчивается на разделитель, добавляем его
        if (!folderPath.endsWith("\\") && !folderPath.endsWith("/")) {
            folderPath += System.getProperty("file.separator");
        }

        HashSet<MediaData> result = new HashSet<>();
        for (MediaData mediaData : mediaDataSet) {
            // Сравниваем по полному пути папки, в которой находится файл
            if (mediaData.getPathFull().equals(folderPath)) {
                result.add(mediaData);
            }
        }

        return result;//работает?
    }

    //todo String path заменить на метод из String[] разбирая путь на подпути для кнопок           //todo Если deactivated, то переход на следующую папку
    private JPanelCustom initCardPanel(FolderData folder, HashSet<FolderData> subFolders, HashSet<MediaData> subMedias) {
        JPanelCustom panel = new JPanelCustom(new BorderLayout());

        //Control panel for title and buttons
        JPanelCustom controlPanel = new JPanelCustom(new FlowLayout(FlowLayout.LEFT));
        controlPanel.add(backButtonPanel(folder));
        controlPanel.add(titlePanel(folder.getPathFull()));
        panel.add(controlPanel, BorderLayout.NORTH);

        //Content scroll for media files
        JScrollPane contentScroll = new JScrollPane();
        contentScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        contentScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        //Content panel
        JPanelCustom contentPanel = new JPanelCustom(PanelType.BORDER);
        contentScroll.setViewportView(contentPanel);

        //Folder panel

        //System.out.println(folder);
        JPanelCustom contentChildPanel = createContentPanel(subFolders, subMedias);
        contentPanel.add(contentChildPanel);

        //JPanelCustom mediasPanel = createMediasPanel(subMedias);
        //contentPanel.add(mediasPanel);

        //Media panel

        // Основная панель с папками (используем WrapLayout)


        panel.add(contentScroll, BorderLayout.CENTER);
        return panel;
    }


    private JPanelCustom backButtonPanel(FolderData folder) {
        JPanelCustom panel = new JPanelCustom(PanelType.FLOW, "LEFT");
        JButton button = new JButton();

        button.setText("back");
        button.addActionListener(e -> {
            showCard(folder.getLinkParentPathFull());
        });

        panel.add(button);

        return panel;
    }

        //todo В случае работы с производительностью Manage, нужно понять что больше всего кушает ресурсов\
        //todo Второй вариант - Сделать прогрузку всего второй опцией и заменить эту структуру на прогрузку только текущей папки, в таком случае он будет показывать папки если в них нет искаемых файлов

    private JPanelCustom titlePanel(String rootPath) {    //Title panel
        JPanelCustom panel = new JPanelCustom(PanelType.FLOW, "LEFT");
        panel.add(new Label(rootPath));
        return panel;
    }

    public void showCard(String path) {
        ((CardLayout) cardPanel.getLayout()).show(cardPanel, path);
    }

    private String defaultIconPath = "anythingPathToIcon";
    private JPanelCustom createContentPanel(HashSet<FolderData> folders, HashSet<MediaData> media) {
        JPanelCustom panel = new JPanelCustom(PanelType.BORDER);

        JPanelCustom contentPanel = new JPanelCustom(PanelType.GRID);

        //Используем WrapLayout
        JPanelCustom foldersPanel = new JPanelCustom(new WrapLayout(FlowLayout.LEFT, 10, 10));
        JPanelCustom mediasPanel = new JPanelCustom(new WrapLayout(FlowLayout.LEFT, 10, 10));
        foldersPanel.setBorder(BorderFactory.createLineBorder(Color.GREEN));
        mediasPanel.setBorder(BorderFactory.createLineBorder(Color.GREEN));


        for (FolderData folderData : folders) {
            foldersPanel.add(new FolderPanel(folderData, defaultIconPath, cardPanel));
        }
        for (MediaData mediaData : media) {
            mediasPanel.add(new MediaPanel(mediaData, defaultIconPath, cardPanel));
        }
        contentPanel.setGridBagConstrains(foldersPanel, mediasPanel);



        //foldersPanel.setMaximumSize(new Dimension(foldersPanel.getMaximumSize().width, (int) foldersPanel.getPreferredSize().getHeight()));
        //mediasPanel.setMaximumSize(new Dimension(mediasPanel.getMaximumSize().height, (int) mediasPanel.getPreferredSize().getHeight()));
        //contentPanel.add(foldersPanel);
        //contentPanel.add(mediasPanel);
        panel.add(contentPanel, BorderLayout.CENTER);
        return panel;
    }
}