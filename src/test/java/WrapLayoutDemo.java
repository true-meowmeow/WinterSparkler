import javax.swing.*;
import java.awt.*;

public class WrapLayoutDemo {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("WrapLayout Demo with Multiple Separators");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(500, 300);

            JPanel mainPanel = new JPanel();
            mainPanel.setLayout(new BorderLayout());


            // Создаем панель с WrapLayout
            WrapLayout wrapLayout = new WrapLayout(FlowLayout.LEFT);
            JPanel panel = new JPanel(wrapLayout);

            panel.setBorder(BorderFactory.createLineBorder(Color.RED));

            // Добавляем компоненты до первого разделителя
            for (int i = 1; i <= 12; i++) {
                panel.add(createLabel("A" + i));
            }

//wrapLayout.setBlockHgap(50);a
            // Добавляем первый разделитель
            panel.add(createSeparator());

            // Добавляем компоненты между разделителями
            for (int i = 1; i <= 6; i++) {
                panel.add(createLabel("B" + i));
            }

            // Добавляем второй разделитель
            panel.add(createSeparator());

            // Добавляем компоненты после второго разделителя
            for (int i = 1; i <= 8; i++) {
                panel.add(createLabel("C" + i));
            }

            // Оборачиваем в JScrollPane для возможности скроллинга
            JScrollPane scrollPane = new JScrollPane(panel);
            mainPanel.add(scrollPane);


            frame.add(mainPanel);





            frame.setVisible(true);
        });
    }

    // Метод для создания ярлыков
    private static JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setOpaque(true);
        label.setBackground(new Color(173, 216, 230));
        label.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
        label.setPreferredSize(new Dimension(80, 30));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        return label;
    }

    // Метод для создания невидимого разделителя
    private static Component createSeparator() {
        return new Separator();
    }
}
