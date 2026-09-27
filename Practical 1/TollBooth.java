import java.util.Scanner;

public class TollBooth {

    // Record
    record Vehicle(String number, String type) {}

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        int total = 0;
        int bike = 0;
        int car = 0;
        int truck = 0;

        while (true) {

            System.out.print("Enter Vehicle Number ('done' for stop): ");
            String number = sc.nextLine();

            // Stop when user enters "done" as vehicle number
            if (number.equalsIgnoreCase("done")) {
                break;
            }

            System.out.print("Enter Vehicle Type (bike/car/truck): ");
            String type = sc.nextLine().toLowerCase();

            // Create Vehicle record
            Vehicle v = new Vehicle(number, type);

            // Switch Expression
            int toll = switch (v.type()) {

                case "bike" : {
                    bike++;
                    yield 20;
                }

                case "car" : {
                    car++;
                    yield 50;
                }

                case "truck" : {
                    truck++;
                    yield 150;
                }

                default : {
                    System.out.println("Invalid Vehicle Type!");
                    yield 0;
                }
            };

            total += toll;
        }

        // Find most frequent vehicle
        String mostFrequent;

        if (car >= bike && car >= truck) {
            mostFrequent = "car";
        } else if (bike >= truck) {
            mostFrequent = "bike";
        } else {
            mostFrequent = "truck";
        }

        System.out.println("\n----- Toll Booth Report -----");
        System.out.println("Cars   : " + car);
        System.out.println("Bikes  : " + bike);
        System.out.println("Trucks : " + truck);
        System.out.println("Total Toll: " + total);
        System.out.println("Most Frequent: " + mostFrequent);

    }
}



