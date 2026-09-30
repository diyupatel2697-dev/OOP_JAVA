import java.lang.annotation.*;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@interface Column 
{
    String name();
}

class Student 
{

    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "age")
    private int age;

    @Override
    public String toString()
    {
        return "Student{id=" + id +", name='" + name + '\'' +", age=" + age + '}';
    }
}

class ObjectMapper 
{

    public static <T> T populateObject( Class<T> clazz,String[] headers, String[] values) 
    
    throws Exception 
    {

        T obj = clazz.getDeclaredConstructor().newInstance();

        Map<String, String> rowData = new HashMap<>();

        for (int i = 0; i < headers.length; i++) 
        {
            rowData.put(headers[i], values[i]);
        }

        for (Field field : clazz.getDeclaredFields()) 
        {

            if (field.isAnnotationPresent(Column.class)) 
            {

                Column column = field.getAnnotation(Column.class);
                String columnName = column.name();

                field.setAccessible(true);

                if (rowData.containsKey(columnName))
                {

                    String value = rowData.get(columnName);

                    if (field.getType() == int.class) 
                    {
                        field.set(obj, Integer.parseInt(value));
                    }

                    else if (field.getType() == String.class) 
                    {
                        field.set(obj, value);
                    }
                }

                else 
                {
                    System.out.println("Missing column: " + columnName);
                }
            }
        }
        return obj;
    }
}

public class Main_2 
{
    public static void main(String[] args) throws Exception 
    {

        String[] headers = {"id", "name", "age"};
        String[] values = {"101", "Diya", "21"};

        Student s = ObjectMapper.populateObject(Student.class,headers,values);
        System.out.println(s);
    }
}
