package swing.objects.selection;

import core.contentManager.FilesDataMap;
import core.contentManager.MediaData;
import swing.objects.JPanelCustom;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashSet;

import static swing.pages.home.play.FolderSystemPanel.FolderSystemPanelInstance;


public class SelectionPanel extends JPanelCustom {

    public SelectionPanel() {
        super(PanelType.FLOW, "Left", 10, 10);

        setAlignmentY(Component.LEFT_ALIGNMENT);
    }

    public void updateSet(FilesDataMap.CatalogData.FilesData filesDataHashSet) {

        int index = 0;      //wtf is this for?
        for (FilesDataMap.CatalogData.FilesData.SubFolder folder : filesDataHashSet.getFoldersDataHashSet()) {
            FolderPanel sp = new FolderPanel(index++, folder.getName().toString());
            FolderSystemPanelInstance().panels.add(sp);
            add(sp);
        }

        for (MediaData media : filesDataHashSet.getMediaDataHashSet()) {
            MediaPanel sp = new MediaPanel(index++, media.getName().toString());
            FolderSystemPanelInstance().panels.add(sp);
            add(sp);
        }



/*        for (FilesDataMap.CatalogData.FilesData fileData : filesDataHashSet) {      //fixme здесь должен пройтись по медиа и папкам отдельно
            SelectablePanel sp = new SelectablePanel(i, fileData, x, y);
            FolderSystemPanelInstance().panels.add(sp);
            add(sp);
            i++;
        }*/





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
                    for (SelectablePanel sp : FolderSystemPanelInstance().panels) {
                        if (FolderSystemPanelInstance().selectionRect.intersects(sp.getBounds())) {
                            if (shiftDownAtStart) {
                                if (altDownAtStart) {
                                    sp.setSelected(false);
                                }/* else if (ctrlDownAtStart) {
                                        sp.setSelected(!sp.isSelected());
                                    }*/ else {
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
        addMouseListener(ma);
        addMouseMotionListener(ma);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
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
        return new Dimension(10 + 8 * (80 + 10), 10 + 5 * (80 + 10));
    }
}