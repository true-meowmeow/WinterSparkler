package core.contentManager;

import java.io.File;
import java.net.URI;
import java.util.*;

import static swing.pages.home.play.objects.FolderUtil.convertSlashes;

public class FileDataProcessor {
    private static final Set<String> AUDIO_EXTENSIONS = new HashSet<>(Arrays.asList("wav", "flac", "opus", "mp3"));


    /**
     * Обходит все указанные корневые пути и собирает данные о файлах.
     * Для каждого корневого пути создаётся объект FilesData, куда добавляются найденные файлы.
     *
     * @param rootPaths список корневых путей (например, "C:\Music")
     * @return объект FilesDataList, содержащий для каждого корневого пути список найденных файлов
     */
    public FilesDataList processRootPaths(List<String> rootPaths) { //Проходит по корневым путям
        FilesDataList filesDataList = new FilesDataList();
        for (String rootPath : rootPaths) {
            System.out.println("dfsfds");
            File root = new File(rootPath);
            if (!root.exists() || !root.isDirectory()) {
                System.out.println("Путь не является допустимой директорией: " + rootPath);
                continue;
            }
            // Создаем объект для всех найденных файлов
            FilesData filesDataAll = new FilesData(rootPath);
            processDirectory(root, root, filesDataAll);
            // Добавляем в общий список (даже если файлов нет)
            filesDataList.getFilesDataListAll().add(filesDataAll);

            // Формируем объект с отфильтрованными (аудио) файлами
            FilesData filesDataFiltered = new FilesData(rootPath);
            for (FilesData.FileData fd : filesDataAll.getFileData()) {
                if (AUDIO_EXTENSIONS.contains(fd.getExtension())) {
                    filesDataFiltered.addFileData(fd);
                }
            }
            // Добавляем в отфильтрованный список только если найдены аудиофайлы
            if (!filesDataFiltered.getFileData().isEmpty()) {
                filesDataList.getFilesDataListFiltered().add(filesDataFiltered);
            }
        }
        return filesDataList;
    }


    /**
     * Рекурсивно обходит директорию и добавляет найденные файлы в переданный объект FilesData.
     *
     * @param currentDir текущая директория обхода
     * @param baseDir    корневая директория, относительно которой вычисляется относительный путь
     * @param filesData  объект, в который добавляются найденные данные о файлах
     */
    private void processDirectory(File currentDir, File baseDir, FilesData filesData) {
        File[] files = currentDir.listFiles();
        if (files == null) {
            return;
        }
        for (File file : files) {
            if (file.isDirectory()) {
                // Рекурсивный обход подпапок
                processDirectory(file, baseDir, filesData);
            } else if (file.isFile()) {
                // Вычисляем относительный путь от baseDir до файла
                URI baseURI = baseDir.toURI();
                URI fileURI = file.toURI();

                String fullRelativePath = baseURI.relativize(fileURI).getPath();
                //System.out.println(baseURI);
                //System.out.println(fileURI);
                // Убираем из пути имя файла, оставляя только путь к папке
                String folderRelative;
                if (fullRelativePath.endsWith(file.getName())) {
                    folderRelative = convertSlashes(fullRelativePath.substring(0, fullRelativePath.length() - file.getName().length()));
                } else {
                    folderRelative = convertSlashes(fullRelativePath);
                }

                // Добавляем данные о файле в текущий FilesData
                filesData.addFileData(new FilesData.FileData("file.getPath()", folderRelative, file.getName()));
            }//todo проверить работу напрямую в диске T:/
        }
    }

    /**
     * Фильтрует файлы, оставляя только аудиофайлы с указанными расширениями.
     *
     * @param filesDataList объект FilesDataList, содержащий данные по всем корневым путям
     * @return список объектов FilesData.FileData, удовлетворяющих условию (например, расширения wav, flac, opus, mp3)
     */
    public List<FilesData.FileData> filterAudioFiles(FilesDataList filesDataList) {
        List<FilesData.FileData> audioFiles = new ArrayList<>();
        for (FilesData filesData : filesDataList.getFilesDataListFiltered()) {
            for (FilesData.FileData fileData : filesData.getFileData()) {
                if (AUDIO_EXTENSIONS.contains(fileData.getExtension())) {
                    audioFiles.add(fileData);
                }
            }
        }
        return audioFiles;
    }
}
