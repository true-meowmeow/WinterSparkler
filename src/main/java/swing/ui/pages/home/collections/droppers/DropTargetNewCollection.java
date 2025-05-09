package swing.ui.pages.home.collections.droppers;

import swing.objects.dropper.DropPanelAbstract;
import swing.ui.pages.home.play.view.selection.SelectablePanel;

import java.util.List;

public class DropTargetNewCollection extends DropPanelAbstract {
    @Override
    public void dropAction() {
        System.out.println("«Добавить новую коллекцию»");
        // TODO: вызвать отображение вашей временной панели

    }

    @Override
    public void dropAction(List<SelectablePanel> selectedItems) {
        super.dropAction(selectedItems);

        System.out.println(selectedItems.size());
    }
}