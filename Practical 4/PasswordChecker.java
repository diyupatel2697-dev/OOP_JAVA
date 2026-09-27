 public class PasswordChecker 
{

    // Check strength of password
    public static String strength(String pw) {

        int count = 0;

        // Rule 1: Length >= 8
        if (pw.length() >= 8)
            count++;

        // Rule 2: Uppercase letter
        if (pw.matches(".*[A-Z].*"))
            count++;

        // Rule 3: Digit
        if (pw.matches(".*[0-9].*"))
            count++;

        // Rule 4: Special character
        if (pw.matches(".*[^a-zA-Z0-9].*"))
            count++;

        if (count <= 1)
            return "Weak";
        else if (count <= 3)
            return "Medium";
        else
            return "Strong";
    }
}
