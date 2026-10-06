class TicketBooking {
    int seatsLeft = 5;

    synchronized void book(String name) {
        if (seatsLeft > 0) {
            System.out.println(name + " booked a seat.");
            seatsLeft--;
        } else {
            System.out.println(name + " could not book.");
        }
    }
}

class BookingThread extends Thread {
    TicketBooking booking;

    BookingThread(TicketBooking booking) {
        this.booking = booking;
    }

    public void run() {
        booking.book(Thread.currentThread().getName());
    }
}

public class SeatBooking {
    public static void main(String[] args) throws Exception {

        TicketBooking booking = new TicketBooking();

        Thread[] threads = new Thread[10];

        for (int i = 0; i < 10; i++) {
            threads[i] = new BookingThread(booking);
            threads[i].setName("User " + (i + 1));
            threads[i].start();
        }

        for (Thread t : threads) {
            t.join();
        }

        System.out.println("Seats left: " + booking.seatsLeft);
    }
}