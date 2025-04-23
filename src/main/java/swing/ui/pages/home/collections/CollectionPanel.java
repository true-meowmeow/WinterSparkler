package swing.ui.pages.home.collections;

import swing.objects.dropper.DropPanel;
import swing.objects.dropper.DropPanelAbstract;
import swing.objects.general.JPanelCustom;
import swing.objects.general.SmoothScrollPane;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

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

    private static class EmptyDropPanel extends DropPanel {
        public EmptyDropPanel() {
            super("_EMPTY_SLOT_", new DropTargetNewCollection());
            setDimensions(ZERO, ZERO, MAX);

            setOpaque(false);
        }
    }

    private static class DropTargetNewCollection extends DropPanelAbstract {
        @Override
        public void dropAction() {
            System.out.println("«Добавить новую коллекцию»");
            // TODO: вызвать отображение вашей временной панели
        }
    }

    private static class BottomAddCollectionPanel extends DropPanel {

        public BottomAddCollectionPanel() {
            super("_EMPTY_SLOT_2", new DropTargetNewCollection());
            int height = 100;
            setPreferredSize(MAX_INT, height);
            add(new JLabel("+ Add collection"));
            //setVisible(false);
        }
    }
}
