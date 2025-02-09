package swing.pages.home.collections;

import swing.objects.JPanelCustom;

import javax.swing.*;
import java.awt.*;

public class CollectionsFrame extends JPanelCustom {

    public CollectionsFrame() {
        super(PanelType.BORDER, true);
        add(new SearchPanel(), BorderLayout.NORTH);
        add(new CollectionPanel(), BorderLayout.CENTER);
    }
}

class SearchPanel extends JPanelCustom {

    public SearchPanel() {
        super(PanelType.BORDER);
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JTextField searchField = new JTextField("Введите текст...");

        searchField.setFocusable(false);
        add(searchField, BorderLayout.CENTER);
        //setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));


    }
}
class CollectionPanel extends JPanelCustom {

    public CollectionPanel() {
        super(PanelType.BORDER);
        setBackground(Color.LIGHT_GRAY);
        add(new JLabel(), BorderLayout.CENTER); // Пустой компонент
    }

}

