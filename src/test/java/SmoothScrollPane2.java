import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * SmoothScrollPane - JScrollPane с плавной инерционной прокруткой.
 */
public class SmoothScrollPane2 extends JScrollPane {

    // Интервал обновления таймера (мс) - как часто обновляем позицию
    private static final int TIMER_DELAY = 10; // Можно 10-20 мс
    // Фактор импульса - насколько сильно колесико "толкает" скорость
    private static final double SCROLL_IMPULSE_FACTOR = 4; // Поэкспериментируйте с этим значением
    // Фактор трения - насколько быстро скорость затухает (0.0 - нет трения, 1.0 - мгновенная остановка)
    private static final double FRICTION_FACTOR = 0.82; // Значение < 1. Чем ближе к 1, тем дольше катится. (0.90 - 0.97 обычно хорошо)
    // Минимальная скорость, при которой анимация останавливается
    private static final double MIN_VELOCITY = 0.3;

    private Timer animationTimer;
    private double currentVelocity = 0.0; // Текущая скорость прокрутки (пиксели / шаг таймера)
    private boolean isRunning = false; // Флаг, что анимация активна

    public SmoothScrollPane2() {
        this(null);
    }

    public SmoothScrollPane2(Component view) {
        super(view, VERTICAL_SCROLLBAR_AS_NEEDED, HORIZONTAL_SCROLLBAR_NEVER);
        initSmoothScrolling();
    }

    public SmoothScrollPane2(int vsbPolicy, int hsbPolicy) {
        this(null);
    }

    private void initSmoothScrolling() {
        setHorizontalScrollBarPolicy(HORIZONTAL_SCROLLBAR_NEVER);
        setVerticalScrollBarPolicy(VERTICAL_SCROLLBAR_AS_NEEDED);
        setWheelScrollingEnabled(false); // Отключаем стандартную обработку

        // Удаляем стандартные обработчики
        for (MouseWheelListener mwl : getMouseWheelListeners()) {
            removeMouseWheelListener(mwl);
        }
        // Добавляем наш
        addMouseWheelListener(new InertialMouseWheelListener());

        // Инициализируем таймер
        animationTimer = new Timer(TIMER_DELAY, e -> performAnimationStep());
        animationTimer.setRepeats(true); // Таймер будет срабатывать многократно
    }

    @Override
    public void setViewportView(Component view) {
        // Сначала вызываем метод родительского класса
        super.setViewportView(view);

        // При смене компонента сбросим скорость на всякий случай
        currentVelocity = 0;

        // !!! ВАЖНО: Проверяем, был ли таймер уже инициализирован !!!
        // Он может быть null, если setViewportView вызывается из конструктора суперкласса
        if (animationTimer != null && animationTimer.isRunning()) {
            animationTimer.stop();
            // Флаг isRunning должен обновляться здесь, если таймер был остановлен
            isRunning = false;
        }
        // Если animationTimer == null, значит, инициализация еще не прошла,
        // и останавливать/проверять нечего. isRunning тоже еще должен быть false.
    }
    /**
     * Слушатель колесика мыши для инерционной прокрутки.
     */
    private class InertialMouseWheelListener implements MouseWheelListener {
        @Override
        public void mouseWheelMoved(MouseWheelEvent e) {
            JScrollBar verticalScrollBar = getVerticalScrollBar();
            if (verticalScrollBar == null || !verticalScrollBar.isVisible()) {
                return;
            }

            double preciseRotation = e.getPreciseWheelRotation();
            int unitIncrement = verticalScrollBar.getUnitIncrement(); // Базовое смещение за "клик"
            int scrollAmount = e.getScrollAmount(); // Количество "кликов" за событие (обычно 3)

            // Рассчитываем импульс, который добавится к скорости
            // Умножаем на unitIncrement и scrollAmount для учета системных настроек
            // SCROLL_IMPULSE_FACTOR - наш коэффициент для управления "силой" толчка
            double impulse = preciseRotation * unitIncrement * scrollAmount * SCROLL_IMPULSE_FACTOR;

            // Добавляем импульс к текущей скорости
            currentVelocity += impulse;

            // Ограничиваем максимальную мгновенную скорость (опционально, но полезно)
            // double maxVelocity = 100.0; // Например
            // currentVelocity = Math.max(-maxVelocity, Math.min(currentVelocity, maxVelocity));

            // Если анимация еще не запущена, запускаем таймер
            if (!isRunning) {
                isRunning = true;
                animationTimer.start();
            }

            e.consume(); // Поглощаем событие
        }
    }

