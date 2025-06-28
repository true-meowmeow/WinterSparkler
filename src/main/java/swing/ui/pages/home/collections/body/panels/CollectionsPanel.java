package swing.ui.pages.home.collections.body.panels;

import swing.objects.core.JPanelCustom;
import swing.objects.core.Axis;
import swing.objects.objects.SmoothScrollPane;

import java.awt.*;

public class CollectionsPanel extends JPanelCustom {

    public CollectionsPanel() {
        initCollectionsFieldPanel();

        SmoothScrollPane scroller = new SmoothScrollPane(collectionsFieldPanel);
        add(scroller);

        JPanelCustom bottomContainer = new JPanelCustom(Axis.Y_AX);
        bottomContainer.add(new BottomAddCollectionPanel());
        bottomContainer.add(new BottomAddCollectionGroupPanel());

        add(bottomContainer, BorderLayout.SOUTH);
    }

    JPanelCustom collectionsFieldPanel = new JPanelCustom();

    private void initCollectionsFieldPanel() {
        collectionsFieldPanel.add(CollectionLinkPanel.getInstance(), BorderLayout.NORTH);

        EmptyCollectionDropPanel emptyPanel = new EmptyCollectionDropPanel();
        collectionsFieldPanel.add(emptyPanel, BorderLayout.CENTER);
    }
}

