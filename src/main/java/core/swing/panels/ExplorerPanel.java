package core.swing.panels;

import core.main.check.Axis;
import core.main.check.PanelType;
import core.objects.GPanel;
import core.objects.GScrollablePanel;
import core.swing.data.transferTrain.ExplorerModel;

import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

public class ExplorerPanel extends GPanel {
    public static final String name_id = "ExplorerPanel";
    private static final int FOLDER_SIZE = 100;
    private static final int FILE_WIDTH = 120;
    private static final int FILE_HEIGHT = 40;
    private static final int ITEM_GAP = 10;

    private final ExplorerModel model;
    private int folderCounter = 1;
    private int mediaCounter = 1;
    private JButton addFolderButton;
    private JButton addMediaButton;
    private GPanel foldersPanel;
    private GPanel filesPanel;

    public ExplorerPanel(ExplorerModel model) {
        super();
        this.model = model;
    }

    public void init() {
        if (addMediaButton != null) {
            return;
        }

        addFolderButton = new JButton("Add Folder");
        addMediaButton = new JButton("Add Media");

        addFolderButton.addActionListener(e -> addFolder());
        addMediaButton.addActionListener(e -> addMedia());

        GPanel actionsPanel = new GPanel(PanelType.FLOW, Axis.LEFT, ITEM_GAP, ITEM_GAP, false);
        actionsPanel.add(addFolderButton);
        actionsPanel.add(addMediaButton);

        foldersPanel = new GPanel(PanelType.WRAP, Axis.LEFT, ITEM_GAP, ITEM_GAP, false);
        filesPanel = new GPanel(PanelType.WRAP, Axis.LEFT, ITEM_GAP, ITEM_GAP, false);

        GScrollablePanel contentPanel = new GScrollablePanel(new BorderLayout(0, ITEM_GAP));
        contentPanel.setScrollableUnitIncrement(ITEM_GAP);
        contentPanel.add(foldersPanel, BorderLayout.NORTH);
        contentPanel.add(filesPanel, BorderLayout.CENTER);

        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setBorder(null);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        add(scrollPane, BorderLayout.CENTER);
        add(actionsPanel, BorderLayout.SOUTH);

        setBackground(Color.GREEN);
    }

    private void addFolder() {
        String name = "Folder " + folderCounter++;
        JButton folder = buildItemButton(name, FOLDER_SIZE, FOLDER_SIZE);
        foldersPanel.add(folder);
        foldersPanel.revalidate();
        foldersPanel.repaint();
    }

    private void addMedia() {
        String name = "File " + mediaCounter++;
        JButton media = buildItemButton(name, FILE_WIDTH, FILE_HEIGHT);
        filesPanel.add(media);
        filesPanel.revalidate();
        filesPanel.repaint();
    }

    private static JButton buildItemButton(String label, int width, int height) {
        JButton button = new JButton(label);
        Dimension size = new Dimension(width, height);
        button.setPreferredSize(size);
        button.setMinimumSize(size);
        button.setMaximumSize(size);
        return button;
    }

}
