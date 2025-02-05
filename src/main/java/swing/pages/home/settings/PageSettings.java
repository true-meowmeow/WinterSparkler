package swing.pages.home.settings;

import swing.objects.JPanelCustom;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import static swing.objects.MethodsSwing.newGridBagConstraintsX;


public class PageSettings extends JPanelCustom {
    public PageSettings() {
        super(JPanelCustom.PanelType.GRID, true);

        add(new LeftPanel(), newGridBagConstraintsX(0, 40));
        add(new CenterPanel(), newGridBagConstraintsX(1, 30));
        add(new RightPanel(), newGridBagConstraintsX(2, 130));
    }
}


class LeftPanel extends JPanelCustom {

    public LeftPanel() {
        super(PanelType.BORDER, true);
    }
}

class CenterPanel extends JPanelCustom {

    public CenterPanel() {
        super(PanelType.BORDER, true);

        setBackground(Color.LIGHT_GRAY);
    }
}

class RightPanel extends JPanelCustom {

    public RightPanel() {
        super(PanelType.BORDER, true);


        setBackground(Color.DARK_GRAY);
        add(new FolderPathsPanel(), BorderLayout.NORTH);
    }
}





class FolderPathsPanel extends JPanelCustom {
    // Модель списка, содержащая пути
    private final DefaultListModel<String> listModel = new DefaultListModel<>();
    // JList, использующий настраиваемый рендерер для отображения кнопки удаления справа
    private final JList<String> pathsList = new JList<>(listModel);

    public FolderPathsPanel() {
        super(PanelType.BORDER);

        // Для демонстрации добавляем несколько элементов
        for (int i = 0; i < 15; i++) {
            listModel.addElement("T:\\DistributeFiles\\Favorites");
        }

        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Заголовок
        JLabel titleLabel = new JLabel("Список рабочих директорий");
        titleLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 14));
        add(titleLabel, BorderLayout.NORTH);

        // Настраиваем список
        pathsList.setFixedCellHeight(30);
        // Устанавливаем настраиваемый рендерер ячеек, который добавляет справа кнопку удаления
        pathsList.setCellRenderer(new FolderPathRenderer());
        // Добавляем слушатель мыши для обработки кликов по кнопке удаления
        pathsList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Определяем индекс ячейки, по которой кликнули
                int index = pathsList.locationToIndex(e.getPoint());
                if (index != -1) {
                    Rectangle cellBounds = pathsList.getCellBounds(index, index);
                    Point ptInCell = new Point(e.getX() - cellBounds.x, e.getY() - cellBounds.y);
                    // Предположим, что кнопка занимает правые 30 пикселей ячейки.
                    // Если щелчок произошёл в этой области, удаляем элемент.
                    if (ptInCell.x > cellBounds.width - 30) {
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

        // Панель с кнопкой добавления новой папки
        JButton addButton = new JButton("Добавить папку");
        addButton.addActionListener(e -> addNewFolder());
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(addButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void addNewFolder() {
        JFileChooser chooser = new JFileChooser(FileSystemView.getFileSystemView());
        // Если необходимо, можно задать размер диалога относительно экрана
        // chooser.setPreferredSize(new Dimension((int)(Toolkit.getDefaultToolkit().getScreenSize().width * 0.5),
        //                                        (int)(Toolkit.getDefaultToolkit().getScreenSize().height * 0.5)));
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

    /**
     * Кастомный рендерер для ячеек JList.
     * Каждая ячейка представляет собой панель с меткой (с текстом элемента) и кнопкой "–"
     * справа, которая отображается в фиксированной области (30 пикселей по ширине).
     */
    private static class FolderPathRenderer extends JPanel implements ListCellRenderer<String> {
        private final JLabel label;
        private final JButton deleteButton;

        public FolderPathRenderer() {
            setLayout(new BorderLayout());
            // Инициализируем метку для отображения текста элемента
            label = new JLabel();
            label.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
            // Инициализируем кнопку удаления
            deleteButton = new JButton("-");
            // Фиксированный размер кнопки – 30 пикселей по ширине (высота подбирается автоматически)
            deleteButton.setPreferredSize(new Dimension(30, 30));
            // Убираем фокус и дополнительные отступы у кнопки (так как она не будет нажиматься напрямую)
            deleteButton.setFocusable(false);
            deleteButton.setMargin(new Insets(0, 5, 0, 5));
            // Добавляем компоненты – метка по центру, кнопка справа
            add(label, BorderLayout.CENTER);
            add(deleteButton, BorderLayout.EAST);
        }

        @Override
        public Component getListCellRendererComponent(JList<? extends String> list, String value,
                                                      int index, boolean isSelected, boolean cellHasFocus) {
            label.setText(value);
            // Подсветка фона для выбранной ячейки
            if (isSelected) {
                setBackground(list.getSelectionBackground());
                label.setForeground(list.getSelectionForeground());
            } else {
                setBackground(list.getBackground());
                label.setForeground(list.getForeground());
            }
            // Обеспечиваем непрозрачность, чтобы фон отображался корректно
            setOpaque(true);
            return this;
        }
    }
}