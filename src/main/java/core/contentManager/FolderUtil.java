package core.contentManager;

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
}
