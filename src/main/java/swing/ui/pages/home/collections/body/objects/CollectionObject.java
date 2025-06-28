package swing.ui.pages.home.collections.body.objects;

import swing.objects.dropper.DropPanel;
import swing.ui.pages.home.SwingHomeVariables;
import swing.ui.pages.home.collections.body.controllers.CollectionObjectController;
import swing.ui.pages.home.collections.body.droppers.DropTargetCollection;

import javax.swing.*;
import java.awt.*;

public class CollectionObject extends DropPanel implements SwingHomeVariables {
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

        setMaximumSize(MAX_INT, HEIGHT_COLLECTION_PANEL);
        setPreferredSize(ZERO_INT, HEIGHT_COLLECTION_PANEL);

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
        setBackground(basicColor);
    }

    public void setOpened() {
        setBackground(aOpenedColor);
    }

    public void setDragHighlight() {
        setBackground(aDragColor);
    }

    public void setFocus() {
        setBackground(aFocusColor);
    }
}
