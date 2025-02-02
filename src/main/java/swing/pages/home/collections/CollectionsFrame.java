package swing.pages.home.collections;

import swing.objects.JPanelBorder;

import javax.swing.*;
import java.awt.*;

public class CollectionsFrame extends JPanelBorder {

    public CollectionsFrame() {
        super(true);
        add(new SearchPanel(), BorderLayout.NORTH);
        add(new CollectionPanel(), BorderLayout.CENTER);
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
class CollectionPanel extends JPanelBorder {

    public CollectionPanel() {
        setBackground(Color.LIGHT_GRAY);
        add(new JLabel(), BorderLayout.CENTER); // Пустой компонент
    }

}

