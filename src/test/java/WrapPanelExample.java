import javax.swing.*;
import java.awt.*;

public class WrapPanelExample extends JFrame {

    public WrapPanelExample() {
        setTitle("Wrap Panel Example");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Создаем основной JPanel с FlowLayout, который автоматически переносит компоненты
        JPanel mainPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));

        // Добавляем 50 маленьких панелей размером 80x80
        for (int i = 0; i < 50; i++) {
            JPanel subPanel = new JPanel();
            subPanel.setPreferredSize(new Dimension(80, 80));
            // Устанавливаем случайный цвет для визуального различия
            subPanel.setBackground(new Color((int)(Math.random() * 0x1000000)));
            subPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            mainPanel.add(subPanel);
        }

        // Оборачиваем основной контейнер в JScrollPane на случай, если окно станет слишком маленьким
        JScrollPane scrollPane = new JScrollPane(mainPanel);
        add(scrollPane, BorderLayout.CENTER);

        setSize(600, 400);
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new WrapPanelExample().setVisible(true));
    }
}
