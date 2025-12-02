package data;

import java.util.ArrayList;
import java.util.List;

public class SeriesObject {

    private String seriesName;

    private List<MediaObject> mediaObjectList;

    public SeriesObject(String seriesName) {
        this.seriesName = seriesName;
        this.mediaObjectList = new ArrayList<>();
    }

    public List<MediaObject> getMediaObjectList() {
        return mediaObjectList;
    }
}
