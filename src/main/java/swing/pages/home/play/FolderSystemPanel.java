package swing.pages.home.play;

import core.contentManager.FilesData;
import core.contentManager.FilesDataList;
import swing.objects.JPanelCustom;
import swing.objects.ScrollablePanel;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

import static swing.pages.home.play.objects.FolderUtil.getChildFolders;
import static swing.pages.home.play.objects.FolderUtil.removeTrailingSlash;

public class FolderSystemPanel extends JPanelCustom {

    private String currentRoot = null;
    private ScrollablePanel contentPanel; // Основной контейнер для панелей

    public FolderSystemPanel(FilesDataList filesDataList) {
        super(PanelType.BORDER);

        // Создаем основной контейнер с вертикальным BoxLayout
        contentPanel = new ScrollablePanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

        // Помещаем contentPanel в JScrollPane
        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        add(scrollPane, BorderLayout.CENTER);

        updateAudioFiles(new FilesDataList());

        // Добавляем тестовые панели
        //contentPanel.add(corePanel, BorderLayout.CENTER);
    }

    String currentFolder;
    JPanelCustom cardPanel = new JPanelCustom(PanelType.CARD);      //todo изменить все имена в картах чтобы принимали путь корня чтобы избежать ошибки при одинаковом названии папок в разных корнях

    public void updateAudioFiles(FilesDataList filesDataList) {
        contentPanel.removeAll();
        cardPanel.removeAll();
        currentFolder = new String("");


        JPanelCustom panel = new JPanelCustom(PanelType.BORDER, "Y");


        for (int i = 0; i < filesDataList.getFilesDataListFiltered().size(); i++) {
            panel.add(corePanel(filesDataList.getFilesDataListFiltered().get(i)));
        }
        cardPanel.add(panel, "coreMainWinterSparkler");

        showCard("coreMainWinterSparkler");
        //showCard("тест пустой папки/вот эту папку надо/");
        contentPanel.add(cardPanel);
        contentPanel.revalidate();
        contentPanel.repaint();
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

    private JPanelCustom corePanel(FilesData filesData) {
        JPanelCustom panel = new JPanelCustom(PanelType.BORDER, "Y");
        panel.add(titlePanel(filesData.getRootPath()), BorderLayout.NORTH);

        // Получаем подпапки для текущего пути (currentFolder)
        String[] childFolders = getChildFolders(currentFolder, filesData.getPathsHashSet());
        panel.add(createFoldersPanel(childFolders));

        // Добавляем карты для всех нужных путей
        for (String path : filesData.getPathsHashSet()) {
            if (path.equals("")) {
                continue;
            }
            cardPanel.add(innerFolders(filesData, path), path);
        }

        return panel;
    }

    private JPanelCustom innerFolders(FilesData filesData, String folderPath) {

        //todo Добавить кнопку назад и пути перехода назад. + отслеживать fullPath
        JPanelCustom panel = new JPanelCustom(PanelType.BORDER, "Y");
        panel.add(new Label(folderPath));
        //System.out.println(folderPath);

/*        System.out.println(folderPath);
        System.out.println(Arrays.toString(new TreeSet[]{filesData.getPathsHashSet()}));
        System.out.println(Arrays.toString(getChildFolders(folderPath, filesData.getPathsHashSet())));
        System.out.println();*/


        panel.add(createFoldersPanel(
                getChildFolders(folderPath, filesData.getPathsHashSet()))
        );

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