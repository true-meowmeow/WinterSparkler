package core.service;

public class CollectionNameGenerator {  /// Этот класс отвечает за автоматическую генерацию имени коллекции на основе данных



    String[] folderNames;       /// Массив имён папок

    //fixme Нужно сделать чтобы если передавалась объединённая группа, то он знал об этом и передавал конечную папку как папку в которой объединяется группа


    String[] mediaNames;        /// Массив имён песен


    public CollectionNameGenerator(String[] folderNames, String[] mediaNames) {
        this.folderNames = folderNames;
        this.mediaNames = mediaNames;
    }


    private static final String defaultName = "New collection";
    public String generateName() {
        boolean failed = true;

        if (failed) {
            return defaultName;  // Стандартное имя до реализации класса
        }
        return "";
    }
}
