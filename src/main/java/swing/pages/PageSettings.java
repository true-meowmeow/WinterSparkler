package swing.pages;

import javax.swing.*;
import java.awt.*;

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
    }
}