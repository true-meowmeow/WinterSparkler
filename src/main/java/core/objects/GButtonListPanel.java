package core.objects;

import core.main.check.PanelType;

import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;
import java.awt.Color;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseListener;

public class GButtonListPanel extends GPanel {
    private static final int DROP_LINE_HEIGHT = 4;
    private static final Color DROP_LINE_COLOR = new Color(64, 128, 255);

    private final GScrollablePanel content;
    private final int itemHeight;
    private final int itemGap;
    private int itemCount = 0;
    private int dropIndicatorY = -1;

    public GButtonListPanel(int itemHeight, int itemGap) {
        this(itemHeight, itemGap, true);
    }

    public GButtonListPanel(int itemHeight, int itemGap, boolean scrollable) {
        super();
        this.itemHeight = Math.max(1, itemHeight);
        this.itemGap = Math.max(0, itemGap);

        content = new GScrollablePanel(PanelType.GRID, null, 0, 0, false) {
            @Override
            protected void paintChildren(java.awt.Graphics g) {
                super.paintChildren(g);
                if (dropIndicatorY >= 0) {
                    g.setColor(DROP_LINE_COLOR);
                    g.fillRect(0, dropIndicatorY, getWidth(), DROP_LINE_HEIGHT);
                }
            }
        };
        content.setScrollableUnitIncrement(Math.max(1, this.itemHeight + this.itemGap));

        if (scrollable) {
            JScrollPane scrollPane = new JScrollPane(content);
            scrollPane.setBorder(null);
            scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
            add(scrollPane, BorderLayout.CENTER);
        } else {
            add(content, BorderLayout.CENTER);
        }
    }

    public JButton addItemButton(String label) {
        JButton button = new JButton(label);
        Dimension size = new Dimension(0, itemHeight);
        button.setPreferredSize(size);
        button.setMinimumSize(size);
        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, itemHeight));

        addItemComponent(button);
        return button;
    }

    public <T extends Component> T addItemComponent(T component) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = itemCount;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        if (itemCount > 0 && itemGap > 0) {
            gbc.insets = new Insets(itemGap, 0, 0, 0);
        }
        content.add(component, gbc);
        content.revalidate();
        content.repaint();
        itemCount++;
        return component;
    }

    public void clearItems() {
        content.removeAll();
        content.revalidate();
        content.repaint();
        itemCount = 0;
        clearDropIndicator();
    }

    public void addContentMouseListener(MouseListener listener) {
        content.addMouseListener(listener);
    }

    public int indexAtPoint(Point point, Component relativeTo) {
        Point p = SwingUtilities.convertPoint(relativeTo, point, content);
        Component[] components = content.getComponents();
        for (int i = 0; i < components.length; i++) {
            Rectangle bounds = components[i].getBounds();
            int midY = bounds.y + bounds.height / 2;
            if (p.y < midY) {
                return i;
            }
        }
        return components.length;
    }

    public void showDropIndicator(int index) {
        int y = computeDropIndicatorY(index);
        int maxY = Math.max(0, content.getHeight() - DROP_LINE_HEIGHT);
        y = Math.max(0, Math.min(y, maxY));
        if (y != dropIndicatorY) {
            dropIndicatorY = y;
            content.repaint();
        }
    }

    public void clearDropIndicator() {
        if (dropIndicatorY != -1) {
            dropIndicatorY = -1;
            content.repaint();
        }
    }

    private int computeDropIndicatorY(int index) {
        Component[] components = content.getComponents();
        if (components.length == 0) {
            return 0;
        }
        if (index <= 0) {
            return 0;
        }
        if (index >= components.length) {
            Rectangle last = components[components.length - 1].getBounds();
            return last.y + last.height + itemGap / 2;
        }
        Rectangle target = components[index].getBounds();
        return Math.max(0, target.y - itemGap / 2);
    }
}
