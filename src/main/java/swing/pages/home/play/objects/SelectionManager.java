package swing.pages.home.play.objects;

import swing.pages.home.play.objects.MediaPanel;
import swing.pages.home.play.objects.FolderPanel;
import java.util.HashSet;

public class SelectionManager {
    public static HashSet<MediaPanel> selectedMediaPanels = new HashSet<>();
    public static HashSet<FolderPanel> selectedFolderPanels = new HashSet<>();

    public static void clearMediaSelection() {
        // Сброс выделения для всех медиа-объектов
        for (MediaPanel mp : new HashSet<>(selectedMediaPanels)) {
            //System.out.println(mp.mediaName);
            mp.setSelected(false);
        }
        selectedMediaPanels.clear();
    }

    public static void clearFolderSelection() {
        // Сброс выделения для всех папок
        for (FolderPanel fp : new HashSet<>(selectedFolderPanels)) {
            fp.setSelected(false);
        }
        selectedFolderPanels.clear();
    }

    public static void toggleMediaSelection(MediaPanel mp, boolean ctrl, boolean moving) {
        System.out.println("toggle");
        if (mp.isSelected() && ctrl) {
            System.out.println("555");
            //clearAllSelections(); // Очищаем все выделения
            //mp.setSelected(false);
            selectedMediaPanels.remove(mp);
            return;
        }
        if (moving && mp.isSelected()) {
            System.out.println("666");
            selectedMediaPanels.add(mp);
            return;
        }
        if (!ctrl) {
            System.out.println("777");
            clearAllSelections(); // Очищаем все выделения
            mp.setSelected(true);
            selectedMediaPanels.add(mp);
        } else {
            System.out.println("888");
            if (mp.isSelected()) {
                mp.setSelected(false);
                selectedMediaPanels.remove(mp);
            } else {
                mp.setSelected(true);
                selectedMediaPanels.add(mp);
            }
        }
        System.out.println("\n\n");
    }
    public static void clearAllSelections() {
        clearMediaSelection();
        clearFolderSelection();
    }
    public static void toggleFolderSelection(FolderPanel fp, boolean ctrl) {
        if (ctrl) {
            if (fp.isSelected()) {
                fp.setSelected(false);
                selectedFolderPanels.remove(fp);
            } else {
                fp.setSelected(true);
                selectedFolderPanels.add(fp);
            }
        } else {
            // Для перетаскивания без Ctrl (обрабатывается в DragGestureHandler)
            fp.setSelected(true);
            selectedFolderPanels.add(fp);
        }
    }
}
