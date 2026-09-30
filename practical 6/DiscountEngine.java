import java.util.*;

@FunctionalInterface
interface DiscountRule {
    double apply(double price);
}

public class DiscountEngine {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        double[] prices = {500, 1000, 1500, 2000};

        System.out.println("1. 10% Discount");
        System.out.println("2. 20% Discount");
        System.out.print("Choose discount rule: ");
        int choice = sc.nextInt();

        DiscountRule rule;

        if (choice == 1) {
            rule = price -> price - (price * 0.10);
        } else {
            rule = price -> price - (price * 0.20);
        }

        System.out.println("\nPrices after discount:");

        for (double price : prices) {
            System.out.println(price + " -> " + rule.apply(price));
        }

        sc.close();
    }
}