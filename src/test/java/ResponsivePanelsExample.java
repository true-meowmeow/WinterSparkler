import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * Демонстрация адаптивного размещения множества JPanels
 * внутри контейнера при изменении размера окна
 */
public class ResponsivePanelsExample extends JFrame {

    private static final int PANEL_COUNT = 50;
    private static final int PANEL_WIDTH = 160;  // Новая фиксированная ширина
    private static final int PANEL_HEIGHT = 80;  // Высота панели (можно изменить при необходимости)
    private static final Color[] COLORS = {
            Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW,
            Color.ORANGE, Color.PINK, Color.CYAN, Color.MAGENTA,
            new Color(128, 0, 128), new Color(165, 42, 42)
    };

    private final JPanel mainPanel;
    private final List<JPanel> smallPanels = new ArrayList<>();
    private JScrollPane scrollPane;

    public ResponsivePanelsExample() {
        setTitle("Responsive Panels Example");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(400, 300));
        setPreferredSize(new Dimension(800, 600));

        // Создаем основную панель с использованием null layout для ручного позиционирования
        mainPanel = new JPanel();
        mainPanel.setLayout(null);

        // Создаем 50 панелей фиксированного размера PANEL_WIDTH x PANEL_HEIGHT
        for (int i = 0; i < PANEL_COUNT; i++) {
            JPanel panel = createSmallPanel(i);
            smallPanels.add(panel);
            mainPanel.add(panel);
        }

        // Добавляем полосы прокрутки для доступа ко всем панелям
        scrollPane = new JScrollPane(mainPanel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        // Добавляем слушатель изменения размера для ScrollPane
        mainPanel.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                adjustLayout();
            }
        });

        getContentPane().add(scrollPane);
        pack();
        setLocationRelativeTo(null);

        // Гарантируем правильную начальную компоновку
        SwingUtilities.invokeLater(this::adjustLayout);
    }

    /**
     * Создает маленькую панель заданного размера с номером и цветом
     */
    private JPanel createSmallPanel(int index) {
        JPanel panel = new JPanel() {
            @Override
            public Dimension getPreferredSize() {
                return new Dimension(PANEL_WIDTH, PANEL_HEIGHT);
            }

            @Override
            public Dimension getMinimumSize() {
                return new Dimension(PANEL_WIDTH, PANEL_HEIGHT);
            }

            @Override
            public Dimension getMaximumSize() {
                return new Dimension(PANEL_WIDTH, PANEL_HEIGHT);
            }
        };

        panel.setBackground(COLORS[index % COLORS.length]);

        // Добавляем метку с номером для идентификации
        JLabel label = new JLabel(String.valueOf(index + 1));
        label.setFont(new Font("Arial", Font.BOLD, 16));
        label.setForeground(Color.WHITE);
        label.setHorizontalAlignment(SwingConstants.CENTER);

        panel.setLayout(new GridBagLayout());
        panel.add(label);

        // Добавляем рамку для визуального разделения
        panel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

        return panel;
    }

    /**
     * Динамически адаптирует layout в зависимости от текущего размера окна
     */
    private void adjustLayout() {
        // Получаем доступную ширину области просмотра
        int viewportWidth = scrollPane.getViewport().getWidth();

        // Если ширина еще не определена, используем оценочное значение
        if (viewportWidth <= 0) {
            viewportWidth = getWidth() - 30;
        }

        int spacing = 10; // Отступ между панелями

        // Вычисляем количество панелей в строке (учитывая отступы)
        int panelsPerRow = Math.max(1, viewportWidth / (PANEL_WIDTH + spacing));

        // Рассчитываем требуемую высоту контента
        int rows = (int) Math.ceil((double) PANEL_COUNT / panelsPerRow);
        int contentHeight = rows * (PANEL_HEIGHT + spacing) + spacing;

        // Обновляем предпочтительный размер mainPanel для правильной работы прокрутки
        mainPanel.setPreferredSize(new Dimension(viewportWidth, contentHeight));

        // Размещаем все панели в сетке
        for (int i = 0; i < smallPanels.size(); i++) {
            JPanel panel = smallPanels.get(i);
            int row = i / panelsPerRow;
            int col = i % panelsPerRow;

            int x = spacing + col * (PANEL_WIDTH + spacing);
            int y = spacing + row * (PANEL_HEIGHT + spacing);

            panel.setBounds(x, y, PANEL_WIDTH, PANEL_HEIGHT);
        }

        // Обновляем интерфейс
        mainPanel.revalidate();
        mainPanel.repaint();

        System.out.println("Resize: Width=" + viewportWidth +
                ", Panels per row=" + panelsPerRow);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ResponsivePanelsExample example = new ResponsivePanelsExample();
            example.setVisible(true);
        });
    }
}
