package swing.pages.home.play.objects;

import core.contentManager.MediaData;
import swing.objects.JPanelCustom;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

public class MediaPanel extends JPanelCustom {
    public String mediaName;
    private ImageIcon icon;
    private JPanelCustom parentPanel;
    private MediaData mediaData;
    private boolean isSelected = false; // Флаг выделения

    private static final Color DEFAULT_COLOR = Color.WHITE;
    private static final Color SELECTED_COLOR = new Color(173, 216, 230); // Светло-голубой

    public MediaPanel(MediaData mediaData, String iconPath, JPanelCustom parentPanel) {
        super(PanelType.BORDER);
        this.mediaData = mediaData;
        this.mediaName = mediaData.getNameFull();
        this.parentPanel = parentPanel;
        this.icon = new ImageIcon(iconPath);
        initialize();
        setupDragAndDrop();
    }

    private void initialize() {
        setPreferredSize(new Dimension(100, 30));
        setBorder(BorderFactory.createLineBorder(Color.GRAY));
        setBackground(DEFAULT_COLOR);
        setLayout(new BorderLayout());

        JLabel label = new JLabel(mediaName, icon, JLabel.CENTER);
        label.setVerticalTextPosition(JLabel.BOTTOM);
        label.setHorizontalTextPosition(JLabel.CENTER);
        add(label, BorderLayout.CENTER);
        label.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {    //todo mousePressed работает иначе, возможно потом заменить на него
                boolean ctrl = e.isControlDown();
                System.out.println("444");
                SelectionManager.toggleMediaSelection(MediaPanel.this, ctrl, true);
            }
/*            @Override
            public void mousePressed(MouseEvent e) {
                // Устанавливаем временный серый фон
                MediaPanel.this.setBackground(Color.GRAY);
                MediaPanel.this.repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                MediaPanel.this.toggleSelection();
                super.mouseExited(e);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                // Возвращаем фон в исходное состояние (выделен или нет)
                //MediaPanel.this.toggleSelection();
            }*/
        });

    }

    public void setSelected(boolean selected) {
        isSelected = selected;
        toggleSelection();
    }

    public boolean isSelected() {
        return isSelected;
    }

    private void toggleSelection() {
        setBackground(isSelected ? SELECTED_COLOR : DEFAULT_COLOR);
        repaint();
    }

    // Переопределяем метод для передачи полного объекта для DnD
    @Override
    protected Object getDragData() {
        return this;
    }

    @Override
    public String toString() {
        return mediaName;
    }
}
