package data;

import java.util.ArrayList;
import java.util.List;

public class CollectionObject {

    private String collectionName;
    private List<SeriesObject> seriesObjectList;

    public CollectionObject(String collectionName) {
        this.collectionName = collectionName;
        this.seriesObjectList = new ArrayList<>();
    }

    public List<SeriesObject> getSeriesObjectList() {
        return seriesObjectList;
    }
}
