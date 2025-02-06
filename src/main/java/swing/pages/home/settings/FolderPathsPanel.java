package swing.pages.home.settings;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DropTarget;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.List;

// Класс-обёртка для элемента списка
class FolderEntry {
    private final String path;
    private final boolean addButton; // если true – это специальный элемент "Добавить папку"

    public FolderEntry(String path) {
        this.path = path;
        this.addButton = false;
    }

    // Для создания элемента "Добавить папку"
    public FolderEntry(boolean addButton) {
        this.path = null;
        this.addButton = addButton;
    }

    public boolean isAddButton() {
        return addButton;
    }

    public String getPath() {
        return path;
    }

    public String getDisplayName() {
        if (path == null) {
            return "";
        }
        String separator = File.separator;
        // Разбиваем по обратному слешу или прямому слешу
        String[] parts = path.split("\\\\|/");
        if (parts.length < 2) {
            return path;
        }
        StringBuilder sb = new StringBuilder();
        // Идем с конца к началу (реверсируем порядок)
        for (int i = parts.length - 1; i >= 0; i--) {
            String part = parts[i];
            // Если это последняя (фактически изначально первая) часть и она оканчивается на ":", переместим двоеточие в начало
            if (i == 0 && part.endsWith(":")) {
                part = ":" + part.substring(0, part.length() - 1);
            }
            sb.append(part);
            if (i > 0) {
                sb.append(separator);
            }
        }
        return sb.toString();
    }
}

public class FolderPathsPanel extends JPanel {
    // Модель списка хранит объекты FolderEntry
    private static final DefaultListModel<FolderEntry> listModel = new DefaultListModel<>();
    // JList для отображения элементов
    public static final JList<FolderEntry> pathsList = new JList<>(listModel);

    public FolderPathsPanel() {
        super(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Добавляем несколько демонстрационных путей
        for (int i = 0; i < 5; i++) {
            listModel.addElement(new FolderEntry("T:\\DistributeFiles\\Favorites"));
        }
        // Добавляем специальный элемент "Добавить папку"
        listModel.addElement(new FolderEntry(true));

        // Заголовок
        JLabel titleLabel = new JLabel("Список рабочих директорий");
        titleLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 14));
        add(titleLabel, BorderLayout.NORTH);

        // Настраиваем список
        pathsList.setFixedCellHeight(30);
        pathsList.setCellRenderer(new FolderEntryRenderer());

