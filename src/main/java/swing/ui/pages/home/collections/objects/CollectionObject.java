package swing.ui.pages.home.collections.objects;

import swing.objects.dropper.DropPanel;
import swing.ui.pages.home.SwingHomeVariables;
import swing.ui.pages.home.collections.controllers.CollectionObjectController;
import swing.ui.pages.home.collections.droppers.DropTargetCollection;

import javax.swing.*;
import java.awt.*;

public class CollectionObject extends DropPanel implements SwingHomeVariables {
    private int positionList; //position in ui list
    private String title;

    CollectionObjectController collectionObjectController;

    public CollectionObject(int positionList, String title) {
        super(title, new DropTargetCollection());
        this.positionList = positionList;
        this.title = title;
        collectionObjectController = new CollectionObjectController(this);
        dropTargetPanel.setCollectionItem(this);

        setMaximumSize(MAX_INT, HEIGHT_COLLECTION_PANEL);
        setPreferredSize(ZERO_INT, HEIGHT_COLLECTION_PANEL);


        setLayout(new BorderLayout());
        add(new JLabel(title, SwingConstants.CENTER), BorderLayout.CENTER);
        setNormal();
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

    public int getPositionList() {
        return positionList;
    }

    public String getTitle() {
        return title;
    }
}
