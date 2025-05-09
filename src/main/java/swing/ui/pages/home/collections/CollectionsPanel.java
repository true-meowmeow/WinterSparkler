package swing.ui.pages.home.collections;

import swing.objects.core.JPanelCustom;
import swing.objects.objects.SmoothScrollPane;
import swing.ui.pages.home.collections.objects.CollectionContentManager;

import java.awt.*;

public class CollectionsPanel extends JPanelCustom {

    public CollectionsPanel() {
        initCollectionsFieldPanel();

        SmoothScrollPane scroller = new SmoothScrollPane(collectionsFieldPanel);

        add(scroller);

        add(new BottomAddCollectionPanel(), BorderLayout.SOUTH);
    }

    JPanelCustom collectionsFieldPanel = new JPanelCustom();
    CollectionContentManager collectionContentManager = new CollectionContentManager();
    private void initCollectionsFieldPanel() {
        collectionsFieldPanel.add(collectionContentManager, BorderLayout.NORTH);

        EmptyDropPanel emptyPanel = new EmptyDropPanel();
        collectionsFieldPanel.add(emptyPanel, BorderLayout.CENTER);
    }
}

