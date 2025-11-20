package data;

import java.util.ArrayList;
import java.util.List;

public class MediaData {

    List<GroupObject> groupObjectList;

    public MediaData() {
        this.groupObjectList = new ArrayList<>();
        exampleData();
    }

    private void exampleData() {

        for (int s = 0; s < 2; s++) {
            groupObjectList.add(new GroupObject("Group " + s));
            for (int i = 0; i < 3; i++) {
                groupObjectList.get(s).getCollectionObjectList().add(new CollectionObject("Collection " + i));
                for (int j = 0; j < 4; j++) {
                    groupObjectList.get(s).getCollectionObjectList().get(i).getSeriesObjectList().add(new SeriesObject("Series " + j));
                    for (int k = 0; k < 10; k++) {
                        groupObjectList.get(s).getCollectionObjectList().get(i).getSeriesObjectList().get(j).getMediaObjectList().add(new MediaObject("Media " + k));
                    }
                }
            }
        }
    }
}
