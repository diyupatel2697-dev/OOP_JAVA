import java.lang.annotation.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@interface MaxLength{
    int value();
}
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@interface NotBlank{

}

class SingupForm
{
    @NotBlank
    @MaxLength(20)
    String username;

    @NotBlank
    @MaxLength(50)
    String email;

    @NotBlank
    @MaxLength(15)
    String password;

    SingupForm(String username,String email,String password)
    {
        this.username=username;
        this.email=email;
        this.password=password;
    }
}

class Validation 
{
    public static List<String> validate(Object obj)
    {
        java.util.List<String>errors=new ArrayList<>();
        Class<?>classType=obj.getClass();
        for(Field field:classType.getDeclaredFields())
        {
            field.setAccessible(true);
        

        try 
        {
            Object value=field.get(obj);

            if(field.isAnnotationPresent(NotBlank.class))
            {
                if(value==null || value.toString().trim().isEmpty())
                {
                    errors.add(field.getName()+" Must not be blank");
                }
            }

            if(field.isAnnotationPresent(MaxLength.class))
            {
                MaxLength annotation=field.getAnnotation(MaxLength.class);

                int maxLength=annotation.value();

                if(value!=null && value.toString().length()>maxLength)
                {
                    errors.add(field.getName()+" must have maximum "+maxLength+" characters");
                }

            }
        } 

        catch (IllegalAccessException e)
        {
            errors.add("cannot access field: "+field.getName());
        }

        }

    return errors;
    }
    
}

public class Main{
public static void main(String[] arg)
{
    SingupForm form=new SingupForm("DIYA", "diyupatel@gamil.com", "178838667668767676767676767677376228");

    List<String>errors=Validation.validate(form);

    if(errors.isEmpty())
    {
        System.out.println("Form is valid.");
    }

    else{
        System.out.println("Validation Errors:");

        for(String error:errors)
        {
            System.out.println(error);
        }
    }
}
}