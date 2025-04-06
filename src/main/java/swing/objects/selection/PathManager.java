package swing.objects.selection;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.nio.file.Path;

public class PathManager {
    private static final PathManager instance = new PathManager(); // один экземпляр
    private Path path;
    private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);

    private PathManager() {
        // приватный конструктор — никто не сможет создать объект извне
    }

    public static PathManager getInstance() {
        return instance;
    }

    public Path getPath() {
        return path;
    }

    public void setPath(Path newPath) {
        Path oldPath = this.path;
        this.path = newPath;
        pcs.firePropertyChange("path", oldPath, newPath);
    }

    public void goToParentDirectory() {
        if (path != null) {
            Path parent = path.getParent();
            if (parent != null) {
                setPath(parent);
            } else {
                System.out.println("Нет родительской директории — вы уже в корне.");
            }
        } else {
            System.out.println("Текущий путь не установлен.");
        }
    }
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        pcs.removePropertyChangeListener(listener);
    }
}
