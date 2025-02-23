package core.contentManager;

import swing.pages.home.settings.FolderPathsPanel;

import javax.swing.*;
import java.io.File;

public class FolderEntities {   //todo блять это вообще что делает

    // Модель списка хранит объекты FolderEntry
    public final DefaultListModel<FolderEntry> listModel = new DefaultListModel<>();
    // JList для отображения элементов
    public final JList<FolderEntry> pathsList = new JList<>(listModel);


    public void addListModel(String path) {
        listModel.addElement(new FolderEntry(path));
    }

    public void addListModel(boolean addButton) {
        listModel.addElement(new FolderEntry(addButton));
    }



    // Метод для получения всех путей (без элемента "Добавить папку")
    public java.util.List<String> getAllPaths() {
        java.util.List<String> paths = new java.util.ArrayList<>();
        for (int i = 0; i < listModel.getSize(); i++) {
            FolderEntry fe = listModel.getElementAt(i);
            if (!fe.isAddButton()) {
                paths.add(fe.getPath());
            }
        }
        return paths;
    }

    public java.util.List<String> getAllDisplayNames() {
        java.util.List<String> names = new java.util.ArrayList<>();
        for (int i = 0; i < listModel.getSize(); i++) {
            FolderEntry fe = listModel.getElementAt(i);
            if (!fe.isAddButton()) {
                names.add(fe.getDisplayName());
            }
        }
        return names;
    }
}