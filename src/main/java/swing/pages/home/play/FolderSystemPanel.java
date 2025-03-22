package swing.pages.home.play;

import core.contentManager.*;
import swing.objects.JPanelCustom;
import swing.objects.WrapLayout;
import swing.pages.home.play.objects.FolderPanel;
import swing.pages.home.play.objects.MediaPanel;

import javax.swing.*;
import java.awt.*;
import java.nio.file.Path;
import java.util.*;
import java.util.List;

public class FolderSystemPanel extends JPanelCustom {

    JPanelCustom cardPanel;

    public FolderSystemPanel() {
        super(PanelType.BORDER, "Y");
        add(createFrameFolderPanel());
        add(createFrameFolderBottomPanel());
    }

    int j = 0;
    public void updateManagingPanel(FilesDataMap filesDataMap) {
        cardPanel.removeAll();  // Очистка карточек

        JPanelCustom panelMain = new JPanelCustom(PanelType.BORDER, "Y");   //Это панель со всеми root's


        // Пробегаем по корневым папкам и создаём карточки для каждой из них

        for (Map.Entry<Path, FilesDataMap.CatalogData> catalogEntry : filesDataMap.getCatalogDataHashMap().entrySet()) {
            Path rootPath = catalogEntry.getKey();
            FilesDataMap.CatalogData catalogData = catalogEntry.getValue();

            //JPanel rootPanel = createRootPanel(catalogData);
            // Получаем внутреннюю мапу FilesData из CatalogData через публичный геттер
            for (Map.Entry<Path, FilesDataMap.CatalogData.FilesData> fileEntry : catalogData.getFilesDataHashMap().entrySet()) {
                FilesDataMap.CatalogData.FilesData filesData = fileEntry.getValue();

                System.out.println(j++);

                addCard(createFolderPanel(filesData), fileEntry.getKey());
            }
        }

        //cardPanel.add(panelMain, "MainPanelWS");
        //showCard("MainPanelWS");
        // Пример: переход на определённую карточку
        showCard("C:\\Users\\meowmeow\\Music\\testing\\core 1");
        cardPanel.revalidate();
        cardPanel.repaint();
    }


    private void addCard(JScrollPane panel, Path path) {
        String key = path.toString();
        cardPanel.add(panel, key);
    }

    private JPanelCustom createFrameFolderPanel() {
        JPanelCustom panel = new JPanelCustom(new BorderLayout());


        // Панель управления с кнопками и заголовком
        JPanelCustom controlPanel = new JPanelCustom(new FlowLayout(FlowLayout.LEFT));
        //controlPanel.add(backButtonPanel(filesData.getFolderData()));
        controlPanel.add(titlePanel("title"));
        panel.add(controlPanel, BorderLayout.NORTH);
/*        controlPanel.add(backButtonPanel(filesData.getFolderData()));
        controlPanel.add(titlePanel(filesData.getFolderData().getFullPathString()));*/

        return panel;
    }

    private JPanelCustom createFrameFolderBottomPanel() {
        cardPanel = new JPanelCustom(new CardLayout());
        return cardPanel;
    }


    private JScrollPane createFolderPanel(FilesDataMap.CatalogData.FilesData filesData) {
        // Прокручиваемая панель для контента
        JScrollPane contentScroll = new JScrollPane();
        contentScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        contentScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        contentScroll.setViewportView(createContentPanel(filesData));
        return contentScroll;
    }

    private JPanelCustom createContentPanel(FilesDataMap.CatalogData.FilesData filesData) {
        JPanelCustom contentPanel = new JPanelCustom(PanelType.GRID);

        CoreInsides coreInsides = new CoreInsides(filesData);
        contentPanel.setGridBagConstrains(coreInsides.foldersPanel, coreInsides.mediasPanel);

        return contentPanel;
    }


    private class CoreInsides {
        JPanelCustom foldersPanel = new Panel();
        JPanelCustom mediasPanel = new Panel();

        public CoreInsides(FilesDataMap.CatalogData.FilesData filesData) {      //todo передалть под отработанную папочную систему демо

            for (FilesDataMap.CatalogData.FilesData.SubFolder folders : filesData.getFoldersDataHashSet()) {
                foldersPanel.add(new FolderPanel(folders, cardPanel));
            }

            for (MediaData mediaData : filesData.getMediaDataHashSet()) {
                mediasPanel.add(new MediaPanel(mediaData, cardPanel));
            }
        }

        private class Panel extends JPanelCustom {
            public Panel() {
                super(new WrapLayout(FlowLayout.LEFT, 10, 10));
            }
        }
    }


    private JPanelCustom backButtonPanel(FolderData folder) {
        JPanelCustom panel = new JPanelCustom(PanelType.FLOW, "LEFT");
        JButton button = new JButton("back");
        button.addActionListener(e -> showCard(folder.getFullPathString()));             //todo fixme LATER LATER LATER LATER LATER LATER LATER                  showCard(folder.getLinkParentPathFull()));
        panel.add(button);
        return panel;
    }

    private JPanelCustom titlePanel(String rootPath) {
        JPanelCustom panel = new JPanelCustom(PanelType.FLOW, "LEFT");
        panel.add(new Label(rootPath));
        return panel;
    }

    public void showCard(String path) {
        ((CardLayout) cardPanel.getLayout()).show(cardPanel, path);
    }                                                                                             //todo Установить в changeCard новый viewport & names & links
}
