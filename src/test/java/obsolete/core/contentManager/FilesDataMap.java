package obsolete.core.contentManager;

import java.nio.file.Path;
import java.util.*;

public class FilesDataMap {
    /// Объект корневых путей

    private HashMap<Path, CatalogData> catalogDataHashMap = new HashMap<>();     //rootPath, FolderData

    public HashMap<Path, CatalogData> getCatalogDataHashMap() {
        return catalogDataHashMap;
    }

    public void createCatalogDataHashMap(Path path) {
        catalogDataHashMap.put(path, new CatalogData());
    }

    public void terminate(Path rootPath) {  //fixme or deactivate??
        catalogDataHashMap.remove(rootPath);
    }

    public FilesDataMap.CatalogData.FilesData getFilesDataByFullPath(Path fullPath) {
        // Если можно однозначно определить корневой путь по началу полного пути, то:
        for (Map.Entry<Path, CatalogData> entry : catalogDataHashMap.entrySet()) {
            Path rootPath = entry.getKey();
            // Если полный путь начинается с этого корневого пути
            if (fullPath.startsWith(rootPath)) {
                FilesDataMap.CatalogData catalogData = entry.getValue();
                FilesDataMap.CatalogData.FilesData filesData = catalogData.getFilesDataWithPath(fullPath);
                if (filesData != null) {
                    return filesData;
                }
            }
        }
        // Если ничего не найдено, возвращаем null или можно выбросить исключение
        return null;
    }

    public ArrayList<MediaData> getAllMediaData(Path directoryPath) {
        ArrayList<MediaData> result = new ArrayList<>();
        Comparator<Path> pathComparator = (p1, p2) -> p1.toString().compareToIgnoreCase(p2.toString());
        TreeMap<Path, CatalogData.FilesData> sortedByPath = new TreeMap<>(pathComparator);

        for (CatalogData catalog : catalogDataHashMap.values()) {
            for (Map.Entry<Path, CatalogData.FilesData> entry : catalog.getFilesDataHashMap().entrySet()) {
                if (entry.getKey().startsWith(directoryPath)) {
                    sortedByPath.put(entry.getKey(), entry.getValue());
                }
            }
        }

        for (CatalogData.FilesData filesData : sortedByPath.values()) {                             ///  Group sort
            List<MediaData> mediaList = new ArrayList<>(filesData.getMediaDataHashSet());
            mediaList.sort(Comparator.comparing(MediaData::getNameFull, String.CASE_INSENSITIVE_ORDER));
            result.addAll(mediaList);
        }

        //result.sort(Comparator.comparing(MediaData::getNameFull, String.CASE_INSENSITIVE_ORDER));     /// Global sort
        return result;
    }
                                                                                                          //fixme Есть вероятность реализации isActive здесь включительно

    public CatalogData getCatalogDataWithPath(Path path) {
        return catalogDataHashMap.get(path);
    }

    public class CatalogData {                                                                           //fixme Объект всех папок

        private HashMap<Path, FilesData> filesDataHashMap = new HashMap<>();

        public FilesData getFilesDataWithPath(Path path) {
            return filesDataHashMap.get(path);
        }

        public void createFilesData(FolderData folderData) {
            filesDataHashMap.put(folderData.getFullPath(), new FilesData(folderData));
        }

        public HashMap<Path, FilesData> getFilesDataHashMap() {
            return filesDataHashMap;
        }

        public class FilesData {                                                                          //todo Можно реализацию isActive отложить до лучших времён чтобы линки сейчас не писать

            private FolderData folderData;

            public FilesData(FolderData folderData) {
                this.folderData = folderData;
            }

            private HashSet<MediaData> mediaDataHashSet = new HashSet<>();
            private HashSet<SubFolder> foldersDataHashSet = new HashSet<>();


            public FolderData getFolderData() {
                return folderData;
            }

            public HashSet<MediaData> getMediaDataHashSet() {
                return mediaDataHashSet;
            }

            public void addMediaData(MediaData data) {
                if (!folderData.isActive()) folderData.activate();
                mediaDataHashSet.add(data);
            }

            public HashSet<SubFolder> getFoldersDataHashSet() {
                return foldersDataHashSet;
            }

            public class SubFolder implements Comparable<SubFolder> {
                Path path;
                String name;      //test2

                public SubFolder(Path path, String name) {
                    this.path = path;
                    this.name = name;
                }

                public void setPath(Path path) {
                    this.path = path;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public Path getPath() {
                    return path;
                }

                public String getName() {
                    return name;
                }

                @Override
                public int compareTo(SubFolder o) {
                    return name.compareTo(o.name);
                }

                @Override
                public boolean equals(Object o) {
                    if (this == o) return true;
                    if (!(o instanceof SubFolder)) return false;
                    SubFolder that = (SubFolder) o;
                    return Objects.equals(name, that.name);
                }

                @Override
                public int hashCode() {
                    return Objects.hash(name);
                }
            }

            public void addSubFolder(Path path, Path name) {
                foldersDataHashSet.add(new SubFolder(path, name.toString()));
            }
        }

    }
}