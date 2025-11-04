package swing.elements.pages.home.repository.collections.body.objects;

import core.main.config.LayoutProperties;
import swing.core.dropper.DropPanel;
import swing.elements.pages.home.repository.collections.body.controllers.CollectionObjectController;
import swing.elements.pages.home.repository.collections.body.droppers.DropTargetCollection;

import javax.swing.*;
import java.awt.*;

public class CollectionObject extends DropPanel {
    private static final int COLLECTION_PANEL_HEIGHT = LayoutProperties.get().getHeightCollectionPanel();

    private int positionList;
    private int collectionOrder;
    private String title;
    private final JLabel label;
    CollectionObjectController collectionObjectController;
    //private static final String PROPERTY_PREFIX = ;
    //private static String PROPERTY_NAME;


    public CollectionObject(String PropertyName, int collectionOrder, int positionList, String title) { //Я буду передавать сюда объекты перемещаемые и информацию о них, статический класс, генерирующий эти названия на основе информации
        super(PropertyName, new DropTargetCollection());

        this.collectionOrder = collectionOrder;
        this.positionList = positionList;
        this.title = title;
        collectionObjectController = new CollectionObjectController(this);
        dropTargetPanel.setCollectionItem(this);

        setMaximumSize(MAX_INT, COLLECTION_PANEL_HEIGHT);
        setPreferredSize(ZERO_INT, COLLECTION_PANEL_HEIGHT);

        setLayout(new BorderLayout());
        label = new JLabel(title, SwingConstants.CENTER);
        add(label, BorderLayout.CENTER);
        setNormal();
    }


    public void setPositionList(int pos) {
        positionList = pos;
    }

    void setCollectionOrder(int order) {
        collectionOrder = order;
    }

    void setTitle(String t) {
        title = t;
        label.setText(t);
    }

    public int getPositionList() {
        return positionList;
    }

    public int getCollectionOrder() {
        return collectionOrder;
    }

    public String getTitle() {
        return title;
    }

    public void setNormal() {
        setBackground(LayoutProperties.get().getColorCollectionBasic());
    }

    public void setOpened() {
        setBackground(LayoutProperties.get().getColorCollectionOpened());
    }

    public void setDragHighlight() {
        setBackground(LayoutProperties.get().getColorCollectionDrag());
    }

    public void setFocus() {
        setBackground(LayoutProperties.get().getColorCollectionFocus());
    }
}
