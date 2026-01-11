package core.swing.data.transferTrain;

import javax.swing.DefaultListModel;

public class GroupItem extends Item {
    private final DefaultListModel<Item> items = new DefaultListModel<>();
    private boolean expanded = true;

    public GroupItem(String name) {
        super(name);
    }

    public DefaultListModel<Item> items() {
        return items;
    }

    public boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }

    public void toggleExpanded() {
        expanded = !expanded;
    }
}
