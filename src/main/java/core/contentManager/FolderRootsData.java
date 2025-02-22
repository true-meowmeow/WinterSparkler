package core.contentManager;

import java.util.Comparator;
import java.util.HashSet;

public class FolderRootsData implements Comparable<FolderRootsData> {
    private String rootPath;
    private HashSet<FolderData> folderDataHashSet;

    public FolderRootsData(String rootPath) {
        this.rootPath = rootPath;
        folderDataHashSet = new HashSet<>();
    }

    public void addFolderDataHashSet(FolderData folderData) {
        folderDataHashSet.add(folderData);
    }


    public HashSet<FolderData> getFolderDataHashSet() {
        return folderDataHashSet;
    }

    @Override
    public int compareTo(FolderRootsData o) {
        return this.rootPath.compareTo(o.rootPath);
    }


    public class FolderData implements Comparable<FolderData> {

        private String fullPath;
        private String relativePath;

        private boolean active = true; //Переключается на false при соответствии условии (Нет медиафайлов и одна папка)

        private String name;            //folder name

        public FolderData(String fullPath/*, String rootPath*/, String relativePath) {
            this.fullPath = fullPath;
            this.relativePath = relativePath;

            System.out.println(fullPath);

            this.name = extractFolderName(fullPath);    //todo check
        }

        @Override
        public String toString() {
            return "FolderData{" +
                    "fullPath='" + fullPath + '\'' +
                    '}';
        }

        public String getFullPath() {
            return fullPath;
        }

        public String getRelativePath() {
            return relativePath;
        }

        public boolean isActive() {
            return active;
        }



        public void deactivate() {
            this.active = false;
        }

        public static String extractFolderName(String fullPath) {
            if (fullPath == null || fullPath.isEmpty()) {
                return "";
            }
            // Удаляем завершающие разделители
            while(fullPath.endsWith("\\") || fullPath.endsWith("/")) {
                fullPath = fullPath.substring(0, fullPath.length() - 1);
            }
            // Находим последний индекс разделителя
            int lastSeparatorIndex = Math.max(fullPath.lastIndexOf("\\"), fullPath.lastIndexOf("/"));
            if (lastSeparatorIndex == -1) {
                // Если разделитель не найден, возвращаем всю строку
                return fullPath;
            }
            return fullPath.substring(lastSeparatorIndex + 1);
        }


        @Override
        public int compareTo(FolderData o) {
            return this.fullPath.compareTo(o.fullPath);
        }
    }
}