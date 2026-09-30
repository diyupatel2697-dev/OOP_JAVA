import java.lang.annotation.*;
import java.lang.reflect.Method;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@interface Run {
}

class MyTests
{
    @Run
    public void testAddition()
        {
            System.out.println("Addition Test");
        }
        
        @Run
        public void testString()
        {
            System.out.println("Sytring test");
        }

        public void normalMethod()
            {
                System.out.println("Normal Method");
            }
        @Run
        public void testMultiplication()
        {
            System.out.println("Multiplication test");
        }
    
}

class MiniTestRunner
{
    public static void runTests(Object obj)
    {
        int count =0;

        Class<?>classType=obj.getClass();

        for(Method method:classType.getDeclaredMethods())
        {
            if(method.isAnnotationPresent(Run.class) && method.getParameterCount()==0)
            {
            try
            {
                System.out.println("Running:"+method.getName());
                method.invoke(obj);
                count++;
            }

            catch(Exception e)
            {
                System.out.println("Error running: "+method.getName());
            }
            }
        }
    
    System.out.println("Total Mothods executed:" +count);
    }
}


public class Main_1 
{
    public static void main(String[] args) 
    {
        MyTests tests =new MyTests();
        MiniTestRunner.runTests(tests);
    }
}
