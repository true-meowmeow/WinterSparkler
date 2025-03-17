package swing.pages.home.play.objects;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class FolderUtil {
    /**
     * Метод возвращает массив строк с непосредственными подкаталогами для заданного родительского пути.
     * Если parentPath некорректен (не заканчивается на "/" или не найден ни один элемент, начинающийся с него),
     * то возвращаются все корневые папки.
     * <p>
     * При не корневом вызове, если для заданного parentPath обнаруживается ровно одна дочерняя папка,
     * метод рекурсивно переходит к ней и возвращает ее дочерние папки («папки внутри неё»).
     *
     * @param parentPath Родительский путь, например "Новая папка/123/"
     * @param paths      TreeSet со всеми путями
     * @return Массив строк с путями непосредственных подкаталогов или (при автопереходе) папок внутри конечной папки
     */
    public static String[] getChildFolders(String parentPath, TreeSet<String> paths) {
        // Проверяем корректность parentPath:
        // Если он не пустой, но не заканчивается на "/", сбрасываем в корневой уровень.
        if (!parentPath.isEmpty() && !parentPath.endsWith("/")) {
            parentPath = "";
        } else if (!parentPath.isEmpty()) {
            // Если parentPath заканчивается на "/" но ни один путь не начинается с него — считаем его некорректным.
            boolean found = false;
            for (String path : paths) {
                if (path.startsWith(parentPath)) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                parentPath = "";
            }
        }

        // Собираем непосредственные дочерние папки для parentPath
        Set<String> childFolders = new LinkedHashSet<>();

        for (String path : paths) {
            if (parentPath.isEmpty()) {
                // Для корневого уровня: извлекаем первую папку (до первого "/")
                if (path.isEmpty()) continue;
                int index = path.indexOf("/");
                if (index != -1) {
                    String childFolder = path.substring(0, index + 1);
                    childFolders.add(childFolder);
                } else {
                    childFolders.add(path.endsWith("/") ? path : path + "/");
                }
            } else {
                // Для заданного уровня: ищем пути, начинающиеся с parentPath (но не равные ему)
                if (path.startsWith(parentPath) && !path.equals(parentPath)) {
                    String remainder = path.substring(parentPath.length());
                    if (remainder.isEmpty()) continue;
                    int index = remainder.indexOf("/");
                    if (index != -1) {
                        // Имя дочерней папки — это первая компонента остатка (с "/" в конце)
                        String candidate = parentPath + remainder.substring(0, index + 1);
                        childFolders.add(candidate);
                    } else {
                        String candidate = path.endsWith("/") ? path : path + "/";
                        childFolders.add(candidate);
                    }
                }
            }
        }

        // Если parentPath не является корневым и найдена ровно одна дочерняя папка,
        // рекурсивно переходим в неё (то есть, возвращаем папки внутри нее).
        if (!parentPath.isEmpty() && childFolders.size() == 1) {
            String singleChild = childFolders.iterator().next();
            return getChildFolders(singleChild, paths);
        }

        return childFolders.toArray(new String[0]);
    }

    public static String removeTrailingSlash(String input) {
        if (input != null && input.endsWith("/")) {
            return input.substring(0, input.length() - 1);
        }
        return input;
    }

    public static String convertSlashes(String input) {
        if (input == null) {
            return null;
        }
        return input.replace('/', '\\');
    }

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
            return nameFull.substring(lastDotIndex + 1);
        }
        return "";
    }
}
