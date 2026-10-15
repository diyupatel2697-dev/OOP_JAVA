import java.util.NoSuchElementException;

class GenericStack<T>
{
    private Object[] elements;
    private int top;

    GenericStack(int capacity)
    {
        elements = new Object[capacity];
        top = -1;
    }

    public void push(T value)
    {
        if (top == elements.length - 1)
        {
            throw new IllegalStateException("Stack is full");
        }

        elements[++top] = value;
    }

    @SuppressWarnings("unchecked")
    public T pop()
    {
        if (top == -1)
        {
            throw new NoSuchElementException("Stack is empty");
        }

        T value = (T) elements[top];
        elements[top--] = null;
        return value;
    }

    public boolean isEmpty()
    {
        return top == -1;
    }
}

public class GenericStackDemo
{
    public static void main(String[] args)
    {
        GenericStack<Integer> numbers = new GenericStack<>(5);
        numbers.push(10);
        numbers.push(20);

        System.out.println(numbers.pop());
        System.out.println(numbers.pop());

        GenericStack<String> names = new GenericStack<>(5);
        names.push("Amit");
        names.push("Neha");

        System.out.println(names.pop());
        System.out.println(names.pop());
    }
}