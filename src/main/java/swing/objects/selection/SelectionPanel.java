package swing.objects.selection;

import core.contentManager.FilesDataMap;
import core.contentManager.MediaData;
import swing.objects.JPanelCustom;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import static swing.pages.home.play.FolderSystemPanel.FolderSystemPanelInstance;

public class SelectionPanel extends JPanelCustom {

    private JPanel folderContainer;
    private JPanel mediaContainer;

    public SelectionPanel() {
        // Keep original FLOW layout for compatibility with existing code
        super("Y");

        // Remove all components that might be added by the parent constructor
        removeAll();

        // Create containers with WrapLayout which behaves like FlowLayout but with proper wrapping
        folderContainer = new JPanel(new WrapLayout(FlowLayout.LEFT, 10, 10));
        folderContainer.setOpaque(false);
        //this.setBorder(BorderFactory.createLineBorder(Color.RED));
        //folderContainer.setBorder(BorderFactory.createLineBorder(Color.GREEN));

        mediaContainer = new JPanel(new WrapLayout(FlowLayout.LEFT, 10, 10));
        mediaContainer.setOpaque(false);
        createBorder(folderContainer);
        createBorder(mediaContainer);




        // Add containers to the main panel
        add(folderContainer);
        add(Box.createVerticalStrut(10)); // Отступ 10 пикселей между folderContainer и mediaContainer
        add(mediaContainer);

        add(Box.createVerticalGlue());       //Клей нужен для сохранения выделения снизу включительно

        // Add component listener to handle resize events
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                revalidate();
                repaint();
            }
        });
    }

    public void updateSet(FilesDataMap.CatalogData.FilesData filesDataHashSet) {
        // Clear old components and selection
        folderContainer.removeAll();
        mediaContainer.removeAll();
        FolderSystemPanelInstance().panels.clear();
        //removeAll();

        int index = 0;
        // Add folder panels to the folder container
        for (FilesDataMap.CatalogData.FilesData.SubFolder folder : filesDataHashSet.getFoldersDataHashSet()) {
            FolderPanel fp = new FolderPanel(index++, folder.getName().toString());
            FolderSystemPanelInstance().panels.add(fp);
            folderContainer.add(fp);
        }

        // Add media panels to the media container
        for (MediaData media : filesDataHashSet.getMediaDataHashSet()) {
            MediaPanel mp = new MediaPanel(index++, media.getName().toString());
            FolderSystemPanelInstance().panels.add(mp);
            mediaContainer.add(mp);
        }

        // Update the UI
        revalidate();
        repaint();

        // Mouse listeners for selection functionality
        MouseAdapter ma = new MouseAdapter() {
            Point dragStart = null;
            boolean dragging = false;
            boolean ctrlDownAtStart = false;
            boolean shiftDownAtStart = false;
            boolean altDownAtStart = false;

            @Override
            public void mousePressed(MouseEvent e) {
                if (e.getSource() == SelectionPanel.this) {
                    dragStart = e.getPoint();
                    dragging = true;
                    ctrlDownAtStart = (e.getModifiersEx() & MouseEvent.CTRL_DOWN_MASK) != 0;
                    shiftDownAtStart = (e.getModifiersEx() & MouseEvent.SHIFT_DOWN_MASK) != 0;
                    altDownAtStart = (e.getModifiersEx() & MouseEvent.ALT_DOWN_MASK) != 0;
                    if (!ctrlDownAtStart && !shiftDownAtStart && !altDownAtStart) {
                        FolderSystemPanelInstance().clearSelection();
                    }
                    FolderSystemPanelInstance().selectionRect = new Rectangle(dragStart);
                }
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                if (dragging) {
                    Point current = e.getPoint();
                    FolderSystemPanelInstance().selectionRect.setBounds(
                            Math.min(dragStart.x, current.x),
                            Math.min(dragStart.y, current.y),
                            Math.abs(dragStart.x - current.x),
                            Math.abs(dragStart.y - current.y)
                    );
                    repaint();
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (dragging) {
                    dragging = false;
                    // Recalculate coordinates of each panel in SelectionPanel coordinate system
                    for (SelectablePanel sp : FolderSystemPanelInstance().panels) {
                        // Calculate panel bounds relative to SelectionPanel
                        Rectangle compBounds = SwingUtilities.convertRectangle(
                                sp.getParent(), sp.getBounds(), SelectionPanel.this);

                        if (FolderSystemPanelInstance().selectionRect.intersects(compBounds)) {
                            if (shiftDownAtStart) {
                                if (altDownAtStart) {
                                    sp.setSelected(false);
                                } else {
                                    sp.setSelected(true);
                                }
                            } else {
                                if (altDownAtStart) {
                                    sp.setSelected(false);
                                } else if (ctrlDownAtStart) {
                                    sp.setSelected(!sp.isSelected());
                                } else {
                                    sp.setSelected(true);
                                }
                            }
                        }
                    }

                    // Find the minimum index among selected panels
                    int minIndex = Integer.MAX_VALUE;
                    for (SelectablePanel sp : FolderSystemPanelInstance().panels) {
                        if (sp.isSelected() && sp.getIndex() < minIndex) {
                            minIndex = sp.getIndex();
                        }
                    }
                    if (minIndex != Integer.MAX_VALUE) {
                        FolderSystemPanelInstance().anchorIndex = minIndex;
                    }

                    FolderSystemPanelInstance().selectionRect = null;
                    repaint();
                }
            }
        };

        // Add mouse listeners
        addMouseListener(ma);
        addMouseMotionListener(ma);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw the selection rectangle if it exists
        if (FolderSystemPanelInstance().selectionRect != null) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setColor(new Color(0, 0, 255, 50));
            g2.fill(FolderSystemPanelInstance().selectionRect);
            g2.setColor(Color.BLUE);
            g2.draw(FolderSystemPanelInstance().selectionRect);
        }
    }

    @Override
    public Dimension getPreferredSize() {
        // Calculating preferred size to accommodate content
        int width = 10 + 8 * (80 + 10);
        int height = folderContainer.getPreferredSize().height + mediaContainer.getPreferredSize().height + 20;
        return new Dimension(width, height);
    }

    /**
     * WrapLayout: A modified FlowLayout that wraps components properly
     * Based on the implementation by Rob Camick
     * https://tips4java.wordpress.com/2008/11/06/wrap-layout/
     */
    public static class WrapLayout extends FlowLayout {
        private Dimension preferredLayoutSize;

        public WrapLayout() {
            super();
        }

        public WrapLayout(int align) {
            super(align);
        }

        public WrapLayout(int align, int hgap, int vgap) {
            super(align, hgap, vgap);
        }

        @Override
        public Dimension preferredLayoutSize(Container target) {
            return layoutSize(target, true);
        }

        @Override
        public Dimension minimumLayoutSize(Container target) {
            Dimension minimum = layoutSize(target, false);
            minimum.width -= (getHgap() + 1);
            return minimum;
        }

        private Dimension layoutSize(Container target, boolean preferred) {
            synchronized (target.getTreeLock()) {
                int targetWidth = target.getWidth();

                // If the container width is 0, use a reasonable default width
                if (targetWidth == 0) {
                    targetWidth = Integer.MAX_VALUE;
                }

                int hgap = getHgap();
                int vgap = getVgap();
                Insets insets = target.getInsets();
                int horizontalInsetsAndGap = insets.left + insets.right + (hgap * 2);
                int maxWidth = targetWidth - horizontalInsetsAndGap;

                // Fit components into the allowed width
                Dimension dim = new Dimension(0, 0);
                int rowWidth = 0;
                int rowHeight = 0;

                int componentCount = target.getComponentCount();
                for (int i = 0; i < componentCount; i++) {
                    Component m = target.getComponent(i);
                    if (m.isVisible()) {
                        Dimension d = preferred ? m.getPreferredSize() : m.getMinimumSize();

                        // If this component will not fit in the current row, start a new row
                        if (rowWidth + d.width > maxWidth && rowWidth > 0) {
                            dim.width = Math.max(dim.width, rowWidth);
                            dim.height += rowHeight + vgap;
                            rowWidth = 0;
                            rowHeight = 0;
                        }

                        // Add the component to current row
                        if (rowWidth != 0) {
                            rowWidth += hgap;
                        }
                        rowWidth += d.width;
                        rowHeight = Math.max(rowHeight, d.height);
                    }
                }

                // Add the last row dimensions
                dim.width = Math.max(dim.width, rowWidth);
                dim.height += rowHeight;

                // Add the container's insets
                dim.width += horizontalInsetsAndGap;
                dim.height += insets.top + insets.bottom + vgap * 2;

                // Account for the container's minimum width
                Container scrollPane = SwingUtilities.getAncestorOfClass(JScrollPane.class, target);
                if (scrollPane != null && targetWidth != Integer.MAX_VALUE) {
                    dim.width = targetWidth;
                }

                return dim;
            }
        }
    }
}