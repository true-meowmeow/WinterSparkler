package swing.pages.home.play;

import core.contentManager.FileData;
import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;
import java.util.List;

public class FolderSystemPanel extends JPanel {

    private JTree tree;
    private DefaultTreeModel treeModel;

    public FolderSystemPanel(List<FileData> audioFiles) {
        setLayout(new BorderLayout());
        DefaultMutableTreeNode root = buildTree(audioFiles);
        treeModel = new DefaultTreeModel(root);
        tree = new JTree(treeModel);
        tree.setRootVisible(true);

        JScrollPane scrollPane = new JScrollPane(tree);
        add(scrollPane, BorderLayout.CENTER);
    }

    /**
     * Обновляет модель дерева новым списком аудиофайлов.
     */
    public void updateAudioFiles(List<FileData> newAudioFiles) {
        DefaultMutableTreeNode newRoot = buildTree(newAudioFiles);
        treeModel.setRoot(newRoot);
        treeModel.reload();
        // При необходимости можно обновить отображение панели:
        revalidate();
        repaint();
    }

    private DefaultMutableTreeNode buildTree(List<FileData> audioFiles) {
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Музыка");

        for (FileData fileData : audioFiles) {
            String relativePath = fileData.getRelativePath();
            String fileName = fileData.getFileName();

            if (relativePath.endsWith("/")) {
                relativePath = relativePath.substring(0, relativePath.length() - 1);
            }

            DefaultMutableTreeNode parentNode = root;
            if (!relativePath.isEmpty()) {
                String[] folders = relativePath.split("/");
                for (String folder : folders) {
                    if (folder.isEmpty()) {
                        continue;
                    }
                    DefaultMutableTreeNode childNode = findChild(parentNode, folder);
                    if (childNode == null) {
                        childNode = new DefaultMutableTreeNode(folder);
                        parentNode.add(childNode);
                    }
                    parentNode = childNode;
                }
            }
            DefaultMutableTreeNode fileNode = new DefaultMutableTreeNode(fileName);
            parentNode.add(fileNode);
        }
        return root;
    }

    private DefaultMutableTreeNode findChild(DefaultMutableTreeNode parent, String name) {
        for (int i = 0; i < parent.getChildCount(); i++) {
            DefaultMutableTreeNode child = (DefaultMutableTreeNode) parent.getChildAt(i);
            if (name.equals(child.getUserObject().toString())) {
                return child;
            }
        }
        return null;
    }
}
