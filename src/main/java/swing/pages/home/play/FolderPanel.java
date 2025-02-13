package swing.pages.home.play;


import swing.objects.JPanelCustom;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

class FolderPanel extends JPanelCustom {
    private String folderName;
    private ImageIcon icon;
    // Ссылка на основную панель (с CardLayout) для переключения между экранами
    private JPanel parentPanel;


    public FolderPanel(String folderName, String iconPath, JPanel parentPanel) {
        super(PanelType.BORDER);
        this.folderName = folderName;
        this.parentPanel = parentPanel;
        // Загрузка иконки (убедитесь, что файл существует)
        this.icon = new ImageIcon(iconPath);
        initialize();
    }

    private void initialize() {
        setPreferredSize(new Dimension(100, 100));
        setBorder(BorderFactory.createLineBorder(Color.GRAY));

        // Метка, отображающая иконку и имя
        JLabel label = new JLabel(folderName, icon, JLabel.CENTER);
        label.setVerticalTextPosition(JLabel.BOTTOM);
        label.setHorizontalTextPosition(JLabel.CENTER);
        add(label, BorderLayout.CENTER);

        // При двойном клике по метке открываем папку
        label.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                openFolder();
            }
        });

    }

    private void openFolder() {
        // Получаем CardLayout из родительской панели
        CardLayout cl = (CardLayout) parentPanel.getLayout();

        // Создаём панель содержимого папки
        JPanel folderContent = new JPanel(new BorderLayout());
        JLabel contentLabel = new JLabel("Содержимое папки: " + folderName, JLabel.CENTER);
        folderContent.add(contentLabel, BorderLayout.CENTER);

        // Кнопка "Назад" для возврата на рабочий стол
        JButton backButton = new JButton("Назад");
        folderContent.add(backButton, BorderLayout.SOUTH);
        backButton.addActionListener(e -> {
            // При нажатии показываем панель "desktop"
            cl.show(parentPanel, "desktop");
        });

        // Добавляем новую панель в родительскую с уникальным именем
        String cardName = folderName + "_content";
        parentPanel.add(folderContent, cardName);

        // Переключаемся на новую панель
        cl.show(parentPanel, cardName);
    }
}