package demo.explorer;

import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class ExplorerDnDApp {

    public static void main(String[] args) {
        FlatLightLaf.setup();

        SwingUtilities.invokeLater(() -> {
            ExplorerModel model = DemoData.createModel();

            JFrame f = new JFrame("Swing Explorer-style DnD (FlatLaf + CardLayout)");
            f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            f.setSize(1100, 650);
            f.setLocationRelativeTo(null);

            JLabel status = new JLabel("Перетащи файл мышкой между панелями (как в проводнике).");
            status.setBorder(new EmptyBorder(8, 10, 8, 10));

            CardLayout cardLayout = new CardLayout();
            JPanel cards = new JPanel(cardLayout);

            Folder inbox   = model.folderByName("Inbox");
            Folder work    = model.folderByName("Work");
            Folder archive = model.folderByName("Archive");
            Folder trash   = model.folderByName("Trash");

            // Карта 1: Inbox | Work | Trash
            JPanel card1 = new JPanel(new BorderLayout(10, 10));
            card1.setBorder(new EmptyBorder(10, 10, 10, 10));
            card1.add(new FolderView(model, inbox), BorderLayout.WEST);
            card1.add(new FolderView(model, work), BorderLayout.CENTER);
            card1.add(new FolderView(model, trash), BorderLayout.EAST);

            // Карта 2: Work | Archive (Work повторяется — видно, что “все знают”)
            JPanel card2 = new JPanel(new BorderLayout(10, 10));
            card2.setBorder(new EmptyBorder(10, 10, 10, 10));
            card2.add(new FolderView(model, work), BorderLayout.CENTER);
            card2.add(new FolderView(model, archive), BorderLayout.EAST);

            cards.add(card1, "CARD_1");
            cards.add(card2, "CARD_2");

            // Переключатель карт
            JToggleButton b1 = new JToggleButton("Карта 1");
            JToggleButton b2 = new JToggleButton("Карта 2");
            ButtonGroup bg = new ButtonGroup();
            bg.add(b1); bg.add(b2);
            b1.setSelected(true);

            b1.addActionListener(e -> cardLayout.show(cards, "CARD_1"));
            b2.addActionListener(e -> cardLayout.show(cards, "CARD_2"));

            JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 8));
            top.add(new JLabel("Переключение CardLayout:"));
            top.add(b1);
            top.add(b2);


            f.setLayout(new BorderLayout());
            f.add(top, BorderLayout.NORTH);
            f.add(cards, BorderLayout.CENTER);
            f.add(status, BorderLayout.SOUTH);

            f.setVisible(true);
        });
    }

    /** Демо-данные вынесены отдельно */
    static final class DemoData {
        static ExplorerModel createModel() {
            ExplorerModel m = new ExplorerModel();

            Folder inbox   = m.addFolder("Inbox");
            Folder work    = m.addFolder("Work");
            Folder archive = m.addFolder("Archive");
            Folder trash   = m.addFolder("Trash");

            m.addItem(inbox, new Item("report.docx"));
            m.addItem(inbox, new Item("photo.png"));
            m.addItem(inbox, new Item("todo.txt"));

            m.addItem(work, new Item("build.gradle"));
            m.addItem(work, new Item("UI_mockup.fig"));

            m.addItem(archive, new Item("old_notes.md"));
            m.addItem(trash, new Item("broken.tmp"));

            return m;
        }
    }
}
