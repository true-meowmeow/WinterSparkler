package swing.pages.home.collections;

import swing.objects.JPanelBorder;

import javax.swing.*;
import java.awt.*;

public class CollectionsPanel extends JPanelBorder {

    public CollectionsPanel() {
        super(true);
        add(new SearchPanel(), BorderLayout.NORTH);
        add(new CollectionMainPanel(), BorderLayout.CENTER);
    }
}

class CollectionMainPanel extends JPanelBorder {

    public CollectionMainPanel() {
        setBackground(Color.LIGHT_GRAY);
        add(new JLabel(), BorderLayout.CENTER); // Пустой компонент
    }
}

class SearchPanel extends JPanelBorder {

    public SearchPanel() {
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JTextField searchField = new JTextField("Введите текст...");
        add(searchField, BorderLayout.CENTER);
        //setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));


    }
}

