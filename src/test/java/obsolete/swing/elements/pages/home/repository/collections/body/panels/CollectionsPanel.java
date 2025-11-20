package obsolete.swing.elements.pages.home.repository.collections.body.panels;

import obsolete.swing.core.basics.JPanelCustom;
import obsolete.swing.core.basics.Axis;
import obsolete.swing.core.objects.SmoothScrollPane;

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

