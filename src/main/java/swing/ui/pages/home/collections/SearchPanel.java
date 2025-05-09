package swing.ui.pages.home.collections;

import swing.objects.core.JPanelCustom;

import javax.swing.*;
import java.awt.*;

public class SearchPanel extends JPanelCustom {

    public SearchPanel() {
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JTextField searchField = new JTextField("Введите текст...");

        searchField.setFocusable(false);
        add(searchField, BorderLayout.CENTER);
    }
}