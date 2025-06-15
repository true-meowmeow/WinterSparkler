package core.contentManager;

import swing.ui.pages.settings.FolderPathsPanel;

import java.nio.file.Path;
import java.util.*;

public class FolderUtil {

    public static String getName(String nameFull) {
        if (nameFull.contains(".")) {
            int lastDotIndex = nameFull.lastIndexOf('.');
            return nameFull.substring(0, lastDotIndex);
        }
        return nameFull;
    }

    public static String getExtension(String nameFull) {
        if (nameFull.contains(".")) {
            int lastDotIndex = nameFull.lastIndexOf('.');
            return nameFull.substring(lastDotIndex + 1).toLowerCase();
        }
        return "";
    }

    public static Path getShortestPath(List<MediaData> mediaList) {
        if (mediaList == null || mediaList.isEmpty()) {
            return null;
        }

        return mediaList.stream()
                .map(MediaData::getFullPath)
                .min(Comparator
                        .comparingInt(Path::getNameCount)
                        .thenComparingInt(p -> p.toString().length()))
                .orElse(null);
    }

    public static String[] getFolderNames(Path fullNamePath) {
        int nameCount = fullNamePath.getNameCount();
        if (nameCount <= 1) {
            return new String[0];
        }
        String[] folders = new String[nameCount];
        for (int i = 0; i < nameCount; i++) {
            folders[i] = fullNamePath.getName(i).toString();
        }
        return folders;
    }

    public static String[] getNamesWithoutExtensions(List<MediaData> mediaList) {
        if (mediaList == null || mediaList.isEmpty()) {
            return new String[0];
        }

        List<String> names = new ArrayList<>(mediaList.size());
        for (MediaData media : mediaList) {
            if (media != null) {
                names.add(media.getName());
            }
        }
        return names.toArray(new String[0]);
    }

    public static String[] getUniqueFoldersBetweenCoreAndMedia(List<MediaData> mediaList) {
        List<Path> corePaths = FolderPathsPanel.corePaths;
        if (mediaList == null || mediaList.isEmpty() || corePaths == null || corePaths.isEmpty())
            return new String[0];

        // детерминируемость: сортируем пути медиаданных
        List<MediaData> sorted = new ArrayList<>(mediaList);
        Collections.sort(sorted);

        // формируем «слои» для каждого медиа-файла
        List<List<String>> layersByMedia = new ArrayList<>();
        int maxDepth = 0;

        for (MediaData md : sorted) {
            Path media = md.getFullPath();
            Path core = null;
            int longest = -1;
            for (Path cp : corePaths) {                         // выбираем самый длинный совпадающий корень
                if (media.startsWith(cp) && cp.getNameCount() > longest) {
                    core = cp;
                    longest = cp.getNameCount();
                }
            }
            if (core == null) continue;                         // медиа вне корней — пропускаем

            List<String> segs = new ArrayList<>();
            segs.add(core.getFileName().toString());            // слой 0 — сам корень
            Path rel = core.relativize(media);                  // всё, что глубже
            rel.forEach(p -> segs.add(p.toString()));
            layersByMedia.add(segs);
            maxDepth = Math.max(maxDepth, segs.size());
        }

        // Breadth-First сбор уникальных имён по слоям
        Set<String> ordered = new LinkedHashSet<>();
        for (int depth = 0; depth < maxDepth; depth++) {
            for (List<String> segs : layersByMedia) {
                if (depth < segs.size()) ordered.add(segs.get(depth));
            }
        }
        return ordered.toArray(new String[0]);
    }

}
