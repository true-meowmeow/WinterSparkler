package swing.ui.pages.home.collections.controls;

import swing.objects.core.JPanelCustom;

import javax.swing.*;
import java.awt.*;

public class ControlsPanel extends JPanelCustom {

    public ControlsPanel() {
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JTextField searchField = new JTextField("Введите текст...");

        searchField.setFocusable(false);
        add(searchField, BorderLayout.CENTER);
    }
}