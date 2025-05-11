package swing.objects.objects;

public class OrderCounter {
    private int current;

    public OrderCounter() {
        this(1);
    }

    public OrderCounter(int start) {
        current = start - 1;
    }

    public int getNextNumber() {
        return ++current;
    }
}
