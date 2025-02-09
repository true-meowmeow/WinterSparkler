package swing.pages.home.play;

import core.contentManager.FolderEntities;
import swing.objects.JPanelCustom;
import swing.objects.MethodsSwing;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PlayFrame extends JPanelCustom {

    public PlayFrame(PlaylistPanel playlistPanel) {
        super(PanelType.BORDER, true);

        add(new InfoPanel(), BorderLayout.NORTH);
        add(playlistPanel, BorderLayout.CENTER);
        add(new PlayerPanel(), BorderLayout.SOUTH);
    }
}

class InfoPanel extends JPanelCustom {
    public InfoPanel() {
        super(PanelType.BORDER);
        //setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JTextArea textArea = new JTextArea("Многострочный\nтекст");



        {
            JPanel infoControlsPanel = new JPanelCustom(PanelType.BORDER);


            {
                JPanel infoPanel = new JPanelCustom(PanelType.GRID);
                infoPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
                GridBagConstraints gbc = new GridBagConstraints();
                gbc.fill = GridBagConstraints.BOTH;
                gbc.weightx = 0.5;

                JButton btn1 = new JButton("1 1");
                btn1.setFocusable(false);
                btn1.setFocusPainted(false);
                gbc.gridx = 0;
                infoPanel.add(btn1, gbc);

                JButton btn2 = new JButton("2 2");
                btn2.setFocusable(false);
                btn2.setFocusPainted(false);
                gbc.gridx = 1;
                infoPanel.add(btn2, gbc);

                infoControlsPanel.add(infoPanel, BorderLayout.WEST);
            }

            {
                JPanel controlsPanel = new JPanelCustom(PanelType.GRID);
                controlsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
                GridBagConstraints gbc = new GridBagConstraints();
                gbc.fill = GridBagConstraints.BOTH;
                gbc.weightx = 0.5;

                JButton btn1 = new JButton("21 1");
                btn1.setFocusable(false);
                btn1.setFocusPainted(false);
                gbc.gridx = 0;
                controlsPanel.add(btn1, gbc);

                JButton btn2 = new JButton("22 2");
                btn2.setFocusable(false);
                btn2.setFocusPainted(false);
                gbc.gridx = 1;
                controlsPanel.add(btn2, gbc);

                btn2.addActionListener(e -> {
                    try {
                        // Получаем позицию (offset) последнего символа
                        int docLength = textArea.getDocument().getLength();
                        if (docLength == 0) {
                            System.out.println("Нет текста");
                            return;
                        }
                        // Получаем область, в которой отображается последний символ.
                        // (Если используете JDK 9 и выше, можно использовать modelToView2D)
                        Rectangle lastCharRect = textArea.modelToView(docLength - 1);

                        // Определяем высоту строки по метрикам шрифта
                        int lineHeight = textArea.getFontMetrics(textArea.getFont()).getHeight();

                        // Количество визуальных строк вычисляется по вертикальной координате
                        // Последняя строка начинается на lastCharRect.y, поэтому делим на высоту строки и прибавляем 1
                        int visualLineCount = (lastCharRect.y / lineHeight) + 1;

                        System.out.println("Визуальных строк: " + visualLineCount);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                });

                infoControlsPanel.add(controlsPanel, BorderLayout.EAST);
            }
            add(infoControlsPanel, BorderLayout.EAST);
        }

        {
            JPanel infoTextPanel = new JPanel(new BorderLayout());
            infoTextPanel.setBackground(Color.ORANGE);


            textArea.setLineWrap(true);        // Включаем автоматический перенос строки
            textArea.setWrapStyleWord(true);    //todo сделать при информации о песне
            textArea.setEnabled(false);
            textArea.setBorder(null);
            textArea.setOpaque(false);
            infoTextPanel.add(textArea);

            add(infoTextPanel, BorderLayout.CENTER);
        }

    }

}



class PlayerPanel extends JPanelCustom {
    public PlayerPanel() {
        super(JPanelCustom.PanelType.BORDER);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(Box.createVerticalStrut(300));
    }
}


/*    private void huinya(JPanel panel) { //movablya resizing panol

        Component verticalStrut = Box.createVerticalStrut(100);
        panel.add(verticalStrut);

        JButton increaseButton = new JButton("Увеличить");
        JButton decreaseButton = new JButton("Уменьшить");

        increaseButton.addActionListener(e -> changeStrutSize(verticalStrut, 150));
        decreaseButton.addActionListener(e -> changeStrutSize(verticalStrut, 50));
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(increaseButton);
        buttonPanel.add(decreaseButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);
    }
    private void changeStrutSize(Component strut, int newHeight) {
        strut.setPreferredSize(new Dimension(0, newHeight));
        strut.revalidate(); // Пересчитать размеры
        strut.getParent().repaint(); // Перерисовать
    }*/