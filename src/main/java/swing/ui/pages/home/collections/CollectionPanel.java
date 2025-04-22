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
    }

    class CollectionsListPanel extends JPanelCustom {
        CollectionsListPanel() {
            super();
            setLayout(new BorderLayout());

            // 1. Панель со списком стандартных CollectionItemPanel
            JPanel listHolder = new JPanel();
            listHolder.setLayout(new BoxLayout(listHolder, BoxLayout.Y_AXIS));
            for (int i = 1; i <= 11; i++) {
                CollectionItemPanel panel = new CollectionItemPanel("Коллекция " + i);
                listHolder.add(panel);
            }
            add(listHolder, BorderLayout.NORTH);

            // 2. Невидимая «ловушка», растягивающаяся на всё свободное пространство
            EmptyDropPanel emptyPanel = new EmptyDropPanel();
            add(emptyPanel, BorderLayout.CENTER);
        }
    }

    // Прозрачная панель‑ловушка: расширяется, но может сжаться до 0
    private static class EmptyDropPanel extends DropPanel {
        public EmptyDropPanel() {
            super("_EMPTY_SLOT_", new DropTargetEmpty());
            setOpaque(false);
            createBorder();
        }

        @Override
        public Dimension getMinimumSize() {
            return new Dimension(0, 0);
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(0, 0);
        }

        @Override
        public Dimension getMaximumSize() {
            return new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE);
        }
    }

    private static class DropTargetEmpty extends DropPanelAbstract {
        @Override
        public void dropAction() {
            System.out.println("Drop в пустое место — показать «Добавить новую коллекцию»");
            // TODO: вызвать отображение вашей временной панели
        }
    }
}
