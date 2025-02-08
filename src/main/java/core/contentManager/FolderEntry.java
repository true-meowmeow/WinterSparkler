package core.contentManager;

import java.io.File;

public
// Класс-обёртка для элемента списка
class FolderEntry {
    private final String path;
    private final boolean addButton; // если true – это специальный элемент "Добавить папку"

    public FolderEntry(String path) {
        this.path = path;
        this.addButton = false;
    }

    // Для создания элемента "Добавить папку"
    public FolderEntry(boolean addButton) {
        this.path = null;
        this.addButton = addButton;
    }

    public boolean isAddButton() {
        return addButton;
    }

    public String getPath() {
        return path;
    }

    public String getDisplayName() {
        if (path == null) {
            return "";
        }
        String separator = File.separator;
        // Разбиваем по обратному слешу или прямому слешу
        String[] parts = path.split("\\\\|/");
        if (parts.length < 2) {
            return path;
        }
        StringBuilder sb = new StringBuilder();
        // Идем с конца к началу (реверсируем порядок)
        for (int i = parts.length - 1; i >= 0; i--) {
            String part = parts[i];
            // Если это последняя (фактически изначально первая) часть и она оканчивается на ":", переместим двоеточие в начало
            if (i == 0 && part.endsWith(":")) {
                part = ":" + part.substring(0, part.length() - 1);
            }
            sb.append(part);
            if (i > 0) {
                sb.append(separator);
            }
        }
        return sb.toString();
    }
}