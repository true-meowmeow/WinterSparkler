package obsolete.core.contentManager;

import javax.swing.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FolderEntities {

    private static final FolderEntities INSTANCE = new FolderEntities();

    public static FolderEntities getInstance() {
        return INSTANCE;
    }

    // Модель списка хранит объекты FolderEntry
    public final DefaultListModel<FolderEntry> listModel = new DefaultListModel<>();
    // JList для отображения элементов
    public final JList<FolderEntry> pathsList = new JList<>(listModel);

/*    public void addListModel(String path) {
        listModel.addElement(new FolderEntry(path));
    }*/

    public void addPaths(List<Path> corePaths) {
        for (Path path : corePaths) {
            listModel.addElement(new FolderEntry(path));
        }
    }

    public void addListModel(boolean addButton) {
        listModel.addElement(new FolderEntry(addButton));
    }

    public List<Path> getAllPaths() {       //todo change to HashSet
        List<Path> paths = new ArrayList<>();
        for (int i = 0; i < listModel.getSize(); i++) {
            FolderEntry fe = listModel.getElementAt(i);
            if (!fe.isAddButton()) {
                paths.add(Paths.get(fe.getPath()).normalize()); // Преобразуем строку в Path и нормализуем
            }
        }
        return paths;
    }
}