package core.contentManager;

public class CollectionNameGenerator {
    /// Этот класс отвечает за автоматическую генерацию имени коллекции на основе данных ->                                 (Этот класс отвечает до тех пор пока он не отвечает)
    ///


    String[] folderNames;       /// Массив имён папок из Path

    //fixme Нужно сделать чтобы если передавалась объединённая группа, то он знал об этом и передавал конечную папку как папку в которой объединяется группа

    /// Сейчас он в состоянии где ему просто переданы данные и он базово реализует свой будущий функционал

    String[] mediaNames;        /// Массив имён песен в текущей папки


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
