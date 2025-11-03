package swing.elements.pages.home.playground;

import core.main.config.LayoutProperties;
import swing.core.basics.JPanelCustom;
import swing.core.basics.Pages;
import swing.core.basics.PanelType;
import swing.elements.pages.home.playground.cover.CoverPanel;
import swing.elements.pages.home.playground.player.PlayerPanel;
import swing.elements.pages.home.playground.queue.QueuePanel;

import javax.swing.*;
import java.awt.*;

public class BottomFrame extends Pages {

    public BottomFrame() {
        super(PanelType.GRID);

        setPreferredSize(MAX_INT, LayoutProperties.get().getBottomFrameHeight());

        add(new PlayerPanel(), menuGridBagConstraintsX(0, LayoutProperties.get().getWeightPlayFrame()));
        add(new CoverPanel(), menuGridBagConstraintsX(1, LayoutProperties.get().getWeightQueueFrame()));
    }


}
