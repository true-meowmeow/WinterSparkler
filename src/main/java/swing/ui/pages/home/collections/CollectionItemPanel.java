package swing.ui.pages.home.collections;

import swing.objects.dropper.DropPanel;
import swing.ui.pages.home.SwingHomeVariables;

import javax.swing.*;
import java.awt.*;

public class CollectionItemPanel extends DropPanel implements SwingHomeVariables {
    private String title;

    public CollectionItemPanel(String title) {
        super(title, new DropTargetCollection());
        this.title = title;

        setMaximumSize(MAX_INT, HEIGHT_COLLECTION_PANEL);
        setPreferredSize(ZERO_INT, HEIGHT_COLLECTION_PANEL);

        dropTargetPanel.setCollectionItem(this);


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

    public String getTitle() {
        return title;
    }
}