        // Включаем drag&drop для списка
        pathsList.setDropMode(DropMode.INSERT);
        pathsList.setTransferHandler(new TransferHandler() {
            @Override
            public boolean canImport(TransferSupport support) {
                // Поддерживаем только список файлов
                return support.isDataFlavorSupported(DataFlavor.javaFileListFlavor);
            }

            @Override
            public boolean importData(TransferSupport support) {
                if (!canImport(support)) {
                    return false;
                }
                try {
                    @SuppressWarnings("unchecked")
                    List<File> files = (List<File>) support.getTransferable()
                            .getTransferData(DataFlavor.javaFileListFlavor);
                    for (File file : files) {
                        if (file.isDirectory()) {
                            String path = file.getAbsolutePath();
                            // Проверяем, что такой путь ещё не добавлен
                            boolean exists = false;
                            for (int i = 0; i < listModel.getSize(); i++) {
                                FolderEntry fe = listModel.getElementAt(i);
                                if (!fe.isAddButton() && fe.getPath().equals(path)) {
                                    exists = true;
                                    break;
                                }
                            }
                            if (!exists) {
                                // Вставляем новый элемент перед последним элементом "Добавить папку"
                                int addButtonIndex = listModel.getSize() - 1;
                                listModel.add(addButtonIndex, new FolderEntry(path));
                            }
                        }
                    }
                    return true;
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                return false;
            }
        });

        // Обработка нажатия мыши для ячеек списка
        pathsList.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int index = pathsList.locationToIndex(e.getPoint());
                if (index == -1) {
                    return;
                }
                Rectangle cellBounds = pathsList.getCellBounds(index, index);
                Point ptInCell = new Point(e.getX() - cellBounds.x, e.getY() - cellBounds.y);
                FolderEntry entry = listModel.getElementAt(index);
                if (entry.isAddButton()) {
                    // Если клик по элементу "Добавить папку", сразу открываем диалог
                    addNewFolder();
                } else {
                    // Если для обычного элемента нажали в левой области (30px) – удаляем элемент
                    if (ptInCell.x < 30) {
                        listModel.remove(index);
                    }
                }
            }
        });

        // Оборачиваем список в JScrollPane
        JScrollPane scrollPane = new JScrollPane(pathsList);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        add(scrollPane, BorderLayout.CENTER);
    }

    // Открытие диалога выбора папки и добавление новой записи (вставляем перед элементом "Добавить папку")
    private void addNewFolder() {
        JFileChooser chooser = new JFileChooser(FileSystemView.getFileSystemView());
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.setDialogTitle("Выберите папку");

        // Получаем размеры экрана
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();     //todo вывести в main
        // Устанавливаем размер диалога (половина экрана)
        chooser.setPreferredSize(
                new Dimension(screenSize.width / 2, screenSize.height / 2)
        );

        // Показываем диалог по центру экрана
        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            File selectedFolder = chooser.getSelectedFile();
            String path = selectedFolder.getAbsolutePath();

            // Проверяем, что такой путь ещё не добавлен
            for (int i = 0; i < listModel.getSize(); i++) {
                FolderEntry fe = listModel.getElementAt(i);
                if (!fe.isAddButton() && fe.getPath().equals(path)) {
                    return;
                }
            }

            // Вставляем новый элемент перед кнопкой "Добавить папку"
            int addButtonIndex = listModel.getSize() - 1;
            listModel.add(addButtonIndex, new FolderEntry(path));
        }
    }

    // Метод для получения всех путей (без элемента "Добавить папку")
    public java.util.List<String> getAllPaths() {
        java.util.List<String> paths = new java.util.ArrayList<>();
        for (int i = 0; i < listModel.getSize(); i++) {
            FolderEntry fe = listModel.getElementAt(i);
            if (!fe.isAddButton()) {
                paths.add(fe.getPath());
            }
        }
        return paths;
    }

    public java.util.List<String> getAllDisplayNames() {
        java.util.List<String> names = new java.util.ArrayList<>();
        for (int i = 0; i < listModel.getSize(); i++) {
            FolderEntry fe = listModel.getElementAt(i);
            if (!fe.isAddButton()) {
                names.add(fe.getDisplayName());
            }
        }
        return names;
    }

    /**
     * Кастомный рендерер для ячеек JList.
     * Для обычных элементов слева отображается кнопка "-" (зона для клика удаления),
     * а текстом – преобразованный путь.
     * Для специального элемента "Добавить папку" кнопка не отображается,
     * а надпись центрируется.
     */
    private static class FolderEntryRenderer extends JPanel implements ListCellRenderer<FolderEntry> {
        private final JLabel label;
        private final JButton actionButton;

        public FolderEntryRenderer() {
            setLayout(new BorderLayout());
            // Кнопка – фиксированная ширина 30px
            actionButton = new JButton();
            actionButton.setPreferredSize(new Dimension(30, 30));
            actionButton.setFocusable(false);
            actionButton.setMargin(new Insets(0, 5, 0, 5));
            // Метка для отображения текста
            label = new JLabel();
            label.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 0));
            add(actionButton, BorderLayout.WEST);
            add(label, BorderLayout.CENTER);
        }

        @Override
        public Component getListCellRendererComponent(JList<? extends FolderEntry> list, FolderEntry value,
                                                      int index, boolean isSelected, boolean cellHasFocus) {
            if (value.isAddButton()) {
                // Для элемента "Добавить папку" кнопку скрываем
                actionButton.setVisible(false);
                label.setText("Добавить папку");
                label.setHorizontalAlignment(SwingConstants.CENTER);
            } else {
                actionButton.setVisible(true);
                actionButton.setText("-");
                label.setText(value.getDisplayName());
                label.setHorizontalAlignment(SwingConstants.LEFT);
            }
            // Подсветка выбранной ячейки
            if (isSelected) {
                setBackground(list.getSelectionBackground());
                label.setForeground(list.getSelectionForeground());
                actionButton.setBackground(list.getSelectionBackground());
            } else {
                setBackground(list.getBackground());
                label.setForeground(list.getForeground());
                actionButton.setBackground(list.getBackground());
            }
            setOpaque(true);
            return this;
        }
    }
}
