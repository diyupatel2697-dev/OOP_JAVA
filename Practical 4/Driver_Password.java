import java.util.Scanner;

public class Driver_Password {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter password: ");
        String pw = sc.nextLine();

        System.out.println("Password: " + pw);
        System.out.println("Strength: " + PasswordChecker.strength(pw));

        sc.close();
    }
}