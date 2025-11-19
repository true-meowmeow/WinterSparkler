package core.cols;

import core.InnerGridPanel;
import core.ThreeColumnLayout;
import core.configOld.*;

import javax.swing.*;
import java.awt.*;

public class Col3 extends JPanel {


    public Col3(ThreeColumnLayout rootLayout, JPanel root) {
        super(new BorderLayout());
        setBackground(Colors.COL3_BG);

        // Тулбар
        JToolBar toolbar = new JToolBar();
        toolbar.setFloatable(false);

        JButton mergeBtn = new JButton(UiText.BTN_MERGE);
        toolbar.add(mergeBtn);

        // Внутренняя сетка
        InnerGridPanel grid = new InnerGridPanel(GridConfig.BOTTOM_ROW_HEIGHT);

        // Логика Merge/Unmerge
        mergeBtn.addActionListener(e -> {
            boolean newMode = !grid.isMergedTop();
            grid.setMergedTop(newMode);
            rootLayout.setForceColsAlwaysVisible(newMode);
            if (UiText.SHOW_SPLIT_TEXT) {
                mergeBtn.setText(newMode ? UiText.BTN_SPLIT : UiText.BTN_MERGE);
            }
            root.revalidate();
            root.repaint();
        });

        add(toolbar, BorderLayout.NORTH);
        add(grid, BorderLayout.CENTER);
    }




}
