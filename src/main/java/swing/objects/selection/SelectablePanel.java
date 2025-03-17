package swing.objects.selection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static swing.objects.selection.SelectionManager.SelectionInstance;

public class SelectablePanel extends JPanel {
    private int index;
    private boolean selected = false;
    private Person person;
    private long selectionOrder = 0; // Порядок выделения

    public SelectablePanel(int index, Person person, int x, int y) {
        this.index = index;
        this.person = person;
        setBackground(Color.LIGHT_GRAY);
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        setBounds(x, y, 80, 80);
        setLayout(new GridLayout(2, 1));

        JLabel nameLabel = new JLabel(person.getName(), SwingConstants.CENTER);
        JLabel ageLabel = new JLabel("Age: " + person.getAge(), SwingConstants.CENTER);
        add(nameLabel);
        add(ageLabel);

        MouseAdapter ma = new MouseAdapter() {
            Point pressPoint = null;
            boolean moved = false;
            boolean initialCtrl = false;
            boolean initialShift = false;
            boolean initialAlt = false; // сохраняем состояние Alt
            boolean pendingShiftClick = false;
            boolean dragStartedWithShift = false;

            @Override
            public void mousePressed(MouseEvent e) {
                e.consume();
                // Определяем состояния модификаторов
                initialAlt = (e.getModifiersEx() & MouseEvent.ALT_DOWN_MASK) != 0;
                boolean shift = (e.getModifiersEx() & MouseEvent.SHIFT_DOWN_MASK) != 0;
                boolean alt = initialAlt;
                if (alt && !shift) {
                    // Если нажата только Alt, сразу снимаем выделение и выходим
                    SelectablePanel.this.setSelected(false);
                    return;
                }
                pressPoint = e.getPoint();
                moved = false;
                initialCtrl = (e.getModifiersEx() & MouseEvent.CTRL_DOWN_MASK) != 0;
                initialShift = shift;
                dragStartedWithShift = initialShift;
                pendingShiftClick = false;

                if (initialShift) {
                    pendingShiftClick = true;
                    if (SelectionInstance().anchorIndex == -1) {
                        SelectionInstance().clearSelection();
                        SelectablePanel.this.setSelected(true);
                        SelectionInstance().anchorIndex = getIndex();
                    }
                } else if (initialCtrl) {
                    SelectionInstance().handlePanelClick(SelectablePanel.this, e);
                } else {
                    if (!SelectablePanel.this.isSelected()) {
                        SelectionInstance().clearSelection();
                        SelectablePanel.this.setSelected(true);
                        SelectionInstance().anchorIndex = getIndex();
                    } else {
                        SelectionInstance().anchorIndex = getIndex();
                    }
                }
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                if (initialAlt) {
                    return;
                }
                Point current = e.getPoint();
                if (!moved && pressPoint != null) {
                    double dx = current.x - pressPoint.x;
                    double dy = current.y - pressPoint.y;
                    if (Math.sqrt(dx * dx + dy * dy) > 5) {
                        moved = true;
                        if (!initialCtrl && !initialShift && !SelectablePanel.this.isSelected()) {
                            SelectionInstance().clearSelection();
                            SelectablePanel.this.setSelected(true);
                            SelectionInstance().anchorIndex = getIndex();
                        }
                        if (initialShift && pendingShiftClick && !SelectablePanel.this.isSelected()) {
                            SelectionInstance().handlePanelClick(SelectablePanel.this, e);
                            pendingShiftClick = false;
                        } else if (initialShift && pendingShiftClick) {
                            pendingShiftClick = false;
                        }
                    }
                }
                if (moved && !SelectionInstance().draggingGroup && isSelected()) {
                    SelectionInstance().draggingGroup = true;
                    SelectionInstance().groupDragStart = SwingUtilities.convertPoint(SelectablePanel.this, pressPoint, SelectionInstance().getGlassPane());
                    int count = 0;
                    for (SelectablePanel sp : SelectionInstance().panels) {
                        if (sp.isSelected()) count++;
                    }
                    String ghostText = (count > 1) ? "Dragging " + count + " objects" : "Dragging object";
                    SelectionInstance().dragGlassPane.setGhostText(ghostText);
                    SelectionInstance().dragGlassPane.setGhostLocation(new Point(SelectionInstance().groupDragStart.x + 10, SelectionInstance().groupDragStart.y + 10));
                    SelectionInstance().dragGlassPane.setVisible(true);
                }
                if (SelectionInstance().draggingGroup) {
                    Point glassPt = SwingUtilities.convertPoint(SelectablePanel.this, e.getPoint(), SelectionInstance().getGlassPane());
                    SelectionInstance().dragGlassPane.setGhostLocation(new Point(glassPt.x + 10, glassPt.y + 10));
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    System.out.println("Я открыт: " + SelectablePanel.this.getDisplayText());
                    if (SelectablePanel.this.getIsFolder()) {
                        SelectionInstance().clearSelection();
                    }
                    SelectionInstance().anchorIndex = -1;
                    return;
                }

                if (SelectionInstance().draggingGroup) {
                    Point dropPoint = SwingUtilities.convertPoint(SelectablePanel.this, e.getPoint(), SelectionInstance().dropTargetPanel);
                    if (new Rectangle(0, 0, SelectionInstance().dropTargetPanel.getWidth(), SelectionInstance().dropTargetPanel.getHeight()).contains(dropPoint)) {
                        List<SelectablePanel> selectedItems = new ArrayList<>();
                        for (SelectablePanel sp : SelectionInstance().panels) {
                            if (sp.isSelected()) {
                                selectedItems.add(sp);
                            }
                        }
                        Collections.sort(selectedItems, Comparator.comparingLong(SelectablePanel::getSelectionOrder));
                        SelectionInstance().dropTargetPanel.dropItems(selectedItems);
                        SelectionInstance().clearSelection();
                        SelectionInstance().anchorIndex = -1;
                    }
                    SelectionInstance().draggingGroup = false;
                    SelectionInstance().groupDragStart = null;
                    SelectionInstance().dragGlassPane.clearGhost();
                } else {
                    if (!moved) {
                        if (initialShift && pendingShiftClick) {
                            SelectionInstance().handlePanelClick(SelectablePanel.this, e);
                            pendingShiftClick = false;
                            return;
                        } else if (!initialCtrl && !initialShift && !initialAlt) {
                            if (SelectablePanel.this.isSelected()) {
                                SelectionInstance().clearSelection();
                                SelectablePanel.this.setSelected(true);
                                SelectionInstance().anchorIndex = getIndex();
                            }
                        }
                    }
                }
            }

        };
        addMouseListener(ma);
        addMouseMotionListener(ma);
    }

    public int getIndex() {
        return index;
    }

    public boolean isSelected() {
        return selected;
    }

    public String getDisplayText() {
        return person.getName() + " (" + person.getAge() + ")";
    }

    public boolean getIsFolder() {
        return person.isFolder();
    }

    public long getSelectionOrder() {
        return selectionOrder;
    }

    public void setSelected(boolean selected) {
        if (!selected && this.selected && SelectionInstance().anchorIndex == this.index) {
            SelectionInstance().updateAnchorAfterDeselection(this.index);
        }
        if (selected && !this.selected) {
            this.selectionOrder = SelectionInstance().globalSelectionCounter++;
        }
        if (!selected) {
            this.selectionOrder = 0;
        }
        this.selected = selected;
        if (selected) {
            setBorder(BorderFactory.createLineBorder(Color.BLUE, 3));
        } else {
            setBorder(BorderFactory.createLineBorder(Color.BLACK));
        }
        repaint();
    }
}