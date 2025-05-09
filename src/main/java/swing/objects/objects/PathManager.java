package swing.objects.objects;

import swing.ui.pages.home.play.view.ManagePanel;

import java.beans.PropertyChangeSupport;
import java.nio.file.Path;
import java.util.HashSet;

public class PathManager {
    private static final PathManager instance = new PathManager(); // один экземпляр
    private Path path;
    private final PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);
    public static final String PROPERTY_NAME = "_PATH_MANAGER_";

    private HashSet<Path> corePaths;

    private PathManager() {
        corePaths = new HashSet<>();

        propertyChangeSupport.addPropertyChangeListener(evt -> {
            ManagePanel.getInstance().updateManagePanel();
        });
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
        updatePath(newPath);
    }

    public void setPath() {
        updatePath(pathHome);
    }

    private void updatePath(Path newPath) {
        Path oldPath = this.path;
        this.path = newPath;
        propertyChangeSupport.firePropertyChange(PROPERTY_NAME, oldPath, newPath);
    }

    public void goToParentDirectory() {
        if (corePaths.contains(path) || path.equals(pathHome)) {
        } else if (corePaths.contains(path.getParent())) {
            goToHome();
        } else {
            setPath(path.getParent());
        }
    }

    private final Path pathHome = Path.of("Home");

    public boolean isPathHome() {
        return path.equals(pathHome);
    }

    public void goToHome() {
        setPath(pathHome);
    }
}
