class Box<T>
{
    private T value;

    public void set(T value)
    {
        this.value = value;
    }

    public T get()
    {
        return value;
    }
}

class Pair<A, B>
{
    private A first;
    private B second;

    Pair(A first, B second)
    {
        this.first = first;
        this.second = second;
    }

    public void display()
    {
        System.out.println(first + " : " + second);
    }
}

public class GenericBasics
{
    public static <T> T firstElement(T[] array)
    {
        if (array.length == 0)
        {
            throw new IllegalArgumentException("Array is empty");
        }

        return array[0];
    }

    public static void main(String[] args)
    {
        Box<String> nameBox = new Box<>();
        nameBox.set("Diya");
        System.out.println("Box: " + nameBox.get());

        Box<Integer> numberBox = new Box<>();
        numberBox.set(100);
        System.out.println("Number: " + numberBox.get());

        Pair<String, Integer> pair = new Pair<>("Amit", 20);
        pair.display();

        String[] names = {"Neha", "Raj", "Priya"};
        Integer[] numbers = {10, 20, 30};

        System.out.println("First name: " + firstElement(names));
        System.out.println("First number: " + firstElement(numbers));
    }
}