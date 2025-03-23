package swing.objects.selection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static swing.pages.home.play.FolderSystemPanel.FolderSystemPanelInstance;


public class SelectionPanel extends JPanel {
    public SelectionPanel() {
        setLayout(null);
        int margin = 10;
        int panelSize = 80;
        int panelsPerRow = 8;
        for (int i = 0; i < FolderSystemPanelInstance().persons.size(); i++) {
            int x = margin + (i % panelsPerRow) * (panelSize + margin);
            int y = margin + (i / panelsPerRow) * (panelSize + margin);
            SelectablePanel sp = new SelectablePanel(i, FolderSystemPanelInstance().persons.get(i), x, y);
            FolderSystemPanelInstance().panels.add(sp);
            add(sp);
        }
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