package swing.ui.pages.home.collections;

import swing.objects.general.JPanelCustom;
import swing.objects.general.SmoothScrollPane;

import javax.swing.*;
import java.awt.*;

//todo При перемещении от manage объектов в коллекции должна появляться временная панель снизу с надписью добавить новую панель, перекрывающая остальные панели

class CollectionPanel extends JPanelCustom {

    public CollectionPanel() {

        CollectionsListPanel collectionsListPanel = new CollectionsListPanel();

        SmoothScrollPane scroller = new SmoothScrollPane(collectionsListPanel);
        add(scroller);

        add(new BottomAddCollectionPanel(), BorderLayout.SOUTH);
    }

    private static class CollectionsListPanel extends JPanelCustom {
        CollectionsListPanel() {
            super();
            setLayout(new BorderLayout());

            // 1. Панель со списком стандартных CollectionItemPanel
            JPanel listHolder = new JPanel();
            listHolder.setLayout(new BoxLayout(listHolder, BoxLayout.Y_AXIS));
            for (int i = 1; i <= 9; i++) {
                CollectionItemPanel panel = new CollectionItemPanel("Коллекция " + i);
                new CollectionItemController(panel);
                listHolder.add(panel);
            }
            add(listHolder, BorderLayout.NORTH);

            // 2. Невидимая «ловушка», растягивающаяся на всё свободное пространство
            EmptyDropPanel emptyPanel = new EmptyDropPanel();
            add(emptyPanel, BorderLayout.CENTER);
        }
    }

    private String ae;
}
