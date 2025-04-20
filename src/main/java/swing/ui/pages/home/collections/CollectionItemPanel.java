package swing.ui.pages.home.collections;

import swing.objects.general.JPanelCustom;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CollectionItemPanel extends JPanelCustom {
    private boolean selected = false;
    private final Color normal = new Color(0xF5F5F5);
    private final Color selectedClr = new Color(0xDCDCDC);

    String title;
    public CollectionItemPanel(String title) {
        this.title = title;
        init();
    }

    private void init() {
        setBackground(normal);

        JLabel label = new JLabel(title, SwingConstants.CENTER);
        add(label, BorderLayout.CENTER);

        MouseAdapter ma = new MouseAdapter() {
            @Override public void mousePressed(MouseEvent e) {
                selected = !selected;
                setBackground(selected ? selectedClr : normal);
                System.out.println("Выбрана коллекция: " + title);
            }
            @Override public void mouseDragged(MouseEvent e) {
                Point p = SwingUtilities.convertPoint(CollectionItemPanel.this, e.getPoint(), getParent());
                Component under = getParent().getComponentAt(p);
                if (under instanceof CollectionItemPanel && under != CollectionItemPanel.this) {
                    int from = getParent().getComponentZOrder(CollectionItemPanel.this);
                    int to   = getParent().getComponentZOrder(under);
                    if (from != to) {
                        Container parent = getParent();
                        parent.remove(CollectionItemPanel.this);
                        parent.add(CollectionItemPanel.this, to);
                        parent.revalidate();
                        parent.repaint();
                    }
                }
            }

            @Override
            public void mouseReleased(MouseEvent event) {
                selected = false;
                setBackground(selected ? selectedClr : normal);
            }
        };
        addMouseListener(ma);
        addMouseMotionListener(ma);
    }

    int height = 150;
    @Override public Dimension getMaximumSize() {
        return new Dimension(Integer.MAX_VALUE, height);
    }

    @Override
    public Dimension preferredSize() {
        return new Dimension(0, height);
    }
}
