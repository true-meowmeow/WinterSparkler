package core.contentManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CollectionNameGenerator {                                                  //                                 (Этот класс отвечает до тех пор пока он не отвечает)
    /// Этот класс отвечает за автоматическую генерацию имени коллекции на основе данных ->
    /// Список имён всех аудиофайлов без расширения передаваемых в коллекцию + Список имён каждой папки абсолютного пути до ближайшего общего корня


    String[] folderNames;       /// Массив имён папок из Path

    //fixme Нужно сделать чтобы если передавалась объединённая группа, то он знал об этом и передавал конечную папку как папку в которой объединяется группа


    String[] mediaNames;

    /// Массив имён медиа в текущей папке


    public CollectionNameGenerator(List<MediaData> mediaGroupList) {
        mediaNames = FolderUtil.getNamesWithoutExtensions(mediaGroupList);
        folderNames = FolderUtil.getFolderNames(FolderUtil.getShortestPath(mediaGroupList));

        generateName();
    }


    private static final String defaultName = "New collection";
    private String collectionName = defaultName;

    /// Сейчас метод логики установки имени в состоянии где ему просто переданы данные и он базово реализует свой будущий функционал
    public void generateName() {
        try {
            collectionName = folderNames[folderNames.length - 1];
        } catch (Exception e) {
        }
    }

    public String getCollectionName() {
        return collectionName;
    }
}
