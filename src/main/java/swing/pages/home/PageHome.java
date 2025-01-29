package swing.pages.home;

import javax.swing.*;
import java.awt.*;

import static core.Methods.arePageWeightsValid;

public class PageHome extends JPanel {

    private final double weight1 = 0.19;
    private final double weight2 = 0.19;
    private final double weight3 = 0.42;
    private final double weight4 = 0.2;

    public PageHome() {
        arePageWeightsValid("Home", weight1, weight2, weight3, weight4);

        setLayout(new GridBagLayout());

        addPanel(0, weight1, new ArtistsPanel());
        addPanel(1, weight2, new CollectionsPanel());
        addPanel(2, weight3, new PlayPanel());
        addPanel(3, weight4, new PlayPanel());
    }

    private void addPanel(int gridX, double weightX, JPanel panel) {

        panel.setBorder(BorderFactory.createLineBorder(Color.GREEN));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = gridX;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = weightX;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;

        add(panel, gbc);
    }

}
