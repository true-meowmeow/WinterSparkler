package swing.pages.home.play;

import core.contentManager.FilesDataList;
import swing.objects.JPanelCustom;
import swing.objects.ScrollablePanel;

import javax.swing.*;
import java.awt.*;
import java.util.List;

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



    private JPanelCustom corePanel() {
        JPanelCustom jPanel = new JPanelCustom(PanelType.BORDER);
        jPanel.setAlignmentX(Component.CENTER_ALIGNMENT); // <-- Добавить выравнивание
        jPanel.add(new Label("fdsfsferw"), BorderLayout.CENTER);
        return jPanel;
    }

    JPanel corePanel = new JPanel();
    int i = 11;
    public void updateAudioFiles(FilesDataList filesDataList) {
        contentPanel.removeAll(); // Полностью очищаем контейнер

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));


        for (int j = 0; j < filesDataList.getFilesDataListFiltered().size(); j++) {
            System.out.println(j);
        }

        panel.add(addCorePanel1());
        panel.add(addPanel2());
        panel.add(addPanel2());

        contentPanel.add(panel);
        contentPanel.revalidate(); // Обязательно обновляем!
        contentPanel.repaint();
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

        JLabel longLabel = new JLabel("1111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111");
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