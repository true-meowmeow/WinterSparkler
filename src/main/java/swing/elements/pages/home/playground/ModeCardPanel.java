package swing.elements.pages.home.playground;

import swing.core.basics.JPanelCustom;
import swing.core.basics.PanelType;
import swing.core.basics.Pages;
import swing.elements.pages.home.RightSideMode;
import swing.elements.pages.home.RightSideSwitchable;

import java.awt.*;

public abstract class ModeCardPanel extends JPanelCustom implements RightSideSwitchable {

    private final CardLayout cardLayout;

    protected ModeCardPanel() {
        super(PanelType.CARD);
        cardLayout = (CardLayout) getLayout();
    }

    protected final void register(RightSideMode mode, Component component) {
        add(component, mode.name());
    }

    protected final Pages createGrid(GridColumn... columns) {
        Pages grid = new Pages(PanelType.GRID);
        for (int i = 0; i < columns.length; i++) {
            GridColumn column = columns[i];
            grid.add(column.component(), grid.menuGridBagConstraintsX(i, column.weight()));
        }
        return grid;
    }

    protected final GridColumn column(Component component, double weight) {
        return new GridColumn(component, weight);
    }

    @Override
    public final void showMode(RightSideMode mode) {
        cardLayout.show(this, mode.name());
    }

    protected record GridColumn(Component component, double weight) {
    }
}

