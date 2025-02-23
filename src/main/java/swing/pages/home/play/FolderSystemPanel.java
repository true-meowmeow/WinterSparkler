package swing.pages.home.play;

import core.contentManager.*;
import swing.objects.JPanelCustom;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

import static swing.pages.home.play.objects.FolderUtil.getChildFolders;

public class FolderSystemPanel extends JPanelCustom {

    private String currentRoot = null;
    //private ScrollablePanel contentPanel; // Основной контейнер для панелей
    private JScrollPane scrollPane;


    public FolderSystemPanel(FilesDataList filesDataList) {
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

    public void updateManagingPanel(FilesDataList filesDataList) {
        // Предполагается, что cardPanel и другие UI-компоненты уже объявлены
        cardPanel.removeAll();  // Каждая карта — все фильтрованные файлы по выбранной папке

        JPanelCustom panel = new JPanelCustom(PanelType.BORDER, "Y");


        List<String> subPaths = new ArrayList<>();
        for (Map.Entry<String, FilesDataList.MediaFolderData> entry : filesDataList.getMediaFolderDataHashMap().entrySet()) {
            FilesDataList.MediaFolderData mfd = entry.getValue();
            HashSet<FolderData> folderSet = mfd.getFolderDataSet();

            System.out.println();
            System.out.println(entry.getKey());
            System.out.println();
            for (FolderData folder : folderSet) {

                System.out.println(folder.getPathFull());




                // Здесь вызываем метод поиска подпапок для текущей папки.
                // Предполагается, что метод findSubfolders возвращает, например, HashSet<FolderData>
                HashSet<FolderData> subfolders = findSubfolders(folder.getPathFull(), folderSet);
                // Можно, например, собрать пути найденных подпапок для дальнейшей работы
                subPaths.addAll(subfolders.stream().map(FolderData::getPathFull).collect(Collectors.toList()));
            }
        }

        // Здесь может идти дальнейшая логика по наполнению панели данными,
        // например, добавление компонентов в panel, затем его размещение в cardPanel
        // cardPanel.add(panel);
        // cardPanel.revalidate();
        // cardPanel.repaint();
    }

    public HashSet<FolderData> findSubfolders(String fullPath, HashSet<FolderData> folderDataSet) {
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



    private JPanelCustom initCardPanel(HashSet<FolderData> folders/*, List<MediaData.MediaFile> mediaDataList*/) {           //todo здесь сделать deactivate проверку и реализацию
        JPanelCustom panel = new JPanelCustom(PanelType.BORDER, "Y");

        //System.out.println(mediaDataList);


        //System.out.println(filesData);
        //System.out.println(filesData);
/*        panel.add(titlePanel(filesData.getRootPath()), BorderLayout.NORTH);

        // Получаем подпапки для текущего пути (currentFolder)
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

    public void updateAudioFiles(FilesDataList filesDataList) {
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
        this.currentFolder = path; // Обновляем текущий путь    //todo check надо ли?
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

    //todo короче надо всю структуру переписывать, это лажа какая-то, он пиздец короче удачи

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