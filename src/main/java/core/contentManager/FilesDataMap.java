package core.contentManager;

import java.nio.file.Path;
import java.util.*;

//todo Есть вариант отсортировать все карты по старшинству имени и запустить в поток чтобы чем ближе к корню файлы, тем больше приоритет
public class FilesDataMap { //Объект корневых путей

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

    //fixme Есть вероятность реализации isActive здесь включительно

    public CatalogData getCatalogDataWithPath(Path path) {
        return catalogDataHashMap.get(path);
    }

    public class CatalogData {  //fixme Объект всех папок

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

        public class FilesData {        //todo Можно реализацию isActive отложить до лучших времён чтобы линки сейчас не писать

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

            public class SubFolder {
                Path path;
                Path name;

                public SubFolder(Path path, Path name) {
                    this.path = path;
                    this.name = name;
                }

                public void setPath(Path path) {
                    this.path = path;
                }

                public void setName(Path name) {
                    this.name = name;
                }

                public Path getPath() {
                    return path;
                }

                public Path getName() {
                    return name;
                }
            }

            public void addSubFolder(Path path, Path name) {
                foldersDataHashSet.add(new SubFolder(path, name));
            }
        }

    }
}