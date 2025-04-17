package swing.objects;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.nio.file.Path;
import java.util.HashSet;

public class PathManager {
    private static final PathManager instance = new PathManager(); // один экземпляр
    private Path path;
    private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);

    private HashSet<Path> corePaths;

    private PathManager() {
        corePaths = new HashSet<>();
    }

    public void setCorePaths(HashSet<Path> corePaths) {
        this.corePaths = corePaths;
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

    public void setPathHome() {
        Path oldPath = this.path;
        this.path = pathHome;
        pcs.firePropertyChange("path", oldPath, pathHome);
    }

    public void goToParentDirectory() {
        if (corePaths.contains(path) || path.equals(pathHome)) {
        } else if (corePaths.contains(path.getParent())) {
            goToHome();
        } else {
            setPath(path.getParent());
        }
    }

    private Path pathHome = Path.of("Home");

    public boolean isPathHome() {
        return path.equals(pathHome);
    }

    public void goToHome() {
        setPath(pathHome);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        pcs.removePropertyChangeListener(listener);
    }
}
