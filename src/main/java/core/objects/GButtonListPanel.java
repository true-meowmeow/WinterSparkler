package core.objects;

import core.main.check.PanelType;

import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.Insets;

public class GButtonListPanel extends GPanel {
    private final GScrollablePanel content;
    private final int itemHeight;
    private final int itemGap;
    private int itemCount = 0;

    public GButtonListPanel(int itemHeight, int itemGap) {
        super();
        this.itemHeight = Math.max(1, itemHeight);
        this.itemGap = Math.max(0, itemGap);

        content = new GScrollablePanel(PanelType.GRID, null, 0, 0, false);
        content.setScrollableUnitIncrement(Math.max(1, this.itemHeight + this.itemGap));

        JScrollPane scrollPane = new JScrollPane(content);
        scrollPane.setBorder(null);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        add(scrollPane, BorderLayout.CENTER);
    }

    public JButton addItemButton(String label) {
        JButton button = new JButton(label);
        Dimension size = new Dimension(0, itemHeight);
        button.setPreferredSize(size);
        button.setMinimumSize(size);
        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, itemHeight));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = itemCount;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        if (itemCount > 0 && itemGap > 0) {
            gbc.insets = new Insets(itemGap, 0, 0, 0);
        }
        content.add(button, gbc);
        content.revalidate();
        content.repaint();
        itemCount++;
        return button;
    }

    public void clearItems() {
        content.removeAll();
        content.revalidate();
        content.repaint();
        itemCount = 0;
    }
}
