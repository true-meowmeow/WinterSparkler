package demo.explorer;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public final class FolderView extends JPanel implements PropertyChangeListener {
    private final ExplorerModel model;
    private final Folder folder;

    private final DefaultListModel<Item> listModel = new DefaultListModel<>();
    private final JList<Item> list = new JList<>(listModel);

    public FolderView(ExplorerModel model, Folder folder) {
        this.model = model;
        this.folder = folder;

        setLayout(new BorderLayout(8, 8));
        setBorder(BorderFactory.createTitledBorder(folder.name()));
        setPreferredSize(new Dimension(330, 500));

        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setDragEnabled(true);
        list.setDropMode(DropMode.ON);
        list.setTransferHandler(new ItemTransferHandler(model, folder, list));

        // Показываем, что объект НЕ копируется (id один и тот же после перемещения)
        list.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                          boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Item it) {
                    String id = Integer.toHexString(System.identityHashCode(it));
                    setText(it.name() + "   (id=" + id + ")");
                }
                return this;
            }
        });

        add(new JScrollPane(list), BorderLayout.CENTER);

        JLabel hint = new JLabel("DnD: перетащи сюда");
        hint.setBorder(new EmptyBorder(0, 6, 6, 6));
        hint.setEnabled(false);
        add(hint, BorderLayout.SOUTH);

        refresh();
        model.addListener(this);
    }

    private void refresh() {
        listModel.clear();
        for (Item it : folder.items()) listModel.addElement(it);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (!"move".equals(evt.getPropertyName())) return;
        MoveEvent me = (MoveEvent) evt.getNewValue();

        // Обновляемся только если нас касается перемещение
        if (me.from() == folder || me.to() == folder) {
            refresh();
        }
    }
}
