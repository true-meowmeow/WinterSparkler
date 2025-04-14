package swing.objects.selection;

import swing.objects.JPanelCustom;

import javax.swing.*;

public class DropPanel extends JPanelCustom {
    public DropPanelChild dropTargetPanel;

    // Конструктор принимает имя и конкретный компонент для drop'а.
    public DropPanel(String name, DropPanelChild dropTargetPanel) {
        // Можно вызвать конструктор базового класса с нужной раскладкой, если требуется.
        super(PanelType.BORDER);
        this.dropTargetPanel = dropTargetPanel;
        // Например, можно добавить scrollPane, если это нужно
        JScrollPane dropScroll = new JScrollPane(dropTargetPanel);
        add(dropScroll);

        // Регистрируем панель по имени.
        DropPanelRegistry.register(name, this);
    }
}

