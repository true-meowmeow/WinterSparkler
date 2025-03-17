package swing.objects.selection;

public class Person {
    private String name;
    private int age;
    private boolean isFolder;

    public Person(String name, int age, boolean isFolder) {
        this.name = name;
        this.age = age;
        this.isFolder = isFolder;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public boolean isFolder() {
        return isFolder;
    }
}