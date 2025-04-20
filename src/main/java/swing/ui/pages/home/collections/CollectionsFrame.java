package swing.ui.pages.home.collections;

import swing.objects.general.JPanelCustom;
import swing.objects.general.SmoothScrollPane;

import javax.swing.*;
import java.awt.*;

public class CollectionsFrame extends JPanelCustom {

    public CollectionsFrame() {
        super(true);
        add(new SearchPanel(), BorderLayout.NORTH);
        add(new CollectionPanel(), BorderLayout.CENTER);
    }
}

class SearchPanel extends JPanelCustom {

    public SearchPanel() {
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JTextField searchField = new JTextField("Введите текст...");

        searchField.setFocusable(false);
        add(searchField, BorderLayout.CENTER);
    }
}