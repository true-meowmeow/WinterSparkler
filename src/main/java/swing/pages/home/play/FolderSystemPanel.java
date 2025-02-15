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

    JPanel corePanel = new JPanel();
    public void updateAudioFiles(FilesDataList filesDataList) {
        contentPanel.removeAll(); // Полностью очищаем контейнер
        currentFolder = new String("");

        JPanelCustom panel = new JPanelCustom(PanelType.BORDER, "Y");


        for (int i = 0; i < filesDataList.getFilesDataListFiltered().size(); i++) {
            panel.add(corePanel(filesDataList.getFilesDataListFiltered().get(i)));
            //System.exit(1);
        }
        contentPanel.add(panel);
        contentPanel.revalidate();
        contentPanel.repaint();
    }


    String currentFolder;
    private JPanelCustom corePanel(FilesData filesData) {       //Core folder panel
        JPanelCustom panel = new JPanelCustom(PanelType.BORDER, "Y");

        panel.add(titlePanel(filesData.getRootPath()), BorderLayout.NORTH);


        panel.add(createFoldersPanel(
                getChildFolders(currentFolder, filesData.getPathsHashSet()))
        );

        return panel;
    }

    private JPanel titlePanel(String rootPath) {    //Title panel
        JPanel panel = new JPanel();
        panel.add(new Label(rootPath));
        return panel;
    }

    private void initManage() {

    }

    private JPanelCustom createFoldersPanel(String[] folders) {
        JPanelCustom panel = new JPanelCustom(PanelType.BORDER);

        JPanelCustom cardPanel = new JPanelCustom(PanelType.CARD);
        JPanelCustom foldersPanel = new JPanelCustom(new FlowLayout(FlowLayout.LEFT, 10, 10));

        cardPanel.add(foldersPanel, "coresMainWS");
        String defaultIconPath = "anythingPathToIcon";

        for (String folder : folders) {
            foldersPanel.add(new FolderPanel(removeTrailingSlash(folder), defaultIconPath, cardPanel));
        }

        // Добавляем cardPanel в основной контейнер
        panel.add(cardPanel, BorderLayout.CENTER);

        return panel;
    }

    private JPanelCustom addCorePanel1() {
        JPanelCustom jPanel = new JPanelCustom(PanelType.BORDER);
        jPanel.setBackground(Color.WHITE);
        jPanel.setLayout(new GridBagLayout()); // GridBagLayout для гибкости

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = GridBagConstraints.REMAINDER; // Занимает всю строку
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        JLabel longLabel = new JLabel("String.valueOf(i)");
        jPanel.add(longLabel, gbc); // Добавляем длинный текст

        gbc.gridy++; // Следующий ряд
        gbc.gridwidth = 1; // Теперь компоненты идут по отдельности
        jPanel.add(new JLabel("sa"), gbc);
        gbc.gridx++; // Следующая колонка
        jPanel.add(new JLabel("sa"), gbc);
        jPanel.add(new JLabel("sa"), gbc);

        return jPanel;
    }
}