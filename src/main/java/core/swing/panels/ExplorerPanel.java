package core.swing.panels;

import core.main.check.Axis;
import core.main.check.PanelType;
import core.objects.GMarqueePanel;
import core.objects.GPanel;
import core.swing.data.transferTrain.ExplorerModel;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class ExplorerPanel extends GPanel {
    public static final String name_id = "ExplorerPanel";
    private static final int FOLDER_SIZE = 100;
    private static final int FILE_WIDTH = 120;
    private static final int FILE_HEIGHT = 40;
    private static final int ITEM_GAP = 10;
    private static final String ORIGINAL_BORDER_PROPERTY = "gptOriginalBorder";
    private static final Border SELECTED_BORDER = BorderFactory.createLineBorder(new Color(60, 120, 220), 2);

    private final ExplorerModel model;
    private int folderCounter = 1;
    private int mediaCounter = 1;
    private JButton addFolderButton;
    private JButton addMediaButton;
    private GPanel foldersPanel;
    private GPanel filesPanel;
    private GMarqueePanel contentPanel;
    private final List<JButton> folderButtons = new ArrayList<>();
    private final List<JButton> fileButtons = new ArrayList<>();
    private final Set<JButton> selectedButtons = new LinkedHashSet<>();
    private Set<JButton> marqueeBaseSelection = new LinkedHashSet<>();
    private JButton anchorButton;

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

        contentPanel = new GMarqueePanel(new BorderLayout(0, ITEM_GAP));
        contentPanel.setScrollableUnitIncrement(ITEM_GAP);
        contentPanel.setScrollableTracksViewportHeight(true);
        contentPanel.add(foldersPanel, BorderLayout.NORTH);
        contentPanel.add(filesPanel, BorderLayout.CENTER);
        contentPanel.setMarqueeHandler(new GMarqueePanel.MarqueeHandler() {
            @Override
            public void marqueeStarted(Rectangle rect, boolean additive) {
                beginMarqueeSelection(additive);
                updateMarqueeSelection(rect);
            }

            @Override
            public void marqueeUpdated(Rectangle rect, boolean additive) {
                updateMarqueeSelection(rect);
            }

            @Override
            public void marqueeFinished(Rectangle rect, boolean additive) {
                updateMarqueeSelection(rect);
            }
        });
        contentPanel.installMarqueeListeners(contentPanel);
        contentPanel.installMarqueeListeners(foldersPanel);
        contentPanel.installMarqueeListeners(filesPanel);

        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setBorder(null);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        contentPanel.installMarqueeListeners(scrollPane.getViewport());

        add(scrollPane, BorderLayout.CENTER);
        add(actionsPanel, BorderLayout.SOUTH);

        setupSelectionShortcuts();
        setBackground(Color.GREEN);
    }

    private void addFolder() {
        String name = "Folder " + folderCounter++;
        JButton folder = buildItemButton(name, FOLDER_SIZE, FOLDER_SIZE);
        registerItemButton(folder, folderButtons);
        foldersPanel.add(folder);
        foldersPanel.revalidate();
        foldersPanel.repaint();
    }

    private void addMedia() {
        String name = "File " + mediaCounter++;
        JButton media = buildItemButton(name, FILE_WIDTH, FILE_HEIGHT);
        registerItemButton(media, fileButtons);
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

    private void beginMarqueeSelection(boolean additive) {
        marqueeBaseSelection = additive ? new LinkedHashSet<>(selectedButtons) : new LinkedHashSet<>();
    }

    private void updateMarqueeSelection(Rectangle rect) {
        Set<JButton> next = new LinkedHashSet<>(marqueeBaseSelection);
        for (JButton button : getAllButtons()) {
            Rectangle buttonRect = SwingUtilities.convertRectangle(
                    button.getParent(),
                    button.getBounds(),
                    contentPanel
            );
            if (rect.intersects(buttonRect)) {
                next.add(button);
            }
        }
        setSelection(next);
    }

    private void registerItemButton(JButton button, List<JButton> bucket) {
        button.putClientProperty(ORIGINAL_BORDER_PROPERTY, button.getBorder());
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                handleSelectionClick(button, e);
            }
        });
        bucket.add(button);
    }

    private void handleSelectionClick(JButton button, MouseEvent e) {
        boolean isCtrl = (e.getModifiersEx() & InputEvent.CTRL_DOWN_MASK) != 0;
        boolean isShift = (e.getModifiersEx() & InputEvent.SHIFT_DOWN_MASK) != 0;

        if (isShift) {
            selectRange(button, isCtrl);
            return;
        }

        if (isCtrl) {
            toggleSelection(button);
            anchorButton = button;
            return;
        }

        setSelection(List.of(button));
        anchorButton = button;
    }

    private void selectRange(JButton target, boolean addToSelection) {
        List<JButton> allButtons = getAllButtons();
        int targetIndex = allButtons.indexOf(target);
        if (targetIndex < 0) {
            return;
        }
        int anchorIndex = anchorButton == null ? -1 : allButtons.indexOf(anchorButton);
        if (anchorIndex < 0) {
            anchorIndex = targetIndex;
        }

        int from = Math.min(anchorIndex, targetIndex);
        int to = Math.max(anchorIndex, targetIndex);
        List<JButton> range = allButtons.subList(from, to + 1);

        if (addToSelection) {
            addSelection(range);
        } else {
            setSelection(range);
        }
        anchorButton = target;
    }

    private void toggleSelection(JButton button) {
        if (selectedButtons.contains(button)) {
            setSelected(button, false);
            selectedButtons.remove(button);
        } else {
            setSelected(button, true);
            selectedButtons.add(button);
        }
    }

    private void addSelection(Collection<JButton> buttons) {
        for (JButton button : buttons) {
            if (selectedButtons.add(button)) {
                setSelected(button, true);
            }
        }
    }

    private void setSelection(Collection<JButton> buttons) {
        clearSelection();
        addSelection(buttons);
    }

    private void clearSelection() {
        for (JButton button : selectedButtons) {
            setSelected(button, false);
        }
        selectedButtons.clear();
    }

    private void setSelected(JButton button, boolean selected) {
        if (selected) {
            button.setBorder(SELECTED_BORDER);
        } else {
            Object original = button.getClientProperty(ORIGINAL_BORDER_PROPERTY);
            if (original instanceof Border) {
                button.setBorder((Border) original);
            }
        }
    }

    private List<JButton> getAllButtons() {
        List<JButton> all = new ArrayList<>(folderButtons.size() + fileButtons.size());
        all.addAll(folderButtons);
        all.addAll(fileButtons);
        return all;
    }

    private void setupSelectionShortcuts() {
        JComponent target = this;
        target.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT)
                .put(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_DOWN_MASK), "selectFiles");
        target.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT)
                .put(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_DOWN_MASK | InputEvent.SHIFT_DOWN_MASK),
                        "selectAll");
        target.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT)
                .put(KeyStroke.getKeyStroke(KeyEvent.VK_D, InputEvent.CTRL_DOWN_MASK), "clearSelection");

        target.getActionMap().put("selectFiles", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setSelection(fileButtons);
                anchorButton = fileButtons.isEmpty() ? null : fileButtons.get(fileButtons.size() - 1);
            }
        });

        target.getActionMap().put("selectAll", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<JButton> all = getAllButtons();
                setSelection(all);
                anchorButton = all.isEmpty() ? null : all.get(all.size() - 1);
            }
        });

        target.getActionMap().put("clearSelection", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearSelection();
                anchorButton = null;
            }
        });
    }
}
