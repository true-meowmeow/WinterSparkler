package swing.pages.home.play;

import core.contentManager.FilesData;
import core.contentManager.FilesDataList;
import swing.objects.JPanelCustom;
import swing.objects.ScrollablePanel;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Objects;

public class FolderSystemPanel extends JPanelCustom {
    //private FilesDataList filesDataList;
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

        JPanelCustom panel = new JPanelCustom(PanelType.BORDER, "Y");


        for (int i = 0; i < filesDataList.getFilesDataListFiltered().size(); i++) {
            panel.add(corePanel(filesDataList.getFilesDataListFiltered().get(i)));
            break;
        }
        contentPanel.add(panel);
        contentPanel.revalidate();
        contentPanel.repaint();
    }



    private JPanelCustom corePanel(FilesData filesData) {
        // Создаем общий контейнер (например, с BorderLayout)
        JPanelCustom panel = new JPanelCustom(PanelType.BORDER, "Y");

        panel.add(titlePanel(filesData.getRootPath()), BorderLayout.NORTH);

        // Создаем панель с CardLayout для навигации между экранами (рабочий стол и открытые папки)
        JPanel cardPanel = new JPanel(new CardLayout());

        // Рабочий стол с иконками папок
        JPanel desktopPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));

        // Создаем несколько панелей-папок
        FolderPanel folder1 = new FolderPanel("Папка 1", "C:/path/to/your/icon1.png", cardPanel);
        FolderPanel folder2 = new FolderPanel("Папка 2", "C:/path/to/your/icon2.png", cardPanel);

        // Добавляем папки на рабочий стол
        desktopPanel.add(folder1);
        desktopPanel.add(folder2);

        // Добавляем рабочий стол в cardPanel с именем "desktop"
        cardPanel.add(desktopPanel, "desktop");

        // Добавляем cardPanel в основной контейнер
        panel.add(cardPanel, BorderLayout.CENTER);

        return panel;
    }


    private JPanel titlePanel(String rootPath) {
        JPanel panel = new JPanel();
        panel.add(new Label(rootPath));
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

    private JPanelCustom addPanel2() {
        JPanelCustom jPanel = new JPanelCustom(PanelType.BORDER);
        jPanel.setBackground(Color.ORANGE);
        jPanel.add(new JLabel("222222222"));
        jPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, jPanel.getPreferredSize().height));
        return jPanel;
    }
}