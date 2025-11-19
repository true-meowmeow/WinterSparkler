package core.cols.col3;

import core.basics.JPanelCustom;
import core.cols.ThreeColumnLayout;
import core.config.GridProperties;
import core.config.ThemeProperties;
import core.config.UiTextProperties;

import javax.swing.*;
import java.awt.*;

public class Col3 extends JPanelCustom {

    private static final ThemeProperties THEME = ThemeProperties.get();
    private static final UiTextProperties UI_TEXT = UiTextProperties.get();
    private static final GridProperties GRID = GridProperties.get();

    public Col3(ThreeColumnLayout rootLayout, JPanel root) {
        setBackground(THEME.columnThreeBackgroundColor());

        JToolBar toolbar = new JToolBar();
        toolbar.setFloatable(false);

        JButton mergeBtn = new JButton(UI_TEXT.mergeButtonText());
        //toolbar.add(mergeBtn);

        InnerGridPanel grid = new InnerGridPanel(GRID.bottomRowHeight());

        mergeBtn.addActionListener(e -> {
            boolean newMode = !grid.isMergedTop();
            grid.setMergedTop(newMode);
            rootLayout.setForceColsAlwaysVisible(newMode);
            if (UI_TEXT.showSplitText()) {
                mergeBtn.setText(newMode ? UI_TEXT.splitButtonText() : UI_TEXT.mergeButtonText());
            }
            root.revalidate();
            root.repaint();
        });

        add(toolbar, BorderLayout.NORTH);
        add(grid, BorderLayout.CENTER);
    }
}
