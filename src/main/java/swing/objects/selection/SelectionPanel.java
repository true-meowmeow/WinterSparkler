package swing.objects.selection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import static swing.objects.selection.SelectionManager.SelectionInstance;

public class SelectionPanel extends JPanel {
    public SelectionPanel() {
        setLayout(null);
        int margin = 10;
        int panelSize = 80;
        int panelsPerRow = 8;
        for (int i = 0; i < SelectionInstance().persons.size(); i++) {
            int x = margin + (i % panelsPerRow) * (panelSize + margin);
            int y = margin + (i / panelsPerRow) * (panelSize + margin);
            SelectablePanel sp = new SelectablePanel(i, SelectionInstance().persons.get(i), x, y);
            SelectionInstance().panels.add(sp);
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
                        SelectionInstance().clearSelection();
                    }
                    SelectionInstance().selectionRect = new Rectangle(dragStart);
                }
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                if (dragging) {
                    Point current = e.getPoint();
                    SelectionInstance().selectionRect.setBounds(
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
                    for (SelectablePanel sp : SelectionInstance().panels) {
                        if (SelectionInstance().selectionRect.intersects(sp.getBounds())) {
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
                    for (SelectablePanel sp : SelectionInstance().panels) {
                        if (sp.isSelected() && sp.getIndex() < minIndex) {
                            minIndex = sp.getIndex();
                        }
                    }
                    if (minIndex != Integer.MAX_VALUE) {
                        SelectionInstance().anchorIndex = minIndex;
                    }
                    SelectionInstance().selectionRect = null;
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
        if (SelectionInstance().selectionRect != null) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setColor(new Color(0, 0, 255, 50));
            g2.fill(SelectionInstance().selectionRect);
            g2.setColor(Color.BLUE);
            g2.draw(SelectionInstance().selectionRect);
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(10 + 8 * (80 + 10), 10 + 5 * (80 + 10));
    }
}