import java.util.Random;
import java.util.Scanner;

public class Rpsls {

    enum RPS {
        ROCK, PAPER, SCISSORS, LIZARD, SPOCK
    }

    static int winner(RPS a, RPS b) {

        if (a == b)
            return 0;

        switch (a) {

            case ROCK:
                if (b == RPS.LIZARD )
                    return 1;
                else
                    return -1;

            case PAPER:
                if (b == RPS.ROCK )
                    return 1;
                else
                    return -1;

            case SCISSORS:
                if (b == RPS.PAPER )
                    return 1;
                else
                    return -1;

            case LIZARD:
                if ( b == RPS.SPOCK)
                    return 1;
                else
                    return -1;

            case SPOCK:
                if ( b == RPS.SCISSORS)
                    return 1;
                else
                    return -1;
        }


        return -2; // Never reached
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        Random random = new Random();

        int userCount = 0;
        int computerCount = 0;

        for (int i = 1; i <= 5; i++) {

            System.out.println("\nRound " + i);

            RPS computer = RPS.values()[random.nextInt(5)];

            System.out.print("Enter your choice (ROCK, PAPER, SCISSORS, LIZARD, SPOCK): ");
            RPS player = RPS.valueOf(sc.nextLine().toUpperCase());

            System.out.println("Computer chose: " + computer);

            int result = winner(player, computer);

            if (result == 1) {
                System.out.println("You Win this Round!");
                userCount++;
            } else if (result == -1) {
                System.out.println("Computer Wins this Round!");
                computerCount++;
            } else {
                System.out.println("Round Tie!");
            }
        }

        System.out.println("\nFinal Score");
        System.out.println("Player   : " + userCount);
        System.out.println("Computer : " + computerCount);

        if (userCount > computerCount)
            System.out.println("Overall Winner: Player");
        else if (computerCount > userCount)
            System.out.println("Overall Winner: Computer");
        else
            System.out.println("Overall Match Tie");

        sc.close();
    }
}