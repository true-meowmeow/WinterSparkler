package data;

import java.util.ArrayList;
import java.util.List;

public class GroupObject {

    private String groupName;
    private List<CollectionObject> collectionObjectList;

    public GroupObject(String groupName) {
        this.groupName = groupName;
        this.collectionObjectList = new ArrayList<>();
    }

    public List<CollectionObject> getCollectionObjectList() {
        return collectionObjectList;
    }
}
