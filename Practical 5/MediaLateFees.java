// MediaLateFees.java
abstract class Media {
    String title;
    Media(String title) {
        this.title = title;
    }
    abstract double lateFee(int daysLate);
}

class Book extends Media {
    Book(String title) {
        super(title);
    }
    @Override
    double lateFee(int daysLate) {
        return daysLate * 2; // Rs.2 per day
    }
}

class DVD extends Media {
    DVD(String title) {
        super(title);
    }
    @Override
    double lateFee(int daysLate) {
        return daysLate * 5; // Rs.5 per day
    }
}

class Magazine extends Media {
    Magazine(String title) {
        super(title);
    }
    @Override
    double lateFee(int daysLate) {
        return daysLate * 1; // Rs.1 per day
    }
}

public class MediaLateFees {
    public static void main(String[] args) {
        Media[] items = {
            new Book("Java Basics"),
            new DVD("Inception"),
            new Magazine("Tech Monthly")
        };

        int[] daysLate = {3, 2, 5}; // Example late days
        double totalFee = 0;

        for (int i = 0; i < items.length; i++) {
            double fee = items[i].lateFee(daysLate[i]);
            System.out.println(items[i].title + " Late Fee = " + fee);
            totalFee += fee;
        }

        System.out.println("Total Late Fees = " + totalFee);
    }
}
