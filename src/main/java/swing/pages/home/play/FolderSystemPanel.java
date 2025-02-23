package swing.pages.home.play;

import core.contentManager.FolderData;
import core.contentManager.FolderEntry;
import core.contentManager.MediaData;
import core.contentManager.FilesDataList;
import swing.objects.JPanelCustom;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

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
        //contentPanel.removeAll();
        cardPanel.removeAll();  //Каждая карта - все фильтрованные файлы по выбранной папке

        JPanelCustom panel = new JPanelCustom(PanelType.BORDER, "Y");
/*        for (int i = 0; i < filesDataList.getMediaDataListFiltered().size(); i++) { //Проходит по корневым путям
            //panel.add(corePanel(filesDataList.getFilesDataListFiltered().get(i)));
            //panel.add(initCardPanel(filesDataList.getFilesDataListFiltered().get(i)));



            //Формирует свою карту для каждой папки корневого пути
*//*            for (String path : filesDataList.getMediaDataListFiltered().get(i).getFullPathsHashSet()) {
                cardPanel.add(initCardPanel(findMatchingFiles(path, filesDataList.getMediaDataListFiltered().get(i).getMediaData())), path);

                System.out.println(path);
                //System.out.println(findDirectSubPaths(path, filesDataList.getFolderDataList()));
                System.out.println();
            }*//*
        }*/



        List<String> subPaths = new ArrayList<>();
        for (Map.Entry<String, HashSet<FolderData>> root : filesDataList.getFolderDataMap().entrySet()) {




            for (FolderData folder : root.getValue()) {
                HashSet<FolderData> folderSet = root.getValue();
                //System.out.println(folderSet);


                findSubfolders(folder.getPathFull(), root.getValue());
                System.out.println(folder.getPathFull() + "      |          " + Arrays.toString(findSubfolders(folder.getPathFull(), root.getValue())));



                //subPaths = findDirectSubPaths(folder.getPathFull(), folderSet);
/*                System.out.println(folder.getPathFull());
                System.out.println(folderSet);
                System.out.println();*/
                //findDirectSubPaths(root.getKey(), subPaths);

            }
        }
        //System.out.println(subPaths);


    }
    public String[] findSubfolders(String fullPath, HashSet<FolderData> folderDataSet) {
        // Если путь не заканчивается на "\", добавляем его
        if (!fullPath.endsWith("\\")) {
            fullPath += "\\";
        }

        List<String> subfolderNames = new ArrayList<>();

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
                    subfolderNames.add(folder.getName());
                }
            }
        }

        return subfolderNames.toArray(new String[0]);
    }



    private List<MediaData.MediaFile> findMatchingFiles(String searchText, List<MediaData.MediaFile> files) {
        List<MediaData.MediaFile> matchingFiles = new ArrayList<>();
        for (MediaData.MediaFile file : files) {
            if (file.getPathFull().equals(searchText)) {
                matchingFiles.add(file);
            }
        }

        System.out.println(matchingFiles);
        return matchingFiles;
    }

    private JPanelCustom initCardPanel(List<MediaData.MediaFile> mediaDataList) {           //todo здесь сделать deactivate проверку и реализацию
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


        for (int i = 0; i < filesDataList.getMediaDataListFiltered().size(); i++) {
            panel.add(corePanel(filesDataList.getMediaDataListFiltered().get(i)));
        }
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