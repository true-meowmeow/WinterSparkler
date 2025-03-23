package swing.objects.selection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static swing.objects.selection.DropPanel.DropPanelInstance;
import static swing.pages.home.play.FolderSystemPanel.FolderSystemPanelInstance;

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
                    if (FolderSystemPanelInstance().anchorIndex == -1) {
                        FolderSystemPanelInstance().clearSelection();
                        SelectablePanel.this.setSelected(true);
                        FolderSystemPanelInstance().anchorIndex = getIndex();
                    }
                } else if (initialCtrl) {
                    FolderSystemPanelInstance().handlePanelClick(SelectablePanel.this, e);
                } else {
                    if (!SelectablePanel.this.isSelected()) {
                        FolderSystemPanelInstance().clearSelection();
                        SelectablePanel.this.setSelected(true);
                        FolderSystemPanelInstance().anchorIndex = getIndex();
                    } else {
                        FolderSystemPanelInstance().anchorIndex = getIndex();
                    }
                }
            }

            @Override
            public void mouseDragged(MouseEvent e) {

                Component glassPane = null;
                JRootPane rootPane = SwingUtilities.getRootPane(FolderSystemPanelInstance());
                if (rootPane != null) {     //todo перенести
                    glassPane = rootPane.getGlassPane();
                }





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
                            FolderSystemPanelInstance().clearSelection();
                            SelectablePanel.this.setSelected(true);
                            FolderSystemPanelInstance().anchorIndex = getIndex();
                        }
                        if (initialShift && pendingShiftClick && !SelectablePanel.this.isSelected()) {
                            FolderSystemPanelInstance().handlePanelClick(SelectablePanel.this, e);
                            pendingShiftClick = false;
                        } else if (initialShift && pendingShiftClick) {
                            pendingShiftClick = false;
                        }
                    }
                }
                if (moved && !FolderSystemPanelInstance().draggingGroup && isSelected()) {
                    FolderSystemPanelInstance().draggingGroup = true;
                    FolderSystemPanelInstance().groupDragStart = SwingUtilities.convertPoint(SelectablePanel.this, pressPoint, glassPane);
                    int count = 0;
                    for (SelectablePanel sp : FolderSystemPanelInstance().panels) {
                        if (sp.isSelected()) count++;
                    }
                    String ghostText = (count > 1) ? "Dragging " + count + " objects" : "Dragging object";
                    FolderSystemPanelInstance().dragGlassPane.setGhostText(ghostText);
                    FolderSystemPanelInstance().dragGlassPane.setGhostLocation(new Point(FolderSystemPanelInstance().groupDragStart.x + 10, FolderSystemPanelInstance().groupDragStart.y + 10));
                    FolderSystemPanelInstance().dragGlassPane.setVisible(true);
                }
                if (FolderSystemPanelInstance().draggingGroup) {
                    Point glassPt = SwingUtilities.convertPoint(SelectablePanel.this, e.getPoint(), glassPane);
                    FolderSystemPanelInstance().dragGlassPane.setGhostLocation(new Point(glassPt.x + 10, glassPt.y + 10));
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    System.out.println("Я открыт: " + SelectablePanel.this.getDisplayText());
                    if (SelectablePanel.this.getIsFolder()) {
                        FolderSystemPanelInstance().clearSelection();
                    }
                    FolderSystemPanelInstance().anchorIndex = -1;
                    return;
                }

                if (FolderSystemPanelInstance().draggingGroup) {
                    Point dropPoint = SwingUtilities.convertPoint(SelectablePanel.this, e.getPoint(), FolderSystemPanelInstance().dropTargetPanel);
                    if (new Rectangle(0, 0, DropPanelInstance().dropTargetPanel.getWidth(), DropPanelInstance().dropTargetPanel.getHeight()).contains(dropPoint)) {
                        List<SelectablePanel> selectedItems = new ArrayList<>();
                        for (SelectablePanel sp : FolderSystemPanelInstance().panels) {
                            if (sp.isSelected()) {
                                selectedItems.add(sp);
                            }
                        }
                        Collections.sort(selectedItems, Comparator.comparingLong(SelectablePanel::getSelectionOrder));
                        DropPanelInstance().dropTargetPanel.dropItems(selectedItems);
                        FolderSystemPanelInstance().clearSelection();
                        FolderSystemPanelInstance().anchorIndex = -1;
                    }
                    FolderSystemPanelInstance().draggingGroup = false;
                    FolderSystemPanelInstance().groupDragStart = null;
                    FolderSystemPanelInstance().dragGlassPane.clearGhost();
                } else {
                    if (!moved) {
                        if (initialShift && pendingShiftClick) {
                            FolderSystemPanelInstance().handlePanelClick(SelectablePanel.this, e);
                            pendingShiftClick = false;
                            return;
                        } else if (!initialCtrl && !initialShift && !initialAlt) {
                            if (SelectablePanel.this.isSelected()) {
                                FolderSystemPanelInstance().clearSelection();
                                SelectablePanel.this.setSelected(true);
                                FolderSystemPanelInstance().anchorIndex = getIndex();
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
        if (!selected && this.selected && FolderSystemPanelInstance().anchorIndex == this.index) {
            FolderSystemPanelInstance().updateAnchorAfterDeselection(this.index);
        }
        if (selected && !this.selected) {
            this.selectionOrder = FolderSystemPanelInstance().globalSelectionCounter++;
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