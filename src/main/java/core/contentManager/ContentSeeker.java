package core.contentManager;

import java.util.ArrayList;
import java.util.List;

public class ContentSeeker {

    FolderEntities folderEntities;

    public ContentSeeker(FolderEntities folderEntities) {
        this.folderEntities = folderEntities;
    }

    /**
     * Ищет файлы по всем заданным корневым путям, выводит статистику и фильтрует аудиофайлы.
     *
     * @return объект FilesDataList, содержащий найденные файлы для каждого корневого пути
     */
    public FilesDataList seek() {
        FileDataProcessor processor = new FileDataProcessor();

        // Получаем список корневых путей из FolderEntities (предполагается, что возвращается List<String>)
        FilesDataList filesDataList = processor.processRootPaths(folderEntities.getAllPaths());

        // Для статистики «неотсортированных» собираем все FileData из всех корневых директорий
        List<FilesData.FileData> allFiles = new ArrayList<>();
        for (FilesData fd : filesDataList.getFilesDataListAll()) {
            allFiles.addAll(fd.getFileData());
        }
        //processor.printFileStatistics(allFiles, "неотсортированных");
        //System.out.println();

        // Фильтруем аудиофайлы (например, с расширениями wav, flac, opus, mp3)
        List<FilesData.FileData> audioFiles = processor.filterAudioFiles(filesDataList);
        //processor.printFileStatistics(audioFiles, "отфильтрованных (аудио)");
        //System.out.println();

        // Демонстрация – вывод найденных аудиофайлов
        //System.out.println("Найденные аудиофайлы:");
        for (FilesData.FileData fd : audioFiles) {
            //System.out.println(fd);
        }

/*
        for (int i = 0; i < filesDataList.getFilesDataListFiltered().size(); i++) {
            for (int j = 0; j < filesDataList.getFilesDataListFiltered().get(i).getFileData().size(); j++) {
                System.out.print(filesDataList.getFilesDataListFiltered().get(i).getRootPath() + "    ");
                System.out.println(filesDataList.getFilesDataListFiltered().get(i).getFileData().get(j));
            }
        }*/


        return filesDataList;
    }
}
