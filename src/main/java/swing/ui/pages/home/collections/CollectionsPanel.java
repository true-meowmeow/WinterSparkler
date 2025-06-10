package swing.ui.pages.home.collections;

import swing.objects.core.JPanelCustom;
import swing.util.SmoothScrollPane;
import swing.ui.pages.home.collections.objects.CollectionObjectPanel;

import java.awt.*;

public class CollectionsPanel extends JPanelCustom {

    public CollectionsPanel() {
        initCollectionsFieldPanel();

        SmoothScrollPane scroller = new SmoothScrollPane(collectionsFieldPanel);

        add(scroller);

        add(new BottomAddCollectionPanel(), BorderLayout.SOUTH);
    }

    JPanelCustom collectionsFieldPanel = new JPanelCustom();
    private void initCollectionsFieldPanel() {
        collectionsFieldPanel.add(CollectionObjectPanel.getInstance(), BorderLayout.NORTH);

        EmptyDropPanel emptyPanel = new EmptyDropPanel();
        collectionsFieldPanel.add(emptyPanel, BorderLayout.CENTER);
    }
}

