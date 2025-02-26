package swing.objects;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class MethodsSwing {

    public static void createBorder(JPanel jPanel) {
        Random random = new Random();
        Color randomColor;

        do {
            int r = random.nextInt(156) + 100; // 100–255
            int g = random.nextInt(156) + 100;
            int b = random.nextInt(156) + 100;

            randomColor = new Color(r, g, b);
        } while (isGrayish(randomColor)); // Проверяем, не получился ли серый

        jPanel.setBorder(BorderFactory.createLineBorder(randomColor, 1));
    }

    private static boolean isGrayish(Color color) {
        int r = color.getRed();
        int g = color.getGreen();
        int b = color.getBlue();

        // Разница между каналами должна быть значительной (иначе цвет серый)
        return (Math.abs(r - g) < 50) && (Math.abs(r - b) < 50) && (Math.abs(g - b) < 50);
    }



    public static JTextArea createTextArea(String name) {
        JTextArea textArea = new JTextArea();
        textArea.setFont(new Font("Arial", Font.PLAIN, 30)); // Пример с параметрами


        textArea.setText(name);
        textArea.setLineWrap(false);    // Перенос строк
        textArea.setWrapStyleWord(false); // Перенос по словам
        textArea.setBorder(null);
        textArea.setOpaque(false);
        textArea.setMinimumSize(new Dimension(0, 0));               //todo при убирании всего текста он воспроизводит ненужный звук
        textArea.setColumns(1); // Фиксированное количество символов        //todo отработать если текстовое поле пустое, наверное стоит вставлять что-то пустое типо null

        textArea.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    e.consume(); // Блокируем нажатие Enter
                    textArea.getParent().requestFocusInWindow(); // Убираем фокус
                }
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) { //todo Обработать отмену и комбинацию ctrl + z одинаково, если не было нажат enter, то не сохраняет.
                    e.consume(); // Блокируем нажатие Enter
                    textArea.getParent().requestFocusInWindow(); // Убираем фокус
                }
            }
        });
        return textArea;
    }
}
