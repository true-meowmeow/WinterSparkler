package swing.pages;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.io.File;


public class PageSettings extends JPanel {
    public PageSettings() {
        initUI();
    }

    private void initUI() {
        setLayout(new BorderLayout());
        add(new JLabel("Settings Page", SwingConstants.CENTER), BorderLayout.CENTER);

        // Добавьте здесь ваши компоненты настроек
        JPanel content = new JPanel();
        content.add(new JCheckBox("Option 1"));
        content.add(new JCheckBox("Option 2"));
        add(content, BorderLayout.NORTH);


        add(new FolderPathsPanel());
    }
}

class FolderPathsPanel extends JPanel {
    private final DefaultListModel<String> listModel = new DefaultListModel<>();
    private final JList<String> pathsList = new JList<>(listModel);

    public FolderPathsPanel() {
        setLayout(new BorderLayout(10, 10));
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