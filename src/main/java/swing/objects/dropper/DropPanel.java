package swing.objects.dropper;

import swing.objects.general.panel.JPanelCustom;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class DropPanel extends JPanelCustom {

    private static final Border HOVER_BORDER =
            BorderFactory.createLineBorder(new Color(0x1E88E5), 2);

    private Border defaultBorder;
    private boolean hoverBorderEnabled = true;
    public  DropPanelAbstract dropTargetPanel;

    /* ----------------------- конструкторы ----------------------- */
    public DropPanel(String name, DropPanelAbstract dropTargetPanel) {
        this.dropTargetPanel = dropTargetPanel;
        DropPanelRegistry.register(name, this);
    }
    public DropPanel() {}

    /* ----------------- настройка DropPanel ---------------------- */
    public void setDropTargetPanel(DropPanelAbstract dropTargetPanel) {
        this.dropTargetPanel = dropTargetPanel;
    }
    public void registerCollectionID(int any)      { DropPanelRegistry.register(String.valueOf(any), this); }
    public void registerCollectionID(String any)   { DropPanelRegistry.register(any, this); }

    /* ----------- управление подсветкой коллекции --------------- */
    public void setHoverBorderEnabled(boolean enabled) {
        this.hoverBorderEnabled = enabled;
        if (!enabled) setBorder(defaultBorder);     // мгновенно убираем рамку, если она была
        repaint();
    }
    public boolean isHoverBorderEnabled() { return hoverBorderEnabled; }

    /** Вызывается MovementHandler’ом при наведении */
    public void setHovered(boolean hovered) {
        if (!hoverBorderEnabled) return;            // рамка отключена — просто выходим

        if (hovered) {
            if (defaultBorder == null) defaultBorder = getBorder();
            setBorder(HOVER_BORDER);
        } else {
            setBorder(defaultBorder);
        }
        repaint();
    }
}