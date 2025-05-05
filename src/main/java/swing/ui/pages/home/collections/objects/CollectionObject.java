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
        setBackground(new Color(0xF5F5F5));
    }

    public void setOpened() {
        setBackground(new Color(0xDCDCDC));
    }

    public void setDragHighlight() {
        setBackground(new Color(0xDCDCDC));
    }

    public void setFocus() {
        setBackground(new Color(0xE0E0E0));
    }

    public int getPositionList() {
        return positionList;
    }

    public String getTitle() {
        return title;
    }
}
