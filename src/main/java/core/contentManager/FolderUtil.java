package core.contentManager;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

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

/*    public static String[] getFolderNamesSkipFirst(Path fullNamePath) {
        int nameCount = fullNamePath.getNameCount();
        if (nameCount <= 1) {
            return new String[0];
        }
        String[] folders = new String[nameCount - 1];
        for (int i = 1; i < nameCount; i++) {
            folders[i - 1] = fullNamePath.getName(i).toString();
        }
        return folders;
    }*/
}
