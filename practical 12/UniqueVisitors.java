import java.util.*;

public class UniqueVisitors
{
    public static void main(String[] args)
    {
        List<String> logins = Arrays.asList(
            "Amit", "Neha", "Raj", "Amit", "Priya", "Neha", "Karan"
        );

        Set<String> uniqueUsers = new HashSet<>(logins);
        Set<String> orderedUsers = new LinkedHashSet<>(logins);

        System.out.println("Unique visitors: " + uniqueUsers.size());
        System.out.println("First three distinct names:");

        int count = 0;

        for (String user : orderedUsers)
        {
            if (count == 3)
            {
                break;
            }

            System.out.println(user);
            count++;
        }
    }
}