package swing.ui.pages.home.play;

import swing.objects.general.JPanelCustom;
import swing.objects.PathManager;
import swing.objects.general.PanelType;

import javax.swing.*;
import java.awt.*;

public class SelectionMenuPanel extends JPanelCustom {


    public SelectionMenuPanel() {

        JPanel infoControlsPanel = new JPanelCustom();
        {
            JPanel infoPanel = new JPanelCustom(PanelType.GRID);
            infoPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.fill = GridBagConstraints.BOTH;
            gbc.weightx = 0.5;

            JButton btn1 = new JButton("Home");
            btn1.setFocusable(false);
            btn1.setFocusPainted(false);
            gbc.gridx = 0;
            infoPanel.add(btn1, gbc);

            JButton btn2 = new JButton("Back");
            btn2.setFocusable(false);
            btn2.setFocusPainted(false);
            gbc.gridx = 1;
            infoPanel.add(btn2, gbc);

            btn2.addActionListener(e -> {
                PathManager.getInstance().goToParentDirectory();
            });
            btn1.addActionListener(e -> {
                PathManager.getInstance().goToHome();
            });


            infoControlsPanel.add(infoPanel, BorderLayout.WEST);
        }
        add(infoControlsPanel, BorderLayout.WEST);


        JPanel infoTextPanel = new JPanel(new BorderLayout());
        infoTextPanel.setBackground(Color.PINK);


        add(infoTextPanel, BorderLayout.CENTER);


    }

    //todo Сначала обработать слушатели и их поведение, переключение новой системы без карт в селекторе, потом добавление кнопок-возвращение по пути как текст

}
