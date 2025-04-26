package swing.objects.dropper;

import swing.ui.pages.home.collections.CollectionItemPanel;
import swing.ui.pages.home.play.SelectablePanel;

import java.util.List;

public abstract class DropPanelAbstract {

    public void dropAction(List<SelectablePanel> selectedItems) {
    }

    public void dropAction() {
    }
    public void dropAction(String title) {
    }

    public void setCollectionItem(CollectionItemPanel dropPanel) {
    }
}
