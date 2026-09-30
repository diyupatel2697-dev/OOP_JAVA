
import java.util.*;

// Custom Checked Exception
class OutOfStockException extends Exception {
    private int shortfall;

    public OutOfStockException(String message, int shortfall) {
        super(message);
        this.shortfall = shortfall;
    }

    public int getShortfall() {
        return shortfall;
    }
}

// Custom Exception
class InvalidQuantityException extends Exception {
    public InvalidQuantityException(String message) {
        super(message);
    }
}

// Warehouse Class
class Warehouse {

    private Map<String, Integer> stock;

    public Warehouse() {
        stock = new HashMap<>();

        stock.put("Laptop", 10);
        stock.put("Mouse", 25);
        stock.put("Keyboard", 15);
        stock.put("Monitor", 8);
    }

    public void issue(String item, int qty)
            throws OutOfStockException, InvalidQuantityException {

        if (qty <= 0) {
            throw new InvalidQuantityException(
                    "Quantity must be greater than zero.");
        }

        int available = stock.getOrDefault(item, 0);

        if (available < qty) {
            int shortfall = qty - available;

            throw new OutOfStockException(
                    item + " stock insufficient.",
                    shortfall);
        }

        stock.put(item, available - qty);

        System.out.println(
                qty + " " + item + "(s) issued successfully.");
    }

    public void displayStock() {
        System.out.println("\nCurrent Stock:");
        for (Map.Entry<String, Integer> entry : stock.entrySet()) {
            System.out.println(
                    entry.getKey() + " : " + entry.getValue());
        }
    }
}

// Request Class
class Request {
    String item;
    int qty;

    public Request(String item, int qty) {
        this.item = item;
        this.qty = qty;
    }
}

// Main Class
public class WarehouseDemo {

    public static void main(String[] args) {

        Warehouse warehouse = new Warehouse();

        List<Request> requests = Arrays.asList(
                new Request("Laptop", 5),
                new Request("Mouse", 30),
                new Request("Keyboard", -2),
                new Request("Monitor", 4),
                new Request("Laptop", 20),
                new Request("Mouse", 10)
        );

        for (Request req : requests) {

            try {
                warehouse.issue(req.item, req.qty);

            } catch (InvalidQuantityException e) {
                System.out.println(
                        "InvalidQuantityException: "
                                + e.getMessage());

            } catch (OutOfStockException e) {
                System.out.println(
                        "OutOfStockException: "
                                + e.getMessage()
                                + " Shortfall = "
                                + e.getShortfall());
            }
        }

        warehouse.displayStock();
    }
}


