
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

class Student implements Serializable
{
    int id;
    String name;
    transient String password;

    Student(int id, String name, String password)
    {
        this.id = id;
        this.name = name;
        this.password = password;
    }
}

public class SAveLoad
{
    public static void main(String[] args)
    {
        Student[] students = {
            new Student(1, "Amit", "abc123"),
            new Student(2, "Neha", "xyz456")
        };

        try
        {
            ObjectOutputStream out = new ObjectOutputStream(
                new FileOutputStream("students.dat"));
            out.writeObject(students);
            out.close();

            ObjectInputStream in = new ObjectInputStream(
                new FileInputStream("students.dat"));
            Student[] loaded = (Student[]) in.readObject();
            in.close();

            for (Student s : loaded)
            {
                System.out.println("ID: " + s.id);
                System.out.println("Name: " + s.name);
                System.out.println("Password: " + s.password);
                System.out.println();
            }
        }
        catch (IOException | ClassNotFoundException e)
        {
            System.out.println(e.getMessage());
        }
    }
}
