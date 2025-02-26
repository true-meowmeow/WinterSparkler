package swing.pages.home.play;

import core.contentManager.*;
import swing.objects.JPanelCustom;

import javax.swing.*;
import java.awt.*;
import java.util.*;

public class FolderSystemPanel extends JPanelCustom {

    //private String currentRoot = null;
    //private ScrollablePanel contentPanel; // Основной контейнер для панелей
    private JScrollPane scrollPane;


    public FolderSystemPanel(FilesDataMap filesDataMap) {
        super(PanelType.BORDER);

        // Создаем основной контейнер с вертикальным BoxLayout
        //contentPanel = new ScrollablePanel();
        //contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));


        scrollPane = new JScrollPane(cardPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        add(scrollPane, BorderLayout.CENTER);

        //updateAudioFiles(new FilesDataList());

        // Добавляем тестовые панели
        //contentPanel.add(corePanel, BorderLayout.CENTER);
    }

    String currentFolder;
    JPanelCustom cardPanel = new JPanelCustom(PanelType.CARD);      //todo изменить все имена в картах чтобы принимали путь корня чтобы избежать ошибки при одинаковом названии папок в разных корнях

    public void updateManagingPanel(FilesDataMap filesDataMap) {
        // Предполагается, что cardPanel и другие UI-компоненты уже объявлены
        cardPanel.removeAll();  // Каждая карта — все фильтрованные файлы по выбранной папке

        JPanelCustom panel = new JPanelCustom(PanelType.BORDER, "Y");

        JPanelCustom panelMain = new JPanelCustom(PanelType.BORDER, "Y");
        for (Map.Entry<String, FilesDataMap.FilesData> root : filesDataMap.getMediaFolderDataHashMap().entrySet()) {

            FilesDataMap.FilesData mfd = root.getValue();
            HashSet<FolderData> folderSet = mfd.getFolderDataSet();

            for (FolderData folder : folderSet) {
                HashSet<FolderData> subFolders = findSubFolders(folder.getPathFull(), folderSet);                                                                               //Получаю все папки в текущей папке
                HashSet<MediaData> subMedias = findMediaFilesInFolder(folder.getPathFull(), filesDataMap.getMediaFolderDataHashMap().get(root.getKey()).getMediaDataSet());     //Получаю все аудиофайлы в текущей папке


                cardPanel.add(initCardPanel(folder.getPathFull(), subFolders, subMedias), folder.getPathFull());  //Создание панели для каждой папки с файлами


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
        // cardPanel.revalidate();
        // cardPanel.repaint();
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

    //todo String path заменить на метод из String[] разбирая путь на подпути для кнопок
    private JPanelCustom initCardPanel(String currentPath, HashSet<FolderData> subFolders, HashSet<MediaData> subMedias) {           //todo здесь сделать deactivate проверку и реализацию
        JPanelCustom panel = new JPanelCustom(PanelType.BORDER, "Y");


        panel.add(titlePanel(currentPath), BorderLayout.NORTH);

/*        // Получаем подпапки для текущего пути (currentFolder)
        String[] childFolders = getChildFolders(currentFolder, filesData.getRelativePathsHashSet());
        panel.add(createFoldersPanel(childFolders));

        // Добавляем карты для всех нужных путей
        for (String path : filesData.getRelativePathsHashSet()) {
            if (path.equals("")) {
                continue;
            }
            cardPanel.add(innerFolders111(filesData, path), path);
        }*/

        return panel;
    }

    private JPanelCustom innerFolders111(MediaData mediaData, String folderPath) {

        //todo Добавить кнопку назад и пути перехода назад. + отслеживать fullPath
        JPanelCustom panel = new JPanelCustom(PanelType.BORDER, "Y");
        panel.add(new Label(folderPath));



/*        panel.add(createFoldersPanel(
                getChildFolders(folderPath, mediaData.getRelativePathsHashSet()))
        );*/

        return panel;
    }

    public void updateAudioFiles(FilesDataMap filesDataMap) {
        //contentPanel.removeAll();
        cardPanel.removeAll();
        currentFolder = new String("");


        JPanelCustom panel = new JPanelCustom(PanelType.BORDER, "Y");


/*        for (int i = 0; i < filesDataList.getMediaDataListFiltered().size(); i++) {
            panel.add(corePanel(filesDataList.getMediaDataListFiltered().get(i)));
        }*/
        cardPanel.add(panel, "coreMainWinterSparkler");

        showCard("coreMainWinterSparkler");
        //showCard("тест пустой папки/вот эту папку надо/");
        //contentPanel.add(cardPanel);
        //contentPanel.revalidate();
        //contentPanel.repaint();
    }

    public void showCard(String path) {
        //this.currentFolder = path; // Обновляем текущий путь    //todo check надо ли?
        ((CardLayout) cardPanel.getLayout()).show(cardPanel, path);
    }


    private JPanel titlePanel(String rootPath) {    //Title panel
        JPanel panel = new JPanel();
        panel.add(new Label(rootPath));
        return panel;
    }

    private JPanelCustom corePanel(MediaData mediaData) {
        JPanelCustom panel = new JPanelCustom(PanelType.BORDER, "Y");
/*        panel.add(titlePanel(mediaData.getRootPath()), BorderLayout.NORTH);

        // Получаем подпапки для текущего пути (currentFolder)
        String[] childFolders = getChildFolders(currentFolder, mediaData.getRelativePathsHashSet());
        panel.add(createFoldersPanel(childFolders));

        // Добавляем карты для всех нужных путей
        for (String path : mediaData.getRelativePathsHashSet()) {
            if (path.equals("")) {
                continue;
            }
            cardPanel.add(innerFolders(mediaData, path), path);
        }*/

        return panel;
    }

    private JPanelCustom innerFolders(MediaData mediaData, String folderPath) {

        //todo Добавить кнопку назад и пути перехода назад. + отслеживать fullPath
        JPanelCustom panel = new JPanelCustom(PanelType.BORDER, "Y");
        panel.add(new Label(folderPath));


        return panel;
    }

    // у меня должен быть список конечных папок и если

    //todo короче надо всю структуру переписывать, это лажа какая-то, он пиздец короче удачи - дада уже переписал почти блять как будто мне делать нехер больше

    private JPanelCustom createFoldersPanel(String[] folders) {
        JPanelCustom panel = new JPanelCustom(PanelType.BORDER);
        JPanelCustom foldersPanel = new JPanelCustom(new FlowLayout(FlowLayout.LEFT, 10, 10));

        String defaultIconPath = "anythingPathToIcon";

        System.out.println(Arrays.toString(folders));
        for (String folder : folders) {
            String fullPath = currentFolder.isEmpty() ? folder : currentFolder + "/" + folder;
            foldersPanel.add(new FolderPanel(folder, defaultIconPath, this.cardPanel, fullPath));
        }
        //System.out.println();


        panel.add(foldersPanel, BorderLayout.CENTER);

        return panel;
    }
}