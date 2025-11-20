package obsolete.swing.core.objects;

public class OrderCounter {    //Из этого класса потом надо будет сделать базу данных
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
