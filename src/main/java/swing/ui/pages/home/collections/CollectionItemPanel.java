package swing.ui.pages.home.collections;

import swing.objects.dropper.DropPanel;
import swing.objects.dropper.DropPanelAbstract;
import swing.objects.general.JPanelCustom;

import javax.swing.*;
import java.awt.*;

public class CollectionItemPanel extends DropPanel {
    public static final int HEIGHT = 80;
    private String title;

    public CollectionItemPanel(String title) {
        super(title, new DropTargetCollection());
        this.title = title;

        setMaximumSize(MAX_INT, HEIGHT);
        setPreferredSize(ZERO_INT, HEIGHT);

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

    // геттеры для title и размеров
    public String getTitle() {
        return title;
    }
}
