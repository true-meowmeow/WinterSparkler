package swing.objects;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SmoothScrollPane extends JScrollPane {//todo короче позже, надоело мне возиться, логику приянто писать на выходных.!
//https://github.com/JetBrains/intellij-community/blob/1d0b0b2692aec9226010664a0a3e8ebd1215d0bc/platform/platform-api/src/com/intellij/ui/scroll/MouseWheelSmoothScroll.java
    // Интервал обновления таймера (мс)
    private static final int TIMER_DELAY = 8;
    // Коэффициент масштабирования прокрутки (на сколько пикселей добавляется скорость на единицу события)
    private static final double SCROLL_MULTIPLIER = 10;
    // Фактор замедления (фрикция). Значение от 0 до 1: чем ближе к 0, тем быстрее скорость затухает.
    private static final double FRICTION = 0.8;
    // Минимальный порог, ниже которого инерция считается закончившейся (в пикселях)
    private static final double VELOCITY_THRESHOLD = 0.5;

    // Таймер для анимации
    private Timer smoothScrollTimer;

    // Текущее положение полосы прокрутки в виде дробного значения
    private double currentScroll;
    // Текущая скорость в пикселях за тик (это значение может накапливаться и изменяться)
    private double velocity = 0;

    public SmoothScrollPane(Component view) {
        super(view, VERTICAL_SCROLLBAR_AS_NEEDED, HORIZONTAL_SCROLLBAR_NEVER);

        //todo ->
        //setWheelScrollingEnabled(false); // Отключаем стандартную обработку колесика
        //initSmoothScrolling();
    }

    private void initSmoothScrolling() {
        position = 0;

        // Удаляем все стандартные MouseWheelListener
        for (MouseWheelListener mwl : getMouseWheelListeners()) {
            removeMouseWheelListener(mwl);
        }
        addMouseWheelListener(new InertialMouseWheelListener());

/*        // Инициализируем текущее значение скролла
        JScrollBar verticalScrollBar = getVerticalScrollBar();
        if (verticalScrollBar != null) {
            currentScroll = verticalScrollBar.getValue();
        }*/

        // Создаем таймер, который будет постоянно обновлять скроллинг, когда есть скорость
        smoothScrollTimer = new Timer(TIMER_DELAY, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performInertialScrollStep();
            }
        });
    }

    /**
     * Переопределение метода setViewportView для установки компонента.
     */
    @Override
    public void setViewportView(Component view) {
        super.setViewportView(view);
        JScrollBar verticalScrollBar = getVerticalScrollBar();
        if (verticalScrollBar != null) {
            currentScroll = verticalScrollBar.getValue();
        }
    }

    double position;
    double move;
    private class InertialMouseWheelListener implements MouseWheelListener {
        @Override
        public void mouseWheelMoved(MouseWheelEvent e) {
            JScrollBar verticalScrollBar = getVerticalScrollBar();
            if (verticalScrollBar == null || !verticalScrollBar.isVisible())
                return;

            double preciseRotation = e.getPreciseWheelRotation();
            position += preciseRotation;

            // При каждом новом событии запускаем таймер (если он не запущен)
            if (!smoothScrollTimer.isRunning()) {
                smoothScrollTimer.start();
            }

            e.consume();
        }
    }
    private void performInertialScrollStep() {
        // Обновляем позицию с учетом текущей скорости
        currentScroll += velocity;
        JScrollBar verticalScrollBar = getVerticalScrollBar();
        if (verticalScrollBar == null) {
            smoothScrollTimer.stop();
            return;
        }
        int maxValue = verticalScrollBar.getMaximum() - verticalScrollBar.getVisibleAmount();
        // Ограничиваем значение скролла
        currentScroll = Math.max(0, Math.min(currentScroll, maxValue));
        //verticalScrollBar.setValue((int) Math.round(currentScroll));
        verticalScrollBar.setValue((int) position);

        // Применяем фрикцию к скорости
        velocity *= FRICTION;

        // Если скорость стала очень маленькой, останавливаем таймер и сбрасываем скорость
        if (Math.abs(velocity) < VELOCITY_THRESHOLD) {
            velocity = 0;
            smoothScrollTimer.stop();
        }
    }
}
