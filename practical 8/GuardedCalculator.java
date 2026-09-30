import java.util.InputMismatchException;
import java.util.Scanner;

// Custom Exception
class DivideByZeroException extends Exception {
    public DivideByZeroException(String message) {
        super(message);
    }
}

public class GuardedCalculator {

    public static double calculate(double num1, double num2, char op)
            throws DivideByZeroException {

        switch (op) {
            case '+':
                return num1 + num2;

            case '-':
                return num1 - num2;

            case '*':
                return num1 * num2;

            case '/':
                if (num2 == 0) {
                    throw new DivideByZeroException(
                            "Division by zero is not allowed.");
                }
                return num1 / num2;

            default:
                throw new IllegalArgumentException(
                        "Invalid operator. Use +, -, *, /");
        }
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        boolean success = false;
        int attempts = 0;

        while (!success) {
            attempts++;

            try {
                System.out.print("Enter first number: ");
                double num1 = sc.nextDouble();

                System.out.print("Enter operator (+, -, *, /): ");
                char op = sc.next().charAt(0);

                System.out.print("Enter second number: ");
                double num2 = sc.nextDouble();

                double result = calculate(num1, num2, op);

                System.out.println("Result = " + result);
                success = true;

            } catch (InputMismatchException e) {
                System.out.println(
                        "Invalid input! Please enter numeric values only.");
                sc.nextLine(); // clear invalid input

            } catch (DivideByZeroException e) {
                System.out.println("Error: " + e.getMessage());

            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());

            } finally {
                System.out.println("Attempt #" + attempts + " processed.");
            }
        }

        sc.close();
    }
}
