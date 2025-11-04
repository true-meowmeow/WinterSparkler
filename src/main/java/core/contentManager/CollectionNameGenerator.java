package core.contentManager;

import java.util.*;

//Можно вкинуть базовую реализацию имени алгоритмом и потом подключить простейшую llm чтобы она умно это делала по данным folderNames & mediaNames, в фоне заполнит автоматическое имя коллекции другим
public class CollectionNameGenerator {
    /// Этот класс отвечает за автоматическую генерацию имени коллекции на основе данных ->
    /// Список имён всех аудиофайлов без расширения передаваемых в коллекцию + Список имён каждой папки от корня папки до конца пути по итерациям


    String[] folderNames;       /// Массив имён папок из Path

    //fixme Нужно сделать чтобы если передавалась объединённая группа, то он знал об этом и передавал конечную папку как папку в которой объединяется группа


    String[] mediaNames;

    /// Массив имён медиа в текущей папке


    public CollectionNameGenerator(List<MediaData> mediaGroupList) {
        //mediaNames = FolderUtil.getNamesWithoutExtensions(mediaGroupList);
        //folderNames = FolderUtil.getUniqueFoldersBetweenCoreAndMedia(mediaGroupList);

        generateName(mediaGroupList);
    }
    private String lastCommonFolder(List<MediaData> list) {
        if (list == null || list.isEmpty()) return "";

        String[] ref = list.get(0).getFullPath().toString().split("[\\\\/]+");
        int common = ref.length;

        for (int i = 1; i < list.size() && common > 0; i++) {
            String[] cur = list.get(i).getFullPath().toString().split("[\\\\/]+");
            int j = 0;
            while (j < common && j < cur.length && ref[j].equals(cur[j])) j++;
            common = j;
        }

        return common == 0 ? "" : ref[common - 1];
    }

    private static final String defaultName = "New collection";
    private String collectionName = defaultName;

    /// Сейчас метод логики установки имени в состоянии где ему просто переданы данные и он базово реализует свой будущий функционал
    public void generateName(List<MediaData> mediaGroupList) {
        try {
            collectionName = lastCommonFolder(mediaGroupList);
        } catch (Exception e) {
        }
    }

    public String getCollectionName() {
        return collectionName;
    }
}
