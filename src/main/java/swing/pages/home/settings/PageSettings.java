package swing.pages.home.settings;

import swing.objects.JPanelCustom;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.io.File;

import static swing.objects.MethodsSwing.newGridBagConstraintsX;


public class PageSettings extends JPanelCustom {
    public PageSettings() {
        super(JPanelCustom.PanelType.GRID, true);

        add(new LeftPanel(), newGridBagConstraintsX(0, 40));
        add(new CenterPanel(), newGridBagConstraintsX(1, 30));
        add(new RightPanel(), newGridBagConstraintsX(2, 30));
    }
}


class LeftPanel extends JPanelCustom {

    public LeftPanel() {
        super(PanelType.BORDER);
    }
}

class CenterPanel extends JPanelCustom {

    public CenterPanel() {
        super(PanelType.BORDER);

        setBackground(Color.LIGHT_GRAY);
    }
}

class RightPanel extends JPanelCustom {

    public RightPanel() {
        super(PanelType.BORDER);


        add(new FolderPathsPanel(), BorderLayout.NORTH);
    }
}






class FolderPathsPanel extends JPanelCustom {
    private final DefaultListModel<String> listModel = new DefaultListModel<>();
    private final JList<String> pathsList = new JList<>(listModel);

    public FolderPathsPanel() {
        super(PanelType.BORDER);

        listModel.addElement("T:\\DistributeFiles\\Favorites");

        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Заголовок
        JLabel titleLabel = new JLabel("Список рабочих директорий");
        titleLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 14));
        add(titleLabel, BorderLayout.NORTH);

        // Список с прокруткой
        JScrollPane scrollPane = new JScrollPane(pathsList);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        add(scrollPane, BorderLayout.CENTER);

        // Панель с кнопкой добавления
        JButton addButton = new JButton("Добавить папку");
        addButton.addActionListener(e -> addNewFolder());

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(addButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Настройки внешнего вида
        pathsList.setFixedCellHeight(30);
        pathsList.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                label.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
                return label;
            }
        });
    }

    private void addNewFolder() {
        JFileChooser chooser = new JFileChooser(FileSystemView.getFileSystemView());
        // Устанавливаем предпочтительный размер окна выбора папки
        chooser.setPreferredSize(new Dimension(1400, 700));     //todo настроить под размер окна или 1/2 от размеров экрана
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.setDialogTitle("Выберите папку");

        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File selectedFolder = chooser.getSelectedFile();
            String path = selectedFolder.getAbsolutePath();
            if (!listModel.contains(path)) {
                listModel.addElement(path);
            }
        }
    }


    // Метод для получения всех путей
    public java.util.List<String> getAllPaths() {
        return java.util.Collections.list(listModel.elements());
    }
}