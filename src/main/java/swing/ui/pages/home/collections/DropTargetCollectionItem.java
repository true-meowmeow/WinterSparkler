package swing.ui.pages.home.collections;

import swing.objects.dropper.DropPanelAbstract;
import swing.ui.pages.home.play.SelectablePanel;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class DropTargetCollectionItem extends DropPanelAbstract {


    @Override
    public void dropAction() {
        System.out.println("dropped from collection");
    }
}