    /**
     * Выполняет один шаг анимации: обновляет позицию и уменьшает скорость.
     */
    private void performAnimationStep() {
        JScrollBar verticalScrollBar = getVerticalScrollBar();
        if (verticalScrollBar == null || !verticalScrollBar.isVisible()) {
            stopAnimation();
            return;
        }

        int currentValue = verticalScrollBar.getValue();
        int maxValue = verticalScrollBar.getMaximum() - verticalScrollBar.getVisibleAmount();

        // Применяем трение, чтобы скорость постепенно уменьшалась
        currentVelocity *= FRICTION_FACTOR;

        // Вычисляем новую позицию
        double nextValueExact = currentValue + currentVelocity;
        int nextValue = (int) Math.round(nextValueExact);

        // Проверяем границы и останавливаем скорость, если уперлись
        boolean hitBounds = false;
        if (nextValue <= 0) {
            nextValue = 0;
            hitBounds = true;
        } else if (nextValue >= maxValue) {
            nextValue = maxValue;
            hitBounds = true;
        }

        // Устанавливаем новое значение скроллбара, только если оно изменилось
        if (currentValue != nextValue) {
            verticalScrollBar.setValue(nextValue);
        }


        // Если уперлись в границу, обнуляем скорость в этом направлении
        if (hitBounds && Math.signum(currentVelocity) == Math.signum(nextValue - currentValue)) {
            currentVelocity = 0;
        }

        // Проверяем условие остановки: скорость слишком мала
        if (Math.abs(currentVelocity) < MIN_VELOCITY) {
            // Дополнительная проверка: если мы "почти" на целом пикселе, но скорость мала,
            // лучше остановиться, чтобы избежать дрожания.
            if (Math.abs(nextValueExact - nextValue) < MIN_VELOCITY || currentValue == nextValue) {
                stopAnimation();
            }
            // Если мы еще не совсем на целом пикселе, но скорость мала,
            // дадим еще один шанс докатиться до ближайшего целого.
            // Можно и сразу остановить `stopAnimation();`
        }
    }

    /**
     * Останавливает анимацию.
     */
    private void stopAnimation() {
        currentVelocity = 0;
        if (isRunning) {
            animationTimer.stop();
            isRunning = false;
        }
    }

    // --- Остальной код (конструкторы, main и т.д.) остается как был ---

    /**
     * Пример основного метода для тестирования SmoothScrollPane.
     */
    public static void main(String[] args) {
        // Улучшение внешнего вида (опционально)
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Inertial SmoothScrollPane Demo"); // Изменил заголовок
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            JTextArea textArea = new JTextArea();
            textArea.setEditable(false); // Лучше сделать нередактируемым для демонстрации
            textArea.setFont(new Font("Monospaced", Font.PLAIN, 12)); // Моноширинный шрифт
            for (int i = 1; i <= 300; i++) { // Больше строк для теста
                textArea.append(String.format("Строка %03d\n", i));
            }
            textArea.setCaretPosition(0); // Установить курсор в начало

            SmoothScrollPane2 smoothScrollPane = new SmoothScrollPane2(textArea);

            // Увеличим unitIncrement для более заметного эффекта от SCROLL_MULTIPLIER
            // В этой модели unitIncrement влияет на силу "толчка"
            smoothScrollPane.getVerticalScrollBar().setUnitIncrement(20); // Можно подобрать

            frame.getContentPane().add(smoothScrollPane);
            frame.setSize(400, 400); // Увеличим размер окна
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}