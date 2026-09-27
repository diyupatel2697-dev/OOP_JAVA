import java.util.Scanner;

public class Driver_Chatfilter {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        String[] logs = {
            "10:05 alice Hello there",
            "10:10 bob How are you",
            "10:15 InvalidLine"
        };

        System.out.print("Enter keyword: ");
        String keyword = sc.nextLine();

        ChatFilter.filter(logs, keyword);

        sc.close();
    }
}

