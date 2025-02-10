package swing.pages.home.play;

import core.contentManager.FilesDataList;
import swing.objects.JPanelCustom;
import swing.objects.ScrollablePanel;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class FolderSystemPanel extends JPanelCustom {
    private FilesDataList filesDataList;
    private String currentRoot = null;
    private ScrollablePanel contentPanel; // Основной контейнер для панелей

    public FolderSystemPanel(FilesDataList filesDataList) {
        super(PanelType.BORDER);
        this.filesDataList = filesDataList;

        // Создаем основной контейнер с вертикальным BoxLayout
        contentPanel = new ScrollablePanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

        // Помещаем contentPanel в JScrollPane
        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        add(scrollPane, BorderLayout.CENTER);

        // Добавляем тестовые панели
        contentPanel.add(addCorePanel("hihi"));
        contentPanel.add(addPanel2());
    }


    public void updateAudioFiles(FilesDataList filesDataList) {
        this.filesDataList = filesDataList;


        //todo Сделать метод который разбирает этот лист для кормления в новый интерфейс
    }


    private JPanelCustom addCorePanel(String corePathName) {
        JPanelCustom jPanel = new JPanelCustom(PanelType.BORDER);
        jPanel.setBackground(Color.WHITE);
        jPanel.setLayout(new GridBagLayout()); // GridBagLayout для гибкости

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = GridBagConstraints.REMAINDER; // Занимает всю строку
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        JLabel longLabel = new JLabel("1111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111");
        jPanel.add(longLabel, gbc); // Добавляем длинный текст

        gbc.gridy++; // Следующий ряд
        gbc.gridwidth = 1; // Теперь компоненты идут по отдельности

        jPanel.add(new JLabel("sa"), gbc);
        gbc.gridx++; // Следующая колонка
        jPanel.add(new JLabel("sa"), gbc);
        gbc.gridx++;
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