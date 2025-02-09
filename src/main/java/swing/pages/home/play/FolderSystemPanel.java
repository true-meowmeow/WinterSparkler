package swing.pages.home.play;

import core.contentManager.FileData;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class FolderSystemPanel extends JPanel {

    /**
     * Конструктор принимает список найденных файлов.
     */
    public FolderSystemPanel(List<FileData> audioFiles) {
        // Основная панель располагает секции одну под другой.
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        updateAudioFiles(audioFiles);
    }

    /**
     * Обновляет содержимое панели новым списком аудиофайлов.
     */
    public void updateAudioFiles(List<FileData> newAudioFiles) {
        removeAll();

        // Группируем файлы по корневой папке (rootPath)
        Map<String, List<FileData>> rootMap = new LinkedHashMap<>();
        for (FileData file : newAudioFiles) {
            String rootPath = file.getRootPath();
            rootMap.computeIfAbsent(rootPath, k -> new ArrayList<>()).add(file);
        }

        // Для каждого корневого пути создаём отдельную секцию
        for (Map.Entry<String, List<FileData>> entry : rootMap.entrySet()) {
            String rootPath = entry.getKey();
            List<FileData> files = entry.getValue();
            JPanel rootSection = createRootSection(rootPath, files);
            add(rootSection);
        }

        revalidate();
        repaint();
    }

    /**
     * Создает секцию для одного корневого пути.
     * В секции выводятся:
     *  - Заголовок с путём (rootPath)
     *  - Два раздела: "Папки:" и "Аудиофайлы:".
     *    Файлы, у которых relativePath не пустой, группируются по первому компоненту (подпапке)
     *    и отображаются как папки (иконка 📁). Файлы без относительного пути – как аудиофайлы (иконка 🎵).
     */
    private JPanel createRootSection(String rootPath, List<FileData> files) {
        // Панель с заголовком (титул – путь к корневой папке)
        JPanel sectionPanel = new JPanel();
        sectionPanel.setLayout(new BorderLayout());
        sectionPanel.setBorder(BorderFactory.createTitledBorder(rootPath));

        // Панель для содержимого внутри корневой папки,
        // в котором сначала идут плитки для подпапок, затем – для аудиофайлов
        JPanel contentPanel = new JPanel();
        // BoxLayout по вертикали – сначала один раздел, затем другой
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

        // Для файлов, лежащих прямо в корневой папке (relativePath пустой)
        List<FileData> filesInRoot = new ArrayList<>();
        // Для файлов, лежащих в подпапках – группируем по имени первой папки
        Map<String, List<FileData>> subfolderMap = new LinkedHashMap<>();

        for (FileData file : files) {
            String rel = file.getRelativePath();
            if (rel == null) {
                rel = "";
            }
            rel = rel.trim();
            // Убираем завершающий слэш, если он есть
            if (rel.endsWith("/")) {
                rel = rel.substring(0, rel.length() - 1);
            }
            if (rel.isEmpty()) {
                filesInRoot.add(file);
            } else {
                // Берем первую папку в относительном пути
                String[] parts = rel.split("/");
                if (parts.length > 0) {
                    String folderName = parts[0];
                    subfolderMap.computeIfAbsent(folderName, k -> new ArrayList<>()).add(file);
                }
            }
        }

        // Если есть подпапки, создаём для них панель с плитками
        if (!subfolderMap.isEmpty()) {
            JLabel subfoldersLabel = new JLabel("Папки:");
            subfoldersLabel.setAlignmentX(LEFT_ALIGNMENT);
            contentPanel.add(subfoldersLabel);

            // Используем FlowLayout для плиток (они будут располагаться в строке и переходить на новую при нехватке места)
            JPanel foldersPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
            foldersPanel.setAlignmentX(LEFT_ALIGNMENT);
            for (String folderName : subfolderMap.keySet()) {
                JPanel tile = createTile("📁 " + folderName);
                foldersPanel.add(tile);
            }
            contentPanel.add(foldersPanel);
        }

        // Если есть аудиофайлы в корне, создаём для них панель с плитками
        if (!filesInRoot.isEmpty()) {
            JLabel filesLabel = new JLabel("Аудиофайлы:");
            filesLabel.setAlignmentX(LEFT_ALIGNMENT);
            contentPanel.add(filesLabel);

            JPanel filesPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
            filesPanel.setAlignmentX(LEFT_ALIGNMENT);
            for (FileData file : filesInRoot) {
                JPanel tile = createTile("🎵 " + file.getFileName());
                filesPanel.add(tile);
            }
            contentPanel.add(filesPanel);
        }

        sectionPanel.add(contentPanel, BorderLayout.CENTER);
        return sectionPanel;
    }

    /**
     * Создает «плитку» для отображения элемента (папка или аудиофайл).
     * Плитка имеет фиксированный размер и границу.
     */
    private JPanel createTile(String text) {
        JPanel tile = new JPanel(new BorderLayout());
        tile.setPreferredSize(new Dimension(100, 100));
        tile.setMaximumSize(new Dimension(100, 100));
        tile.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        // Метка с иконкой (можно заменить на ImageIcon при наличии файлов с изображениями)
        JLabel iconLabel = new JLabel("", SwingConstants.CENTER);
        iconLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 32));
        // Здесь текст уже содержит эмодзи (📁 или 🎵), поэтому можно и не устанавливать отдельно

        // Метка с текстом (имя элемента)
        JLabel nameLabel = new JLabel(text, SwingConstants.CENTER);
        nameLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        nameLabel.setPreferredSize(new Dimension(100, 20));

        tile.add(iconLabel, BorderLayout.CENTER);
        tile.add(nameLabel, BorderLayout.SOUTH);
        return tile;
    }
}
