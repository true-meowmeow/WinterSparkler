package data;

import java.util.ArrayList;
import java.util.List;

public class MediaData {

    List<CollectionObject> collectionObjectList;

    public MediaData() {
        this.collectionObjectList = new ArrayList<>();
        exampleData();
    }

    private void exampleData() {
        collectionObjectList.add(new CollectionObject("Collection 1"));
        collectionObjectList.add(new CollectionObject("Collection 2"));
        collectionObjectList.add(new CollectionObject("Collection 3"));

        for (int i = 0; i < 3; i++) {
            collectionObjectList.add(new CollectionObject("Collection " + i));
            for (int j = 0; j < 4; j++) {
                collectionObjectList.get(i).getSeriesObjectList().add(new SeriesObject("Series " + j));
                for (int k = 0; k < 10; k++) {
                    collectionObjectList.get(i).getSeriesObjectList().get(j).getMediaObjectList().add(new MediaObject("Media " + k));
                }
            }
        }

    }
}
