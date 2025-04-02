package swing.pages.home.settings;

import core.contentManager.FolderEntities;
import core.contentManager.FolderEntry;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.List;



public class FolderPathsPanel extends JPanel {

    //FolderEntities folderEntities;
    public DefaultListModel<FolderEntry> listModel;
    public JList<FolderEntry> pathsList;

    public FolderPathsPanel(FolderEntities folderEntities) {
        super(new BorderLayout());

        //this.folderEntities = folderEntities;
        this.listModel = folderEntities.listModel;
        this.pathsList = folderEntities.pathsList;


        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        //todo Нужно обработать исключение на отсутствие путей и попытку вызова manage дополнительным экраном что ничего нет
        // Добавляем несколько демонстрационных путей
        folderEntities.addListModel("T:\\testing\\core 1");
        folderEntities.addListModel("T:\\testing\\core 1 — копия");
        folderEntities.addListModel("T:\\testing\\1c4324x2");
        //folderEntities.addListModel("T:\\DistributeFiles");

        // Добавляем специальный элемент "Добавить папку"
        folderEntities.addListModel(true);

        // Заголовок
        JLabel titleLabel = new JLabel("Список рабочих директорий");
        titleLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 14));
        add(titleLabel, BorderLayout.NORTH);

        // Настраиваем список
        folderEntities.pathsList.setFixedCellHeight(30);
        folderEntities.pathsList.setCellRenderer(new FolderEntryRenderer());

        // Включаем drag&drop для списка
        folderEntities.pathsList.setDropMode(DropMode.INSERT);
        folderEntities.pathsList.setTransferHandler(new TransferHandler() {
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
        folderEntities.pathsList.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int index = folderEntities.pathsList.locationToIndex(e.getPoint());
                if (index == -1) {
                    return;
                }
                Rectangle cellBounds = folderEntities.pathsList.getCellBounds(index, index);
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
        JScrollPane scrollPane = new JScrollPane(folderEntities.pathsList);
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
